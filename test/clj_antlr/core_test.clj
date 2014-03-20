(ns clj-antlr.core-test
  (:import clj_antlr.ParseError)
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

(deftest string-grammar-test
  (let [cadr (parser (slurp "grammars/Cadr.g4"))]
    (is (= (cadr "cdr")
           '(:cadr "c" "d" "r")))))

(deftest error-test
  (let [json (parser "grammars/Json.g4")]
    (testing "throws on tokenization errors"
      (let [^ParseError err (try (parse json "[1, \uf000, 2]")
                                 (catch ParseError e e))]
        (is (= (.getMessage err) "token recognition error at: ''\nextraneous input ',' expecting {'null', '{', '[', 'false', 'true', NUMBER, STRING}"))))

    (testing "throws on recognition errors"
      (let [^ParseError err (try (parse json "[1,2,,5,]")
                                 (catch ParseError e e))]
        (is (= (.getMessage err)
               "extraneous input ',' expecting {'null', '{', '[', 'false', 'true', NUMBER, STRING}
mismatched input ']' expecting {'null', '{', '[', 'false', 'true', NUMBER, STRING}"))
        (is (= (map :line @err) [1 1]))
        (is (= (map :char @err) [5 8]))))

    (testing "parsing invalid text without throwing"
      (let [t (parse json {:throw? false} "[1,2,cat]")]
        (is (= t '(:jsonText
                    (:clj-antlr/error
                      (:jsonArray
                        "["
                        (:jsonValue (:jsonNumber "1"))
                        ","
                        (:jsonValue (:jsonNumber "2"))
                        ","
                        (:clj-antlr/error (:jsonValue)))))))
        (is (= (first (:errors (meta t)))
               {:token nil,
                :expected nil,
                :state -1,
                :rule nil,
                :symbol nil,
                :line 1,
                :char 5,
                :message "token recognition error at: 'c'"}))))

    (testing "skipping garbage at end of parse"
      (let [t (parse json {:throw? false} "[1,2,3] foo {}")]
        (is (= t '(:jsonText
                    (:jsonArray
                      "["
                      (:jsonValue (:jsonNumber "1"))
                      ","
                      (:jsonValue (:jsonNumber "2"))
                      ","
                      (:jsonValue (:jsonNumber "3"))
                      "]"))))))))

(deftest error-tagging-test
  "Should produce maximally valid trees, with errors constrained where recovery
  is possible."
  (let [json (parser "grammars/Json.g4" {:throw? false})
        tree (json   "[1, {\"sub\": map}, 4]")]
    (is (= tree
           '(:jsonText
              (:jsonArray
                "["
                (:jsonValue (:jsonNumber "1"))
                ","
                (:jsonValue
                  (:jsonObject
                    "{"
                    (:member "\"sub\"" ":" (:clj-antlr/error (:jsonValue)))
                    "}"))
                ","
                (:jsonValue (:jsonNumber "4"))
                "]"))))))

(deftest case-insensitive-test
  (let [cadr (parser "grammars/Cadr.g4")]
    (is (thrown? ParseError (parse cadr "CAR")))
    (is (= (parse cadr {:case-sensitive? false} "CAR")
           '(:cadr "C" "A" "R")))))

(deftest format-test
  (let [cadr (parser "grammars/Cadr.g4")]
    (is (= (set (keys (parse cadr {:format :raw} "caddr")))
           #{:tree :parser :errors}))))
