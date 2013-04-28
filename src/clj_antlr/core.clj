(ns clj-antlr.core
  (:use clojure.reflect)
  (:require [clojure.set :as set])
  (:import (java.io InputStream
                    Reader)
           (org.antlr.v4.runtime ANTLRInputStream
                                 CommonTokenStream)
           (org.antlr.v4.runtime.tree Tree
                                      ParseTree
                                      ParseTreeWalker
                                      ParseTreeVisitor)))

(defmacro multi-hinted-let
  "A let expression which expands into multiple type-hinted bodies with runtime
  type dispatch provided by instanceof. Thanks to amalloy, as usual!"
  [[name expr classes] & body]
  (let [x (gensym)]
    `(let [~x ~expr]
       (condp instance? ~x
         ~@(for [class classes
                 clause [class `(let [~(with-meta name {:tag class})
                                      ~x] ~@body)]]
             clause)
         (throw (IllegalArgumentException. (str "No matching class for
                                                " ~x " in " '~classes)))))))

(defn input-stream
  "Constructs an ANTLRInputStream out of a String, Reader, or InputStream."
  [s]
  (multi-hinted-let [hinted s [InputStream Reader String]]
                    (ANTLRInputStream. hinted)))

(defmacro lexer
  "Given a lexer class, returns a lexer over a string or stream."
  [lexer-class s]
  `(new ~lexer-class (input-stream ~s)))

(defn tokens
  "A token stream taken from a lexer."
  [lexer]
  (CommonTokenStream. lexer))

(defmacro parser
  "Constructs a parser over a token stream."
  [parser-class tokens]
  `(new ~parser-class ~tokens))

(defn visit
  "Visits a node with a visitor."
  [^ParseTreeVisitor visitor node]
  (.visit visitor node))

(defn child-count
  "How many children does a node have?"
  [^Tree node]
  (.getChildCount node))

(defn children
  "Returns the children of a RuleNode."
  [^Tree node]
  (map #(.getChild node %)
       (range (child-count node))))

(defn parent
  "The parent of a node."
  [^Tree node]
  (.getParent node))

(defn text
  "The text of a node."
  [^ParseTree node]
  (.getText node))

(defn signature
  "The signature of a reflected method."
  [meth]
  (select-keys meth [:name :parameter-types :return-type]))

(defn method?
  "Is a reflection member a method?"
  [member]
  (boolean (:return-type member)))

(defn method-signatures
  "Returns a list of method signatures for a class."
  [class]
  (->> class
    reflect
    :members
    (filter method?)
    (map signature)))

(defn unique-methods
  "Given a class, returns specs for methods which are defined in that class and
  are *not* defined in its supers."
  [class]
  ; Compute all superclass/interface method signatures
  (let [supes (->> class
                supers
                (mapcat method-signatures)
                set)]
    ; Take methods on this class
    (->> class reflect :members (filter method?)
      ; And drop any which have corresponding signatures in supers
      (remove (comp supes signature)))))

(defn ->class
  "Converts symbols and strings into classes."
  [sym]
  (Class/forName (str sym)))

(defn visitor-method-children
  "Given a visitor method, returns a map of children to forms which visit
  those children, e.g.

  {:kittens (map (partial visit this (.kittens ctx)))}"
  [sig]
  (->> sig
    ; Find the parser context this method accepts
    :parameter-types
    first
    ->class
    ; Figure out what methods that context uniquely defines
    unique-methods
    ; Select zero-arities
    (filter (comp empty? :parameter-types))
    ; Three possibilities forms:
    ; - Returns a TerminalNode: (text (.FOO ctx))
    ; - Returns a List: (map (partial visit this) (.foo ctx))
    ; - Returns a thing: (visit this (.foo ctx))
    (map (fn [meth]
           (let [child (:name meth)
                 acc   (symbol (str "." child))
                 value (list acc 'ctx)]
             [(keyword child)
              (list `when-let `[~'v ~value]
                    (condp = (:return-type meth)
                      ; Multiple children
                      'java.util.List
                      `(map (partial visit ~'this) ~'v)

                      ; Terminal node
                      'org.antlr.v4.runtime.tree.TerminalNode
                      `(text ~'v)

                      ; Descend
                      `(visit ~'this ~'v)))])))
    ; Make a map out of it.
    (into {})))

(defn degenerate-visitor-spec
  "A reify spec for a particular visitor method. Returns code which, when
  used in a visitor, handles that node by returning a hashmap of its children.
  When a node has only one children, returns that child's value instead."
  [sig]
  (let [children (visitor-method-children sig)]
    ; Construct a reify spec for this method
    (list (:name sig)
          '[this ctx]
          (condp = (count children)
            ; When there are no children, return the text at this node.
            0 `(text ~'ctx)
            ; With one child, proxy directly to the child node.
            1 (first (vals children))
            ; Otherwise, make a map of our children
            children))))

(defmacro visitor
  "Helps compile a visitor for an antlr grammar. Takes the name of a visitor
  interface, followed by several method bodies. Given a grammar with a node
  like

  json:   object # JsonObject
      |   array  # JsonArray
      ;
  
  Antlr will compile an interface to traverse the parse tree like:
  
  public interface JsonVisitor<T> extends ParseTreeVisitor<T> {
      T visitJsonObject(JsonParser.JsonObjectContext ctx);
      T visitJsonArray(JsonParser.JsonArrayContext ctx);
  }
  
  You might want to define a function to handle each of these branches. Here
  'this refers to the visitor itself, and 'context refers to the parser context
  at the current node. You can traverse the tree by calling (.visit this node).

  (visitor JsonVisitor
    (JsonObject [this context] (prn :got (.getText .object c)))
    (JsonArray [this context] (.visit this (.array context))))"
  [interface-name & specs]
  (let [interface  (eval interface-name)

        ; Reflect on the visitor interface
        reflection (reflect interface)

        ; Index the interface methods by name
        iface-methods (->> reflection
                        :members
                        (map (fn [m] [(:name m) m]))
                        (into {}))

        ; Translate provided specs into reify specs
        reify-specs (->> specs
                      (map (fn [[node- [this- ctx-] & body]]
                             (let [spec-name (symbol (str "visit" node-))
                                   method    (get iface-methods spec-name)]

                               ; Pull up red leader!
                               (when-not method
                                 (throw (IllegalArgumentException.
                                          (str "Can't override nonexistent method " 
                                               spec-name
                                               " of interface "
                                               interface-name))))

                               ; Reify method spec
                               `(~spec-name [~this- ~ctx-] ~@body)))))

        ; Fill in unfulfilled methods for the interface
        provided-spec-names (set (map first reify-specs))
        default-specs  (->> reflection
                         :members
                         (remove (comp provided-spec-names :name))
                         ; Sort for convenience in reading code
                         (sort-by :name)
                         (map degenerate-visitor-spec))]

    `(reify ~interface-name
       ~@reify-specs
       ~@default-specs

       ; Adapted from http://www.antlr.org/api/Java/org/antlr/v4/runtime/tree/AbstractParseTreeVisitor.html
       (visit [~'this ~'tree] (.accept ~'tree ~'this))

       (visitChildren [~'this ~'node]
                      (map (partial visit ~'this) (children ~'node)))

       (visitTerminal [~'this ~'node])

       (visitErrorNode [~'this ~'node]))))

(defmacro visit-string
  "Given a lexer class, parser class, a visitor, and a string, tokenizes,
  parses, and visits the string, returning the result of the visitor.
  
  Example:
  
  (visit-string JsonLexer JsonParser (JsonInitBaseVisitor.) .json \"[1,2,3]\""
  [lexer-class parser-class visitor root-node string]
  `(->> ~string
     (lexer ~lexer-class)
     tokens
     (parser ~parser-class)
     ~root-node
     (.visit ~(vary-meta visitor assoc :tag `ParseTreeVisitor))))
