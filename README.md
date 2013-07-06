# clj-antlr

Clojure bindings for the Antlr 4 parser library. Uses all kinds of evil macro
tricks to turn small Clojure mappings into full parse tree transformations.

## Installation

Add the latest release from https://clojars.org/clj-antlr to your project.clj.

## Usage

clj-antlr wraps the normal antlr-generated java classes, using reflection and macros to build parse tree visitors. Take a look at demo/src for example EDN and JSON parsers. The general form is:

0. Add src/java to `:java-source-paths` in `project.clj`.
1. Write or find a .g4 grammar; e.g. `Json.g4`, and drop it in `src/java/`.
2. Compile the grammar with antlr4, as usual. This will spew a bunch of .java
   files into `src/java/`.
3. Include `clj-antlr.core` and your parser types (`JsonVisitor`, `JsonLexer`,
   etc.) in your namespace.
4. Define a parse tree visitor using `(visitor)`.
5. Use `visit-string` with your lexer, parser, and visitor, to turn a string
   into a data structure.

Take a look at `demo/src/clj_antlr_demo/json.clj` or `edn.clj` for an example;
it'll be much clearer. :)

## What are visitors?

A Visitor, in the context of Antlr, is something which starts at the root of a parse tree and recursively walks it, possibly descending into child nodes; it returns a value for each tree node.

You could write a ton of Java boilerplate to do this, but clj-antlr makes the
common case easy. You define a visitor using the `visitor` macro, which takes
the visitor base class for your grammar, followed by several pattern-matching
forms. Each form is a list beginning with the type of node being visited,
followed by an argument vector which receives the visitor itself and the
current parse context.

```clj
(visitor JsonVisitor
  ; When you visit a JsonObject, run this code:
  (JsonObject [this context]
    ; visit the object child node, and return that value.
    (visit this (.object context))))

  ; Nodes with repeated children are exposed as sequences:
  (Array [t c] (map (partial visit t) (.value c)))

  ; (text context) returns the string belonging to a given parse node:
  ; in this case, a string like "hello\n".
  (ValueString [t c] (read-json-string (text c)))

  ; Or you can simply return literals
  (valueTrue [t c] true))
```

If you don't specify how to interpret a given node, clj-antlr will use
reflection and some heuristics to make up a sensible default; for instance,
returning a map of child names to their values. Exactly *what* kind of data
structures it returns is probably going to change; I'd treat it only as a
convenient scaffolding for experiments.

## License

Copyright © 2013 Kyle Kingsbury <aphyr@aphyr.com>

Distributed under the Eclipse Public License, the same as Clojure.
