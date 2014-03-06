(ns clj-antlr.core-test
  (:use clj-antlr.core
        clojure.test
        clojure.pprint))

(deftest interprets-json
  (let [json (parser "grammars/Json.g4")
        sexpr (json "{\"nums\": [1,2,3]}")]
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
