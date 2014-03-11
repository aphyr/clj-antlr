(ns clj-antlr.core
  "Parser frontend."
  (:require [clj-antlr.interpreted :as interpreted]
            [clj-antlr.coerce :as coerce]))

(declare parse)

(defrecord Parser [grammar opts]
  clojure.lang.IFn
  (invoke [parser text]
    (parse parser text)))

(defn parse
  "Parses a string, reader, or inputstream using the given parser, and returns
  a data structure. If options are passed, override the options given at parser
  construction."
  ([^Parser parser input]
   (coerce/tree->sexpr
     (interpreted/parse (.grammar parser) (.opts parser) input)))
  ([^Parser parser opts input]
   (coerce/tree->sexpr
     (interpreted/parse (.grammar parser)
                        (merge (.opts parser)
                               opts)
                        input))))

(defn parser
  "Constructs a new parser. Takes a filename for an Antlr v4 grammar. Options:

  :root   The string name of the rule to begin parsing. Defaults to the
          first rule in the grammar.
  :throw? If truthy, parse errors will be thrown. Defaults true."
  ([filename]
   (parser filename {}))
  ([filename opts]
   (Parser. (interpreted/grammar filename) opts)))
