(ns clj-antlr.common
  "Common functions for building and using parsers."
  (:require [clojure.string :as string])
  (:import (java.io InputStream
                    Reader)
           (java.util.concurrent ConcurrentHashMap)
           (clj_antlr ParseError)
           (org.antlr.v4.runtime ANTLRInputStream
                                 CaseChangingCharStream
                                 CaseInsensitiveInputStream
                                 CharStreams
                                 CommonTokenStream
                                 Parser
                                 Recognizer
                                 ANTLRErrorListener
                                 RecognitionException)
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

(defn char-stream-from-input
  "Constructs a CharStream out of a String, Reader, or InputStream."
  [s]
  (condp instance? s
    InputStream (CharStreams/fromStream s)
    Reader (CharStreams/fromReader s)
    String (CharStreams/fromString s)))

(defmacro multi-hinted-let
  "Deprecated, since antlr-input-stream is deprecated.
  A let expression which expands into multiple type-hinted bodies with runtime
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

(defn antlr-input-stream
  "Deprecated in favor of char-stream.
  Constructs an ANTLRInputStream out of a String, Reader, or InputStream."
  [s]
  (multi-hinted-let [hinted s [InputStream Reader String]]
                    (ANTLRInputStream. hinted)))

(defn case-changing-char-stream
  "Wraps a CharStream in a CaseChangingCharStream. Called with one argument,
  defaults to lowercasing all input, but the upper? argument allows for case to be chosen.
  Adapted from https://github.com/parrt/antlr4/blob/case-insensitivity-doc/doc/resources/CaseChangingCharStream.java."
  ([charstream]
   (case-changing-char-stream charstream false))
  ([charstream upper?]
   (CaseChangingCharStream. charstream upper?)))


(defn case-insensitive-input-stream
  "Deprecated in favor of case-changing-char-stream.
  Constructs an ANTLRInputStream out of a string. Presents all characters to
  the lexer as lowercase, but getText methods will return the original string.
  Adapted from https://gist.github.com/sharwell/9424666. Consumes memory
  proportional to the input string."
  [input]
  (CaseInsensitiveInputStream. input))

(defn char-stream
  "Constructs a charstream. With no options, calls char-stream-from-input. With
  options:

  :case-sensitive? true calls char-stream-from-input,
                   false calls case-changing-char-stream"
  ([s] (char-stream-from-input s))
  ([s opts]
   (if (get opts :case-sensitive? true)
     (char-stream-from-input s)
     (case-changing-char-stream (char-stream-from-input s) false))))


(defn input-stream
  "Deprecated in favor of char-stream.
  Constructs an inputstream. With no options, calls antlr-input-stream. With
  options:

  :case-sensitive? true calls antlr-input-stream,
                   false calls case-insensitive-input-stream"
  ([s] (antlr-input-stream s))
  ([s opts]
   (if (get opts :case-sensitive? true)
     (antlr-input-stream s)
     (case-insensitive-input-stream s))))

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

(defn rule-index
  "Given a grammar and the name of a rule, returns the integer index of that
  rule."
  [^Grammar grammar rule-name]
  (let [rule (.getRule grammar ^String (name rule-name))]
    (when-not rule
      (throw (RuntimeException. (str "No such rule: " (pr-str rule-name)))))
    (.index rule)))

(defn parser-rule-name
  "Given a parser and an integer rule index, returns the string name of that
  rule. Negative indexes map to nil."
  [^Parser parser ^long index]
  (when-not (neg? index)
    (aget (.getRuleNames parser) index)))

(defn token-name
  "Given a parser and a token index, returns the string name of that token.
  Negative indexes map to nil."
  [^Parser parser ^long index]
  (when-not (neg? index)
    (.getDisplayName (.getVocabulary parser) index)))

(defn parse-error
  "Constructs a new ParseError exception with a list of errors."
  [errors tree]
  (ParseError. errors
               tree
               (string/join "\n" (map :message errors))))

(defn recognition-exception->map
  "Converts a RecognitionException to a nice readable map."
  [^RecognitionException e]
  {:rule     (.getCtx e)
   :state    (.getOffendingState e)
   :expected (try (.getExpectedTokens e)
                  (catch IllegalArgumentException e
                    ; I think ANTLR throws here for
                    ; tokenizer errors.
                    nil))
   :token    (.getOffendingToken e)})

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
                    (merge err (recognition-exception->map e))
                    err)]
        (swap! errors conj err))))))
