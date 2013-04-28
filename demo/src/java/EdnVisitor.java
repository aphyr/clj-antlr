// Generated from Edn.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface EdnVisitor<T> extends ParseTreeVisitor<T> {
	T visitFloat(EdnParser.FloatContext ctx);

	T visitElement(EdnParser.ElementContext ctx);

	T visitEdn(EdnParser.EdnContext ctx);

	T visitKeyword(EdnParser.KeywordContext ctx);

	T visitSet(EdnParser.SetContext ctx);

	T visitList(EdnParser.ListContext ctx);

	T visitDiscarded(EdnParser.DiscardedContext ctx);

	T visitFalse(EdnParser.FalseContext ctx);

	T visitCharacter(EdnParser.CharacterContext ctx);

	T visitString(EdnParser.StringContext ctx);

	T visitSymbol(EdnParser.SymbolContext ctx);

	T visitVector(EdnParser.VectorContext ctx);

	T visitMap(EdnParser.MapContext ctx);

	T visitTrue(EdnParser.TrueContext ctx);

	T visitTagged(EdnParser.TaggedContext ctx);

	T visitInteger(EdnParser.IntegerContext ctx);

	T visitNil(EdnParser.NilContext ctx);
}