(ns clj-antlr-demo.t-json
  (:use midje.sweet
        clj-antlr-demo.json))

(facts read-json-string
       (fact (read-json-string "\"hi\"") => "hi")
       (fact (read-json-string "\"a new \\n line\"") => "a new \n line")
       (fact (read-json-string "\"\\\\ \\/ \\\" \\b \\f \\n \\r \\t\"") =>
             "\\ / \" \b \f \n \r \t")
       (fact (read-json-string "\"\\uaab0\"") => "\uaab0"))

(facts "JSON"
       (fact (parse-string "[]")     => [])
       (fact (parse-string "[true]") => [true])
       (fact (parse-string "[true, false]") => [true false])
       (fact (parse-string "[true,false]") => [true false])
       (fact (parse-string "[true,false, null]") => [true false nil])
       (fact (parse-string "[1]") => [1])
       (fact (parse-string "[-1]") => [-1])
       (fact (parse-string "[1.23]") => [1.23])
       (fact (parse-string "[-2e32]") => [-2e32])
       (fact (parse-string "[\"ᚠᛇᚻ᛫ᛒᛦᚦ᛫ᚠᚱᚩᚠᚢᚱ᛫ᚠᛁᚱᚪ᛫ᚷᛖᚻᚹᛦᛚᚳᚢᛗ\"]") =>
                             ["ᚠᛇᚻ᛫ᛒᛦᚦ᛫ᚠᚱᚩᚠᚢᚱ᛫ᚠᛁᚱᚪ᛫ᚷᛖᚻᚹᛦᛚᚳᚢᛗ"])
       (fact (parse-string "{}")     => {})
       (fact (parse-string "{\"foo\": true, \"bar\": [1,2,3]}") =>
                            {:foo true :bar [1 2 3]})
       )
