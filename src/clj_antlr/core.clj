(ns clj-antlr.core
  "Parser frontend."
  (:require [clj-antlr.interpreted :as interpreted]
            [clj-antlr.coerce :as coerce]))

(declare parse)

(defrecord Parser [grammar opts]
  clojure.lang.IFn
  (invoke [parser text]
    (parse parser text)))

(defn parse*
  "Helper for parse"
  [parser opts input]
  (let [formatter (condp = (:format opts)
                    nil    coerce/tree->sexpr
                    :sexp  coerce/tree->sexpr
                    :raw   identity
                           (:format opts))]
    (formatter
      (interpreted/parse (.grammar parser)
                         opts
                         input))))

(defn parse
  "Parses a string, reader, or inputstream using the given parser, and returns
  a data structure. If options are passed, override the options given at parser
  construction."
  ([^Parser parser input]
   (parse* parser (.opts parser) input))
  ([^Parser parser opts input]
   (parse* parser (merge (.opts parser) opts) input)))

(defn parser
  "Constructs a new parser. Takes a filename for an Antlr v4 grammar. Options:

  :format           The parse tree to generate. One of:
                      :sexpr (default)  Nested lists, node names first
                      :raw              Equivalent to identity
                      <any function>    Takes a map of {:tree, :parser, etc}

  :root             The string name of the rule to begin parsing. Defaults to
                    the first rule in the grammar.

  :throw?           If truthy, parse errors will be thrown. Defaults true.

  :case-sensitive?  Whether the lexer must match the exact case of characters.
                    Defaults true. If false, the tokenizer will only receive
                    lowercase characters. The generated parse tree will still
                    retain the case of the original text."
  ([filename]
   (parser filename {}))
  ([filename opts]
   (Parser. (interpreted/grammar filename) opts)))
