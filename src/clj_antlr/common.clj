(ns clj-antlr.common
  "Common functions for building and using parsers."
  (:require [clojure.string :as string])
  (:import (java.io InputStream
                    Reader)
           (java.util.concurrent ConcurrentHashMap)
           (clj_antlr ParseError)
           (org.antlr.v4.runtime ANTLRInputStream
                                 CommonTokenStream
                                 Parser
                                 Recognizer
                                 ANTLRErrorListener)
           (org.antlr.v4.tool Grammar)
           (org.antlr.v4.runtime.tree Tree
                                      ParseTree
                                      ParseTreeWalker
                                      ParseTreeVisitor)))

(def ^ConcurrentHashMap fast-keyword-cache
  "A map of strings to keywords."
  (ConcurrentHashMap. 1024))

(defn fast-keyword
  "Like (keyword str), but faster."
  [s]
  (or (.get fast-keyword-cache s)
      (let [k (keyword s)]
        (if (< 1024 (.size fast-keyword-cache))
          k
          (do
            (.put fast-keyword-cache s k)
            k)))))

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

(defn tokens
  "A token stream taken from a lexer."
  [lexer]
  (CommonTokenStream. lexer))

(defn visit
  "Visits a node with a visitor."
  [^ParseTreeVisitor visitor node]
  (when-not (nil? node)
    (.visit visitor node)))

(defn child
  "Get a specific child in a tree."
  [^Tree node i]
  (.getChild node i))

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

(defn first-rule
  "The name of the first rule in a grammar."
  [^Grammar grammar]
  (aget (.getRuleNames grammar) 0))

(defn parser-rule-name
  "Given a parser and an integer rule index, returns the string name of that
  rule."
  [^Parser parser ^long index]
  (aget (.getRuleNames parser) index))

(defn parse-error
  "Constructs a new ParseError exception with a list of errors."
  [errors tree]
  (ParseError. errors
               tree
               (string/join "\n" (map :message errors))))

(defn error-listener
  "A stateful error listener which accretes parse errors in a deref-able
  structure. Deref returns nil if there are no errors; else a sequence of
  heterogenous maps, depending on what debugging information is available."
  []
  (let [errors (atom [])]
    (reify
      clojure.lang.IDeref
      (deref [this] (seq (deref errors)))

      ANTLRErrorListener
      (reportAmbiguity [this
                        parser
                        dfa
                        start-index
                        stop-idex
                        exact
                        ambig-alts
                        configs]
        ; TODO
        )

      (reportAttemptingFullContext [this
                                    parser
                                    dfa
                                    start-index
                                    stop-index
                                    conflicting-alts
                                    configs])

      (reportContextSensitivity [this
                                 parser
                                 dfa
                                 start-index
                                 stop-index
                                 prediction
                                 configs])

      (syntaxError [this
                    recognizer
                    offending-symbol
                    line
                    char
                    message
                    e]
        (let [err {:symbol   offending-symbol
                   :line     line
                   :char     char
                   :message  message}
              err (if (isa? Parser recognizer)
                    (assoc err :stack (->> ^Parser recognizer
                                           .getRuleInvocationStack
                                           reverse))
                    err)
              err (if e
                    (assoc err
                           :rule     (.getCtx e)
                           :state    (.getOffendingState e)
                           :expected (try (.getExpectedTokens e)
                                          (catch IllegalArgumentException e
                                            ; I think ANTLR throws here for
                                            ; tokenizer errors.
                                            nil))
                           :token    (.getOffendingToken e))
                    err)]
        (swap! errors conj err))))))
