# Clj-Antlr

Clojure bindings for the [Antlr 4](http://www.antlr.org/) parser library, an
adaptive LL(\*) parser. Looks a lot like
[Instaparse](https://github.com/Engelberg/instaparse), only much faster, with
richer grammar definitions, and none of Instaparse's delightful features.

## Installation

Just [add clj-antlr to your project.clj](https://clojars.org/clj-antlr), and
load a grammar file at runtime.

No Antlr installation is required; clj-antlr will load the grammar for you, no
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

## Where can I find grammars?

[Here's a ton of ANTLR 4 parsers for various languages!](https://github.com/antlr/grammars-v4)

## Faster?

On a real-world 3.5KB JSON object, clj-antlr with a straightforward JSON
grammar is about 70 times faster than an identical AST built by an Instaparse
grammar.

```
kingsbury@hackbook:~/clj-antlr master$ lein test :perf

lein test clj-antlr.perf-test
Benchmarking instaparse
WARNING: Final GC required 1.6149652300025767 % of runtime
Evaluation count : 720 in 60 samples of 12 calls.
             Execution time mean : 90.875875 ms
    Execution time std-deviation : 4.531521 ms
   Execution time lower quantile : 88.121389 ms ( 2.5%)
   Execution time upper quantile : 98.868433 ms (97.5%)
                   Overhead used : 10.399571 ns

Found 10 outliers in 60 samples (16.6667 %)
  low-severe   2 (3.3333 %)
  low-mild   8 (13.3333 %)
 Variance from outliers : 35.2586 % Variance is moderately inflated by outliers


Benchmarking clj-antlr
Evaluation count : 46140 in 60 samples of 769 calls.
             Execution time mean : 1.318649 ms
    Execution time std-deviation : 21.488349 µs
   Execution time lower quantile : 1.287781 ms ( 2.5%)
   Execution time upper quantile : 1.356745 ms (97.5%)
                   Overhead used : 10.399571 ns

Ran 1 tests containing 1 assertions.
0 failures, 0 errors.
```

## License

Copyright © 2014 Kyle Kingsbury <aphyr@aphyr.com>

Distributed under the Eclipse Public License, the same as Clojure.
