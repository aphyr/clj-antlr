(ns clj-antlr.proto)

(defprotocol Parser
  (parse [parser opts input]))
