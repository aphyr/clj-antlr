(ns clj-antlr.interpreted-test
  (:use clj-antlr.interpreted
        clj-antlr.coerce
        clojure.test
        clojure.pprint))

(deftest interprets-json
  (-> "demo/src/java/Json.g4"
      grammar
      (parse "json" "{\"nums\": [1,2,3]}")
      ->hiccup
      pprint))
