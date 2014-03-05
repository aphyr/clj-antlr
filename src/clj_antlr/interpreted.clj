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
  "Given a Grammar, the string name of a root node to start with, and an input
  (string, reader, or inputstream), returns the StringTree for the input."
  [^Grammar grammar input ^String root]
  (let [tokens (-> grammar
                   (.createLexerInterpreter (common/input-stream input))
                   (CommonTokenStream.))
        rule   (.index (.getRule grammar root))
        parser (.createParserInterpreter grammar tokens)]
    {:tree (.parse parser rule)
     :parser parser}))
