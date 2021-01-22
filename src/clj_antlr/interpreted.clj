(ns clj-antlr.interpreted
  "Interpreter for antlr grammars. Slightly slower, but easier to use than the
  full antlr compilation process."
  (:require [clj-antlr.common :as common]
            [clj-antlr.proto  :as proto])
  (:import (java.lang ThreadLocal)
           (org.antlr.v4 Tool)
           (org.antlr.v4.tool Grammar LexerGrammar)
           (org.antlr.v4.runtime Lexer
                                 LexerInterpreter
                                 ParserInterpreter)))

(defn ^Tool tool
  "Construct a new ANTLR tool"
  []
  (Tool.))

(defn load-string-grammar
  "Loads a grammar from a string."
  [string]
  (let [tool (tool)
        ast (.parseGrammarFromString tool string)
        grammar (.createGrammar tool ast)]
    (.process tool grammar false)
    grammar))

(defn ^Grammar grammar
  "Loads a Grammar from a string filename, or a string containing a grammar
  inline. If the string contains newlines, interprets as a grammar string;
  otherwise as a filename."
  [grammar-or-filename]
  (if (re-find #"\n" grammar-or-filename)
    (load-string-grammar grammar-or-filename)
    (Grammar/load grammar-or-filename)))

(defn rule-index
  "Finds the index of the given rule in a grammar. Throws if the rule is not
  present in the given grammar. Example

  (rule-index my-grammar :address)"
  [^Grammar grammar rule-name]
  (common/rule-index grammar (or rule-name (common/first-rule grammar))))

(defn lexer-interpreter
  "Builds a new lexer interpreter around an empty input stream. Used to
  initialize our parser, which will later reset and re-use this object
  for speed."
  [^Grammar grammar]
  (.createLexerInterpreter grammar (common/char-stream "")))

; (defn parser-interpreter
;   "Builds a new parser interpreter around a given grammar and lexer."
;   [^Grammar grammar ^Lexer lexer]
;   (.createParserInterpreter grammar (common/tokens lexer)))
;   
(defn parser-interpreter
  "Builds a new parser interpreter around a given grammar and lexer."
  [^Grammar grammar ^Lexer lexer]
  (.createGrammarParserInterpreter grammar (common/tokens lexer)))

(defn reset-lexer-interpreter!
  "Prepares a lexer interpreter for a new run."
  [^LexerInterpreter lexer error-listener char-stream]
  (doto lexer
    (.setInputStream char-stream)
    (.reset)
    (.removeErrorListeners)
    (.addErrorListener error-listener)))

(defn reset-parser-interpreter!
  "Prepares a parser interpreter for a new run."
  [^ParserInterpreter parser error-listener token-stream]
  (doto parser
    (.setTokenStream token-stream)
    (.reset)
    (.removeErrorListeners)
    (.addErrorListener error-listener)))

; Re-uses the same lexer and parser each time. Note that the :tokens and
; :parser returned by (parse) may be mutated at any time; they should only be
; used for static things like resolving token names.
(defrecord SinglethreadedParser [^Grammar grammar
                                 ^LexerInterpreter lexer
                                 ^ParserInterpreter parser]
  proto/Parser
  (parse [p opts text]
    (locking p ; lmao pretty sure returning tokens is a race condition
               ; waiting to happen
      (let [error-listener (common/error-listener)
            char-stream   (common/char-stream text opts)]
        (reset-lexer-interpreter! lexer error-listener char-stream)

        (let [tokens (common/tokens lexer)]
          (reset-parser-interpreter! parser error-listener tokens)

          (let [tree (.parse parser (rule-index grammar (:root opts)))]
            ; Throw errors unless requested not to
            (when-let [errors (and (get opts :throw? true)
                                   @error-listener)]
              (throw (common/parse-error errors tree)))

            {:tree   tree
             :tokens tokens
             :errors @error-listener
             :parser parser
             :grammar grammar
             :opts opts}))))))

; (defn singlethreaded-parser
;   "Creates a new single-threaded parser for a grammar."
;   [^Grammar grammar]
;   (let [^Lexer lexer (.createLexerInterpreter grammar (common/char-stream ""))
;         parser       (.createParserInterpreter grammar (common/tokens lexer))]
;  (SinglethreadedParser. grammar lexer parser)))
;  
(defn singlethreaded-parser
  "Creates a new single-threaded parser for a grammar."
  [^LexerGrammar lexer-grammar ^Grammar grammar]
  (println grammar)
  (let [^Lexer lexer (.createLexerInterpreter lexer-grammar (common/char-stream ""))
        parser       (.createGrammarParserInterpreter grammar (common/tokens lexer))]
    (SinglethreadedParser. grammar lexer parser)))

; Wrapper for using the singlethreaded parser in multiple threads.
(defrecord ThreadLocalParser [^ThreadLocal local lexer-grammar grammar]
  proto/Parser
  (parse [_ opts text]
    (let [parser (or (.get local)
                     (let [parser (singlethreaded-parser lexer-grammar grammar)]
                       (.set local parser)
                       parser))]
      (proto/parse parser opts text))))

(defn parser
  "Construct a new parser."
  ([filename]
   (let [grammar (grammar filename)
         lexer-grammar (.getImplicitLexer grammar)]
     (ThreadLocalParser. (ThreadLocal.) lexer-grammar grammar)))
  ([lexer-filename parser-filename]
   (let [lexer-grammar (grammar lexer-filename)
         grammar       (grammar parser-filename)]
     (ThreadLocalParser. (ThreadLocal.) lexer-grammar grammar))))
;   (singlethreaded-parser (grammar filename))))

(defn parse
  "Given a Grammar, options, and text to parse (a string, reader, or
  inputstream), returns a map of the :parser, :tree, and :errors for the
  input."
  ([^Grammar grammar opts input]
   (let [error-listener (common/error-listener)

         ; Root node to start at
         ^String root   (or (:root opts) (common/first-rule grammar))
         rule           (.getRule grammar root)
         _              (assert rule)
         rule           (.index rule)

         ; Char stream
         char-stream (if (get opts :case-sensitive? true)
                       (common/char-stream input)
                       (common/case-changing-char-stream input))

         ; Extract tokens
         ^Lexer lexer   (doto (.createLexerInterpreter grammar char-stream)
                          (.removeErrorListeners)
                          (.addErrorListener error-listener))
         tokens         (common/tokens lexer)

         ; Create parser
         ^ParserInterpreter parser (doto
                                    (.createParserInterpreter grammar tokens)
                                     (.removeErrorListeners)
                                     (.addErrorListener error-listener))
         ; Parse
         tree (.parse parser rule)]
       ; Throw errors unless requested not to
     (when-let [errors (and (get opts :throw? true)
                            @error-listener)]
       (throw (common/parse-error errors tree)))

     {:tree   tree
      :tokens tokens
      :errors @error-listener
      :parser parser
      :grammar grammar
      :opts opts})))
