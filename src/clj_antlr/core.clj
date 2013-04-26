(ns clj-antlr.core
  (:use clojure.reflect)
  (:import (java.io InputStream
                    Reader)
           (org.antlr.v4.runtime ANTLRInputStream
                                 CommonTokenStream)
           (org.antlr.v4.runtime.tree ParseTree
                                      ParseTreeWalker)))

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
  [visitor node]
  (.visit visitor node))

(defn child-count
  "How many children does a node have?"
  [node]
  (.getChildCount node))

(defn children
  "Returns the children of a RuleNode."
  [node]
  (map #(.getChild node %)
       (range (child-count node))))

(defn parent
  "The parent of a node."
  [node]
  (.getParent node))

(defn text
  "The text of a node."
  [node]
  (.getText node))

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
                         (map (fn [m]
                                `(~(:name m) [~'this ~'ctx]))))]
                         

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
     (.visit ~visitor)))
