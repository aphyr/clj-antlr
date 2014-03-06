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
  a data structure."
  [^Parser parser input]
  (coerce/tree->sexpr
    (if-let [root (:root (.opts parser))]
      (interpreted/parse (.grammar parser) input root)
      (interpreted/parse (.grammar parser) input))))

(defn parser
  "Constructs a new parser. Takes a filename for an Antlr v4 grammar."
  ([filename]
   (parser filename {}))
  ([filename opts]
   (Parser. (interpreted/grammar filename) opts)))
