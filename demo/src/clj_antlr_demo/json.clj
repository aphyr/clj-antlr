(ns clj-antlr-demo.json
  "An example JSON parser."
  (:use clj-antlr.core)
  (:import JsonBaseVisitor
           JsonVisitor
           JsonLexer
           JsonParser))

(def v
  (visitor JsonVisitor
    (JsonObject [t c]
                     (visit t (.object c)))
    (JsonArray [t c]
                    (visit t (.array c)))
    (Object [t c]
                 (into {} (map (partial visit t) (.pair c))))
    (Pair [t c]
               [(keyword (read-string (text (.STRING c))))
                (visit t (.value c))])
    (Array       [t c] (map (partial visit t) (.value c)))
    (ValueString [t c] (read-string (text c)))
    (ValueNumber [t c] (read-string (text c)))
    (ValueObject [t c] (visit t (.object c)))
    (ValueArray  [t c] (visit t (.array c)))
    (ValueTrue   [t c] true)
    (ValueFalse  [t c] false)
    (ValueNull   [t c] nil)))

(defn parse-string
  [s]
  (visit-string JsonLexer JsonParser v .json s))
