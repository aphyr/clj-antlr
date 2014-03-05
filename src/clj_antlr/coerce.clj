(ns clj-antlr.coerce
  (:require [clj-antlr.common :as c])
  (:import (org.antlr.v4.runtime.tree TerminalNode
                                      ParseTree)
           (org.antlr.v4.runtime ParserRuleContext)))

(defprotocol Hiccup
  "Coerces trees to hiccup-style structures."
  (hiccup [^ParseTree tree ^Parser p]))

(extend-protocol Hiccup
  TerminalNode
  (hiccup [t p] (.getText t))

  ParserRuleContext
  (hiccup [t p] (cons (keyword (c/parser-rule-name p (.getRuleIndex t)))
                      (map #(hiccup % p) (c/children t)))))

(defn ->hiccup
  "Takes a map with a :tree node and a :parser (required for interpreting the
  indices of rule nodes as rule names), and returns a hiccup data structure."
  [m]
  (hiccup (:tree m) (:parser m)))
