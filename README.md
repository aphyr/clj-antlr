# Clj-Antlr

Clojure bindings for the [ANTLR 4](http://www.antlr.org/) parser library, an
adaptive LL(\*) parser. Looks a lot like
[Instaparse](https://github.com/Engelberg/instaparse), only much faster, with
richer grammar definitions, and none of Instaparse's delightful features.

## Installation

Just [add clj-antlr to your project.clj](https://clojars.org/clj-antlr), and
load a grammar file at runtime.

No ANTLR installation is required; clj-antlr will load the grammar for you, no
compilation needed. No macros, either! Running the parser in interpreted mode
is a tad slower than the compiled parsers that Antlr can emit, but means a lot
less hassle for folks to get started.

## Usage

```clj
user=> (require ['clj-antlr.core :as 'antlr])
nil
user=> (def json (antlr/parser "grammars/Json.g4"))
#'user/json
user=> (pprint (json "[1,2,3]"))
(:jsonText
 (:jsonArray
  "["
  (:jsonValue (:jsonNumber "1"))
  ","
  (:jsonValue (:jsonNumber "2"))
  ","
  (:jsonValue (:jsonNumber "3"))
  "]"))
```

Parsers act like functions, and can take strings, InputStreams, and Readers as
their arguments. They emit trees of lists: each list begins with the keyword
node name, and is followed by the nodes' children. Terminal nodes are
represented as strings.

You can define parsers directly with strings, too. ANTLR 4.2 will complain but compile successfully; 4.2.1 will include a patch to fix that.

```clj
user=> (def aaa (antlr/parser "grammar Aaa;
  #_=>                         aaa : AA+;
  #_=>                         AA : [Aa]+ ;
  #_=>                         WS : ' ' -> channel(HIDDEN) ;"))
#'user/aaa
user=> (aaa "aAAaa A aAA AAAAaAA")
(:aaa "aAAaa" "A" "aAA" "AAAAaAA")
```

Works with split (lexer and parser) grammars:

```clj
  clj::clj-antlr.core-test=> (let [g (parser "grammars/L.g4" "grammars/T.g4" {})] (g "abbc"))
  (:s "a" "b" "b" "c")

```

## Errors

ANTLR can recover from errors in mid-parse by performing single-token insertion
and single-token deletion on mismatched error tokens, where possible. This
means an ANTLR parse may throw an error, but still produce useful parse
information; or produce *multiple* errors.  Parsing an invalid string will
throw an exception with a textual explanation of the errors encountered:

```clj
user=> (json "[1,2,,3,]")

ParseError extraneous input ',' expecting {'null', '{', '[', 'false', 'true', NUMBER, STRING}
mismatched input ']' expecting {'null', '{', '[', 'false', 'true', NUMBER, STRING}  clj-antlr.common/parse-error (common.clj:106)
```

But wait, there's more! ParseErrors are deref-able, yielding detailed debugging
information:

```clj
user=> (try (json "[1,2,,3,]") (catch clj_antlr.ParseError e (pprint @e)))
({:symbol #<CommonToken [@5,5:5=',',<4>,1:5]>,
  :line 1,
  :char 5,
  :message
  "extraneous input ',' expecting {'null', '{', '[', 'false', 'true', NUMBER, STRING}"}
 {:token #<CommonToken [@8,8:8=']',<1>,1:8]>,
  :expected #<IntervalSet {2..3, 5, 7, 9..10, 12}>,
  :state 25,
  :rule #<InterpreterRuleContext [51 15]>,
  :stack ("jsonText" "jsonArray" "jsonValue"),
  :symbol #<CommonToken [@8,8:8=']',<1>,1:8]>,
  :line 1,
  :char 8,
  :message
  "mismatched input ']' expecting {'null', '{', '[', 'false', 'true', NUMBER, STRING}"})
```

You can use the line and char numbers, in addition to the messages, to guide
the user in generating correct syntax. Clj-antlr handles both lexer and parser
errors; though the debugging information available at different passes may
vary.

```clj
user=> (try (json "⊂") (catch clj_antlr.ParseError e (pprint @e)))
({:token nil,
  :expected nil,
  :state -1,
  :rule nil,
  :symbol nil,
  :line 1,
  :char 0,
  :message "token recognition error at: '⊂'"}
 {:token #<CommonToken [@0,1:0='<EOF>',<-1>,1:1]>,
  :expected #<IntervalSet {3, 5}>,
  :state 16,
  :rule #<InterpreterRuleContext []>,
  :symbol #<CommonToken [@0,1:0='<EOF>',<-1>,1:1]>,
  :line 1,
  :char 1,
  :message "no viable alternative at input '<EOF>'"})
```

clj-antlr will still produce parse trees from invalid input. Use the {:throw?
false} option, either when constructing the parser, or as an argument to the
`parse` function.

```clj
user=> (->> "[1,2" (antlr/parse json {:throw? false}) pprint)
(:jsonText
 (:jsonArray
  "["
  (:jsonValue (:jsonNumber "1"))
  ","
  (:jsonValue (:jsonNumber "2"))))
```

Any parse errors will be available as metadata on the returned tree:

```clj
user=> (->> "[1,2" (antlr/parse json {:throw? false}) meta :errors pprint)
({:token #<CommonToken [@4,4:3='<EOF>',<-1>,1:4]>,
  :expected #<IntervalSet {1, 4}>,
  :state 54,
  :rule #<InterpreterRuleContext [15]>,
  :symbol #<CommonToken [@4,4:3='<EOF>',<-1>,1:4]>,
  :line 1,
  :char 4,
  :message "no viable alternative at input '<EOF>'"})
```

*Sometimes*, clj-antlr is able to identify invalid rules in the parse tree, and
wrap them with a `:clj-antlr/error` node.

```clj
user=> (->> "[1, {\"foo\"::}, 3]" (antlr/parse json {:throw? false}) pprint)
(:jsonText
 (:jsonArray
  "["
  (:jsonValue (:jsonNumber "1"))
  ","
  (:jsonValue
   (:jsonObject
    "{"
    (:member "\"foo\"" ":" (:clj-antlr/error (:jsonValue ":")))
    "}"))
  ","
  (:jsonValue (:jsonNumber "3"))
  "]"))
```

But not always. This input generates errors in the top-level :errors metadata
map, but creates an invalid parse tree without any error nodes. I think this is
a bug in clj-antlr or ANTLR itself; if you have suggestions, I'd like to hear
them.

```clj
user=> (->> "[1,,3]" (antlr/parse json {:throw? false}) pprint)
(:jsonText
 (:jsonArray
  "["
  (:jsonValue (:jsonNumber "1"))
  ","
  (:jsonValue "," (:jsonNumber "3"))
  "]"))
```

## Options

All options may be passed at parser construction time:

```clj
user=> (antlr/parse (antlr/parser "grammars/Cadr.g4" {:case-sensitive? false})
                    "CdDr")
(:cadr "C" "d" "D" "r")
```

... and also overridden at parse time via `antlr.core/parse`:

```
user=> (antlr/parse (antlr/parser "grammars/Cadr.g4")
                    {:case-sensitive? false}
                    "CdDr")
(:cadr "C" "d" "D" "r")
```

```clj
user=> (doc antlr/parser)
-------------------------
clj-antlr.core/parser
([filename] [filename opts])
  Constructs a new parser. Takes a filename for an Antlr v4 grammar. Options:

  :format           The parse tree to generate. One of
                      :sexp (default)  Nested lists, node names first
                      :raw             Equivalent to identity
                      <any function>   Takes a map of {:tree, :parser, etc}

  :root             The string name of the rule to begin parsing. Defaults to
                    the first rule in the grammar.

  :throw?           If truthy, parse errors will be thrown. Defaults true.

  :case-sensitive?  Whether the lexer must match the exact case of characters.
                    Defaults true. If false, the tokenizer will only receive
                    lowercase characters. The generated parse tree will still
                    retain the case of the original text.

  :use-alternates?  If truthy, uses the alternate name for a node, rather than
                    the rule name. Defaults false.
```

## Where can I find grammars?

[Here's a ton of ANTLR 4 parsers for various languages!](https://github.com/antlr/grammars-v4)

## Faster?

On a [real-world 3.5KB JSON
object](https://github.com/aphyr/clj-antlr/blob/master/data/test.json),
clj-antlr with a [typical JSON
grammar](https://github.com/aphyr/clj-antlr/blob/master/grammars/Json.g4) is
about 100 times faster than an identical AST built by an [Instaparse
grammar](https://github.com/aphyr/clj-antlr/blob/master/grammars/json.instaparse).
Since Instaparse doesn't really have a separation between grammar and lexer
rules, I'm using regular expressions for strings, ints, etc; but the transformation between grammars is pretty straightforward.

```
kingsbury@hackbook:~/clj-antlr master$ lein test :perf
Benchmarking instaparse
WARNING: Final GC required 1.645508727049022 % of runtime
Evaluation count : 660 in 60 samples of 11 calls.
             Execution time mean : 97.557634 ms
    Execution time std-deviation : 5.132833 ms
   Execution time lower quantile : 91.651987 ms ( 2.5%)
   Execution time upper quantile : 108.289375 ms (97.5%)
                   Overhead used : 10.328888 ns

Found 4 outliers in 60 samples (6.6667 %)
  low-severe   3 (5.0000 %)
  low-mild   1 (1.6667 %)
 Variance from outliers : 38.4948 % Variance is moderately inflated by outliers



Benchmarking clj-antlr
Evaluation count : 64440 in 60 samples of 1074 calls.
             Execution time mean : 958.366202 µs
    Execution time std-deviation : 36.434070 µs
   Execution time lower quantile : 901.210266 µs ( 2.5%)
   Execution time upper quantile : 1.032678 ms (97.5%)
                   Overhead used : 10.328888 ns

Ran 1 tests containing 1 assertions.
0 failures, 0 errors.
```

## License

Copyright © 2014 Kyle Kingsbury <aphyr@aphyr.com>, and Factual, Inc. Includes
ANTLR code under the BSD 3-clause license, written by Terence Parr and Sam
Harwell.  My sincerest appreciation to all ANTLR contributors as well. :)

Distributed under the Eclipse Public License, the same as Clojure.
