(ns clj-antlr-demo.bin
  (:gen-class)
  (:require [cheshire.core :as ch :refer [bench]])
  (:import (java.io PushbackReader))
  (:require criterium.core
        clojure.java.io
        clj-antlr-demo.edn))

(declare with-progress-reporting reader parse-string)
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
  (println "Clojure reader")
  (with-progress-reporting
    (bench
      (with-open [r (PushbackReader. (reader file))]
        (read r)))))
