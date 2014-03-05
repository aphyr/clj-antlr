(ns clj-antlr.interpreted-test
  (:use clj-antlr.interpreted
        clj-antlr.coerce
        clojure.test
        clojure.pprint))

(deftest interprets-json
  (let [grammar (grammar "demo/src/java/Json.g4")
        sexpr (-> grammar
                  (parse "{\"nums\": [1,2,3]}" "jsonText")
                  tree->sexpr)]
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
