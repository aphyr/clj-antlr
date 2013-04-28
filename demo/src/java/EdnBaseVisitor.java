// Generated from Edn.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.ParserRuleContext;

public class EdnBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements EdnVisitor<T> {
	@Override public T visitFloat(EdnParser.FloatContext ctx) { return visitChildren(ctx); }

	@Override public T visitElement(EdnParser.ElementContext ctx) { return visitChildren(ctx); }

	@Override public T visitEdn(EdnParser.EdnContext ctx) { return visitChildren(ctx); }

	@Override public T visitKeyword(EdnParser.KeywordContext ctx) { return visitChildren(ctx); }

	@Override public T visitSet(EdnParser.SetContext ctx) { return visitChildren(ctx); }

	@Override public T visitList(EdnParser.ListContext ctx) { return visitChildren(ctx); }

	@Override public T visitDiscarded(EdnParser.DiscardedContext ctx) { return visitChildren(ctx); }

	@Override public T visitFalse(EdnParser.FalseContext ctx) { return visitChildren(ctx); }

	@Override public T visitCharacter(EdnParser.CharacterContext ctx) { return visitChildren(ctx); }

	@Override public T visitString(EdnParser.StringContext ctx) { return visitChildren(ctx); }

	@Override public T visitSymbol(EdnParser.SymbolContext ctx) { return visitChildren(ctx); }

	@Override public T visitVector(EdnParser.VectorContext ctx) { return visitChildren(ctx); }

	@Override public T visitMap(EdnParser.MapContext ctx) { return visitChildren(ctx); }

	@Override public T visitTrue(EdnParser.TrueContext ctx) { return visitChildren(ctx); }

	@Override public T visitTagged(EdnParser.TaggedContext ctx) { return visitChildren(ctx); }

	@Override public T visitInteger(EdnParser.IntegerContext ctx) { return visitChildren(ctx); }

	@Override public T visitNil(EdnParser.NilContext ctx) { return visitChildren(ctx); }
}