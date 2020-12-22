(ns clj-antlr.core
  "Parser frontend."
  (:require [clj-antlr.interpreted :as interpreted]
            [clj-antlr.coerce :as coerce]
            [clj-antlr.proto :as proto]))

(declare parse)

; Wraps an underlying parser with default opts.
(defrecord ParserWrapper [parser opts]
  proto/Parser
  (parse [_ opts text]
    (proto/parse parser opts text))

  clojure.lang.IFn
  (invoke [parser text]
    (parse parser text))
  (invoke [parser opts text]
          (parse parser opts text)))

(defn parse*
  "Helper for parse"
  [^ParserWrapper parser opts input]
  (let [formatter (condp = (:format opts)
                    nil    coerce/tree->sexpr
                    :sexp  coerce/tree->sexpr
                    :raw   identity
                           (:format opts))]
    (formatter
      (proto/parse parser opts input))))

(defn parse
  "Parses a string, reader, or inputstream using the given parser, and returns
  a data structure. If options are passed, override the options given at parser
  construction."
  ([^ParserWrapper parser input]
   (parse* parser (:opts parser) input))
  ([^ParserWrapper parser opts input]
   (parse* parser (merge (:opts parser) opts) input)))

(defn tokens
  "Instead of a parse tree, yields a sequence of tokens."
  ([^ParserWrapper parser input]
   (tokens parser {} input))
  ([^ParserWrapper parser opts input]
   (let [p (parse parser (assoc opts :format identity) input)]
     (coerce/tokens->sexpr (:parser p) (:tokens p)))))

(defn parser
  "Constructs a new parser. Takes a filename for an Antlr v4 grammar. Options:

  :format           The parse tree to generate. One of
                      :sexp (default)  Nested lists, node names first
                      :raw             Equivalent to identity
                      <any function>   Takes a map of {:tree, :parser, etc}

  :root             The string name of the rule to begin parsing. Defaults to
                    the first rule in the grammar.

  :throw?           If truthy, parse errors will be thrown. Defaults true.

  :case-sensitive?  Whether the lexer must match the exact case of characters.
                    Defaults true. If false, the tokenizer will only receive
                    lowercase characters. The generated parse tree will still
                    retain the case of the original text.
   
  :use-alternates?  If truthy, uses the alternate name for a node, rather than
                    the rule name. Defaults false."
  ([filename]
   (parser filename {}))
  ([filename opts]
   (ParserWrapper. (interpreted/parser filename) opts))
  ([lexer-filename parser-filename opts]
   (ParserWrapper. (interpreted/parser lexer-filename parser-filename) opts)))
