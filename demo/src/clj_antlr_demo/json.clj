(ns clj-antlr-demo.json
  "An example JSON parser."
  (:use clj-antlr.core)
  (:require [clojure.string :as s])
  (:import JsonBaseVisitor
           JsonVisitor
           JsonLexer
           JsonParser))

(defn read-json-string
  "Takes a JSON string and parses it."
  [^String s]
  (-> s
    (subs 1 (dec (.length s)))
    (s/replace #"\\(u(.{4})|.)"
             (fn [match]
               (condp = (first (nth match 1))
                 \u (str (char (Integer/parseInt (nth match 2) 16)))
                 \" "\""
                 \\ "\\"
                 \/ "/"
                 \b "\b"
                 \f "\f"
                 \n "\n"
                 \r "\r"
                 \t "\t")))))

(def v
  (visitor JsonVisitor
    (JsonObject [t c]
                     (visit t (.object c)))
    (JsonArray [t c]
                    (visit t (.array c)))
    (Object [t c]
                 (into {} (map (partial visit t) (.pair c))))
    (Pair [t c]
               [(keyword (read-json-string (text (.STRING c))))
                (visit t (.value c))])
    (Array       [t c] (map (partial visit t) (.value c)))
    (ValueString [t c] (read-json-string (text c)))
    (ValueNumber [t c] (read-string (text c)))
    (ValueObject [t c] (visit t (.object c)))
    (ValueArray  [t c] (visit t (.array c)))
    (ValueTrue   [t c] true)
    (ValueFalse  [t c] false)
    (ValueNull   [t c] nil)))

(defn parse-string
  [s]
  (visit-string JsonLexer JsonParser v .json s))
