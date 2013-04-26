(ns clj-antlr.core
  (:import (org.antlr.v4.runtime ANTLRInputStream
                                 CommonTokenStream)
           (org.antlr.v4.runtime.tree ParseTree
                                      ParseTreeWalker)
           ArrayInitLexer
           ArrayInitBaseVisitor
           ArrayInitParser))

(defn input-stream
  "Constructs an ANTLRInputStream for something."
  [s]
  (ANTLRInputStream. s))

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
  "Visits a parse tree with a visitor."
  [visitor tree]
  (.visit visitor tree))

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

;  [lexer-class parser-class visitor]
;  (let [input  (ANTLRInputStream. s)
;        lexer  (ArrayInitLexer. input)
;        tokens (CommonTokenStream. lexer)
;        parser (ArrayInitParser. tokens)
;        tree (.json parser)]
;    (.visit visitor tree)))
