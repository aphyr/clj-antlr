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

(defn string->lexer-grammar
  "Parses a lexer grammar into a LexerGrammar object.

  (lexer-grammar \"lexer grammar L;
  A : 'a' ;
  B : 'b' ;
  C : 'c' ;\")"
  [grammar-string]
  (LexerGrammar. grammar-string))

(defn string->parser-grammar
  "Parses a parser grammar into a Grammar object, given a lexer grammar.

  (parser-grammar (lexer-grammar \"...\") \"grammar T;\n s : (A|B)* C ;\")"
  [lexer-grammar parser-grammar-string]
  (Grammar. parser-grammar-string lexer-grammar))

(defn grammar
  "Loads a Grammar from a file."
  [filename]
  (Grammar/load filename))

(defn parse
  "Given a Grammar, the string name of a root node to start with, and an input
  (string, reader, or inputstream), returns the StringTree for the input."
  [grammar root input]
  (let [tokens (-> grammar
                   (.createLexerInterpreter (common/input-stream input))
                   (CommonTokenStream.))
        rule   (-> grammar (.getRule root) .index)
        parser (.createParserInterpreter grammar tokens)]
    {:tree (.parse parser rule)
     :parser parser}))
