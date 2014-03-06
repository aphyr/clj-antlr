(ns clj-antlr.perf-test
  (:use clj-antlr.core
        clojure.test
        clojure.pprint)
  (:require [criterium.core :as criterium]
            [instaparse.core :as insta]))

(deftest ^:perf perf-test
  (let [clj-antlr (parser "grammars/Json.g4")
        insta (insta/parser "grammars/json.instaparse")
        blob (slurp "data/test.json")]

    (is (= (insta blob) (clj-antlr blob)))

    (println "Benchmarking instaparse")
    (criterium/bench (insta blob))

    (println "\n\n")

    (println "Benchmarking clj-antlr")
    (criterium/bench (clj-antlr blob))))
