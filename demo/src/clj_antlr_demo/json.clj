(ns clj-antlr-demo.json
  "An example JSON parser."
  (:use clj-antlr.core)
  (:import JsonBaseVisitor
           JsonLexer
           JsonParser))

(def visitor
  (proxy [JsonBaseVisitor] []
    (visitJsonObject [c]
                     (.visit this (.object c)))
    (visitJsonArray [c]
                    (.visit this (.array c)))
    (visitObject [c]
                 (into {} (map #(.visit this %) (.pair c))))
    (visitPair [c]
               [(keyword (read-string (.getText (.STRING c))))
                (.visit this (.value c))])
    (visitArray       [c] (map #(.visit this %) (.value c)))
    (visitValueString [c] (read-string (.getText c)))
    (visitValueNumber [c] (read-string (.getText c)))
    (visitValueObject [c] (.visit this (.object c)))
    (visitValueArray  [c] (.visit this (.array c)))
    (visitValueTrue   [c] true)
    (visitValueFalse  [c] false)
    (visitValueNull   [c] nil)))

(defn parse-string
  [s]
  (visit-string JsonLexer JsonParser visitor .json s))
