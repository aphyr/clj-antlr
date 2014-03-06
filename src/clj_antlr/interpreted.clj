(ns clj-antlr.interpreted
  "Interpreter for antlr grammars. Slightly slower, but easier to use than the
  full antlr compilation process."
  (:require [clj-antlr.common :as common])
  (:import (org.antlr.v4.tool LexerGrammar
                              Grammar)
           (org.antlr.v4.runtime CommonTokenStream
                                 LexerInterpreter
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
   (let [tokens (-> grammar
                    (.createLexerInterpreter (common/input-stream input))
                    (CommonTokenStream.))
         rule   (.index (.getRule grammar root))
         parser (.createParserInterpreter grammar tokens)]
     {:tree (.parse parser rule)
      :parser parser})))
