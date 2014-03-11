(ns clj-antlr.interpreted
  "Interpreter for antlr grammars. Slightly slower, but easier to use than the
  full antlr compilation process."
  (:require [clj-antlr.common :as common])
  (:import (org.antlr.v4.tool LexerGrammar
                              Grammar)
           (org.antlr.v4.runtime CommonTokenStream
                                 LexerInterpreter
                                 Parser
                                 ParserInterpreter)
           (org.antlr.v4.runtime.tree ParseTree)))

(defn grammar
  "Loads a Grammar from a file."
  [filename]
  (Grammar/load filename))

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

         ; Input stream
         input-stream (if (get opts :case-sensitive? true)
                        (common/input-stream input)
                        (common/case-insensitive-input-stream input))

         ; Extract tokens
         ^Lexer lexer   (doto (.createLexerInterpreter grammar input-stream)
                          (.removeErrorListeners)
                          (.addErrorListener error-listener))
         tokens         (CommonTokenStream. lexer)

         ; Create parser
         ^ParserInterpreter parser (doto
                                     (.createParserInterpreter grammar tokens)
                                     (.removeErrorListeners)
                                     (.addErrorListener error-listener))]

     ; Parse
     (let [tree (.parse parser rule)]
       ; Throw errors unless requested not to
       (when-let [errors (and (get opts :throw? true)
                              @error-listener)]
         (throw (common/parse-error errors tree)))

       {:tree   tree
        :errors @error-listener
        :parser parser}))))
