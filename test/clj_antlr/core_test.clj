(ns clj-antlr.core-test
  (:import clj_antlr.ParseError)
  (:require [clojure.test.check.generators :as gen]
            [cheshire.core :as cheshire])
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
        (is (= (.getMessage err) "token recognition error at: ''\nextraneous input ',' expecting {'false', 'null', 'true', '{', '[', NUMBER, STRING}"))))

    (testing "throws on recognition errors"
      (let [^ParseError err (try (parse json "[1,2,,5,]")
                                 (catch ParseError e e))]
        (is (= (.getMessage err)
               "extraneous input ',' expecting {'false', 'null', 'true', '{', '[', NUMBER, STRING}\nmismatched input ']' expecting {'false', 'null', 'true', '{', '[', NUMBER, STRING}"))
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
                        (:clj-antlr/error (:jsonValue "<EOF>"))
                        "<EOF>")))))
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
                (:jsonValue (:jsonObject "{" (:member "\"sub\"" ":" (:clj-antlr/error (:jsonValue "}"))) "}"))
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
           #{:tree :tokens :parser :errors}))))

(deftest ^:slow race-conditions
  (let [json    (parser "grammars/Json.g4")
        strings ["{\"foo\": [1, 2, 3]}"
                 "[4, 5, 6]"
                 "[1, true, false]"]
        objs    '[(:jsonText
                    (:jsonObject
                      "{" (:member
                            "\"foo\"" ":"
                            (:jsonValue
                              (:jsonArray
                                "[" (:jsonValue (:jsonNumber "1"))
                                "," (:jsonValue (:jsonNumber "2"))
                                "," (:jsonValue (:jsonNumber "3")) "]"))) "}"))
                  (:jsonText
                    (:jsonArray
                      "[" (:jsonValue (:jsonNumber "4"))
                      "," (:jsonValue (:jsonNumber "5"))
                      "," (:jsonValue (:jsonNumber "6")) "]"))
                  (:jsonText
                    (:jsonArray
                      "[" (:jsonValue (:jsonNumber "1"))
                      "," (:jsonValue "true")
                      "," (:jsonValue "false") "]"))]]
    (time
    (->> (range 100)
         (map (fn [_]
                (future
                  (dotimes [i 1000]
                    ; Parse trees
                    (is (= (map json strings)
                           (mapv json strings)
                           objs))

                    ; Tokens
                    (is (= (map (partial tokens json) strings)
                           (mapv (partial tokens json) strings)))))))
         doall
         (map deref)
         dorun))))

(deftest token-positions
  (let [json (parser "grammars/Json.g4")
        result (json "{\"foo\":\n \"bar\"}")
        position-of #(-> %
                         meta
                         :clj-antlr/position
                         ((juxt :row :column :index)))
        verify-object (fn [expected-object expected-position sexpr]
                        (is (= expected-object sexpr))
                        (is (= expected-position (position-of sexpr))))]

    ;; Top-Level Result should always be at (0,0)
    (verify-object
      '(:jsonText
         (:jsonObject
           "{"
           (:member
             "\"foo\"" ":"
             (:jsonValue
               (:jsonString
                 "\"bar\"")))
           "}"))
      [0 0 0]
      result)

    ;; "bar" is on the second line, indented by 1, overall at index 9
    (verify-object
      '(:jsonString "\"bar\"")
      [1 1 9]
      (-> result second (nth 2) last second))))
