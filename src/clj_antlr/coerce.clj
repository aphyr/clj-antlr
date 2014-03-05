(ns clj-antlr.coerce
  (:require [clj-antlr.common :as c])
  (:import (org.antlr.v4.runtime.tree TerminalNode
                                      ParseTree)
           (org.antlr.v4.runtime ParserRuleContext
                                 Parser)))

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

(defn sexpr [^ParseTree t ^Parser p]
  (if (instance? ParserRuleContext t)
    (cons (->>
            (.getRuleIndex ^ParserRuleContext t)
            (c/parser-rule-name p)
            c/fast-keyword)
          (doall (map #(sexpr % p) (c/children t))))
    (.getText t)))

(defn tree->sexpr
  "Takes a map with a :tree node and a :parser (required for interpreting the
  indices of rule nodes as rule names), and returns a lazily evaluated tree,
  where each tree is either a string, or a sequence composed of a rule name
  followed by that rule's child trees. For instance:

  (:json (:object \"{\" (:pair \"age\" \":\" (:value \"53\"))))"
  [m]
  (sexpr (:tree m) (:parser m)))
