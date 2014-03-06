(ns clj-antlr.core-test
  (:use clj-antlr.core
        clojure.test
        clojure.pprint)
  (:require [criterium.core :as criterium]
            [instaparse.core :as insta]))

(deftest perf
  (let [json (parser "grammars/Json.g4")
        blob (slurp "data/test.json")]
    (criterium/bench (json blob))))

(deftest interprets-json
  (let [json (parser "grammars/Json.g4")
        sexpr (json "{\"nums\": [1,2,3]}")]
    (pprint sexpr)
    (is (= sexpr
           '(:jsonText
              (:jsonObject
                "{"
                (:member
                  "\"nums\""
                  ":"
                  (:jsonValue
                    (:jsonArray
                      "["
                      (:jsonValue (:jsonNumber "1"))
                      ","
                      (:jsonValue (:jsonNumber "2"))
                      ","
                      (:jsonValue (:jsonNumber "3"))
                      "]")))
                "}"))))))
