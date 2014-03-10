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
  "Given a Grammar, text to parse (a string, reader, or inputstream), and an
  optional root node to parse from, returns a map of the Parser and ParseTree
  for the input. If no root node is given, chooses the first rule from the
  grammar."
  ([grammar input]
   (parse grammar input (common/first-rule grammar)))
  ([^Grammar grammar input ^String root]
   (let [error-listener (common/error-listener)
         ; Extract tokens
         ^Lexer lexer   (doto (.createLexerInterpreter
                                grammar (common/input-stream input))
                          (.removeErrorListeners)
                          (.addErrorListener error-listener))
         tokens         (CommonTokenStream. lexer)
         ; Where do we start matching?
         rule           (.index (.getRule grammar root))
         ; Create parser
         ^ParserInterpreter parser (doto
                                     (.createParserInterpreter grammar tokens)
                                     (.removeErrorListeners)
                                     (.addErrorListener error-listener))]

     ; Parse
     (let [tree (.parse parser rule)]
       (when-let [errors (seq @error-listener)]
         (throw (common/parse-error errors tree)))

       {:tree   tree
        :parser parser}))))
