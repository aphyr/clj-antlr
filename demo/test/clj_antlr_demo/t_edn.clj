(ns clj-antlr-demo.t-edn
  (:use midje.sweet
        clj-antlr-demo.edn))

(facts "parse-string"
       (tabular
         (fact "booleans" (first (parse-string ?str)) => ?expected)
         ?str              ?expected
         "nil"               nil
         "true"              true
         "false"             false)

       (tabular
         (fact "chars" (first (parse-string ?str)) => ?expected)
         ?str         ?expected
         "\\newline"  \newline
         "\\return"   \return
         "\\space"    \space
         "\\tab"      \tab
         "\\n"        \n
         "\\r"        \r
         "\\a"        \a
         "\\b"        \b
         "\\0"        \0
         "\\1"        \1
         "\\u1234"    \u1234
         "\\u0000"    \u0000)

       (tabular
         (fact "strings" (first (parse-string (prn-str ?str))) => ?str)
         ?str         
         ""
         "hi"
         "hello there"
         "\\"
         "\""
         "\b\f\n\r\t"
         "گرامماتىكا")

       (fact "string escapes"
             (first (parse-string "\"\u1234\"")) => "\u1234")
       
       (tabular
         (fact "symbols" (first (parse-string ?str)) => ?expected)
         ?str     ?expected
         "a"        'a
         "ab"       'ab
         "a-"       'a-
         "a2"       'a2
         "-foo"     '-foo
         "ab/cd"    'ab/cd
         "a/*"      'a/*
         "f.a--/-"  'f.a--/-
         ".foo/.b"  '.foo/.b)
      
       (tabular
         (fact "keywords" (first (parse-string ?str)) => ?expected)
         ?str         ?expected
         ":foo"       :foo
         ":foo/bar"   :foo/bar
         ":a-b/c-d2"  :a-b/c-d2)

       (tabular
         (fact "integers" (first (parse-string ?str)) => ?expected)
         ?str     ?expected
         "0"      0
         "+010"   10
         "-010"   -10
         "1"      1
         "100"    100
         "100N"   100N)

       (tabular
         (fact "floats" (first (parse-string ?str)) => ?expected)
         ?str       ?expected
         "0.0"      0.0
         "1.23"     1.23
         "-4.5"     -4.5
         "10e4"     10e4
         "-1.25e-5" -1.25e-5
         "-2E5"     -2E5)

       (tabular
         (fact "lists" (first (parse-string ?str)) => ?expected)
         ?str                 ?expected
         "()"                 '()
         "(())"               '(())
         "(1)"                '(1)
         "(1 2)"              '(1 2)
         "(() ())"            '(() ())
         "(((() ()) ()) ())"  '(((() ()) ()) ()))

       (tabular
         (fact "vectors" (first (parse-string (prn-str ?s))) => ?s)
         ?s
         []
         [[]]
         [1]
         [1 2]
         [[] []]
         [[[[] []] []] []])

       (tabular
         (fact "sets" (first (parse-string (prn-str ?s))) => ?s)
         ?s
         #{}
         #{['()]}
         #{1}
         #{1 2})

       (tabular
         (fact "maps" (first (parse-string (prn-str ?s))) => ?s)
         ?s
         {}
         {1 2}
         {:foo [1 2 3]}
         {:foo {:bar {:baz #{1 2 3}}}})
         
       )
