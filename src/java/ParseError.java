package clj_antlr;

import clojure.lang.IDeref;

public class ParseError extends RuntimeException implements IDeref {
  public final Object errors;

  public ParseError(final Object errors, final String msg) {
    super(msg);
    this.errors = errors;
  }

  public Object deref() {
    return errors;
  }
}
