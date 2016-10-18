(ns clj-antlr.coerce
  (:require [clj-antlr.common :as c])
  (:import (org.antlr.v4.runtime.tree TerminalNode
                                      ParseTree)
           (org.antlr.v4.runtime ParserRuleContext
                                 Parser
                                 Token
                                 CommonTokenStream)))

;(defprotocol Sexpr
;  "Coerces trees to hiccup-style structures."
;  (sexpr [^ParseTree tree ^Parser p]))
;
;(extend-protocol Sexpr
;  TerminalNode
;  (sexpr [t p] (.getText t))
;
;  ParserRuleContext
;  (sexpr [t p] (cons (keyword (c/parser-rule-name p (.getRuleIndex t)))
;                     (doall (map #(sexpr % p) (c/children t))))))

(defn- sexpr-head
  [^ParserRuleContext t ^Parser p]
  (->> (.getRuleIndex ^ParserRuleContext t)
       (c/parser-rule-name p)
       c/fast-keyword))

(defn- maybe-error-node
  [^ParserRuleContext t node]
  ; If there was a recognition error, we'll wrap the node in an ::error
  ; and drop the exception in the metadata.
  (if-let [e (.exception ^ParserRuleContext t)]
    (with-meta (list :clj-antlr/error node)
               {:error (c/recognition-exception->map e)})
    node))


(defn- literal->sexpr
  [^ParseTree t]
  (.getText t))

(defn sexpr [^ParseTree t ^Parser p]
  (if (instance? ParserRuleContext t)
    (->> (mapv #(sexpr % p) (c/children t))
         (cons (sexpr-head t p))
         (maybe-error-node t))
    (literal->sexpr t)))

(defn tokens->sexpr
  "Takes a Parser and a CommonTokenStream and emits a lazy sequence of
  (:SOME_TYPE \"str\") pairs for the tokens in the stream."
  [^Parser parser ^CommonTokenStream tokens]
  (->> tokens
       .getTokens
       (map (fn [^Token t]
              (list (c/token-name parser (.getType t))
                    (.getText t))))))

(defn tree->sexpr
  "Takes a map with a :tree node and a :parser (required for interpreting the
  indices of rule nodes as rule names), and returns a lazily evaluated tree,
  where each tree is either a string, or a sequence composed of a rule name
  followed by that rule's child trees. For instance:

  (:json (:object \"{\" (:pair \"age\" \":\" (:value \"53\"))))"
  [m]
  (vary-meta (sexpr (:tree m) (:parser m))
             assoc :errors (:errors m)))
