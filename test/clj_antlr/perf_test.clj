(ns clj-antlr.perf-test
  (:require [clj-antlr.core :refer [parser]]
            [clojure.test :refer [deftest is]]
            [criterium.core :as criterium]
            [instaparse.core :as insta]))

;#_:clj-kondo/ignore
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
