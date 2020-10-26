(ns clj-antlr-demo.edn
  "An example EDN parser."
  (:require [clojure.string :as s]
            [common.clj :refer [child text visit visit-string]])
  (:import EdnVisitor
           EdnLexer
           EdnParser))

(defn parse-unicode-escape
  "Takes a string referring to a unicode codepoint like \"u0a2f\"
  and returns the corresponding char."
  [^String s]
  (char (Integer/parseInt (subs s 1)
                          16)))

(defn parse-char
  "Takes a character string and parses it into a char."
  [^String s]
  ; Drop initial \
  (let [s (subs s 1)]
    (if (= 1 (.length s))
      ; Standard 1-char escape.
      (first s)

      ; Extended escapes
      (case (first s)
        \s \space
        \n \newline
        \r \return
        \t \tab
        \u (parse-unicode-escape s)))))

(defn parse-edn-string
  "Takes an EDN string and parses it."
  [^String s]
  (-> s
    (subs 1 (dec (.length s)))
    (s/replace #"\\(u(.{4})|.)"
             (fn [match]
               (condp = (first (nth match 1))
                 \u (str (parse-unicode-escape (nth match 1)))
                 \" "\""
                 \\ "\\"
                 \/ "/"
                 \b "\b"
                 \f "\f"
                 \n "\n"
                 \r "\r"
                 \t "\t")))))

(defn parse-int
  "Takes an EDN int and parses it."
  [^String s]
  (if (= \N (last s))
    (read-string s) ; cop-out
    (Long. s)))

(declare Edn Element True False Nil Symbol Keyword List Vector Set Map)
(declare visitor this ctx c)

(def v
  (visitor EdnVisitor
           (Edn [this ctx] (map (partial visit this)
                                (.element ctx)))
           (Element   [v c] (visit v (child c 0)))
           (True      [v c] true)
           (False     [v c] false)
           (Nil       [v c] nil)
           (Character [v c] (parse-char (text c)))
           (String    [v c] (parse-edn-string (text c)))
           (Symbol    [v c] (symbol (text c)))
           (Keyword   [v c] (keyword (subs (text c) 1)))
           (Integer   [v c] (parse-int (text c)))
           (Float     [v c] (read-string (text c)))
           (List      [v c] (map (partial visit v) (.element c)))
           (Vector    [v c] (mapv (partial visit v) (.element c)))
           (Set       [v c] (set (map (partial visit v) (.element c))))
           (Map       [v c] (apply hash-map
                                   (map (partial visit v) (.element c))))

           ))

(defn parse-string
  [s]
  (visit-string EdnLexer EdnParser v .edn s))
