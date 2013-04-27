(ns clj-antlr-demo.bin
  (:gen-class)
  (:require [cheshire.core :as ch])
  (:use criterium.core
        clojure.java.io
        clj-antlr-demo.json))

(defn -main
  "Parse a JSON file."
  [file]
;  (dotimes [i 10000000]
;    (with-open [r (reader file)]
;      (parse-string r)))

  (println "Antlr parser")
  (with-progress-reporting
    (bench
      (with-open [r (reader file)]
        (parse-string r))))

  (println)
  (println)
  (println "Cheshire")
  (with-progress-reporting
    (bench
      (with-open [r (reader file)]
        (ch/parse-stream r true)))))
