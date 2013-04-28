// Generated from Edn.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface EdnListener extends ParseTreeListener {
	void enterFloat(EdnParser.FloatContext ctx);
	void exitFloat(EdnParser.FloatContext ctx);

	void enterElement(EdnParser.ElementContext ctx);
	void exitElement(EdnParser.ElementContext ctx);

	void enterEdn(EdnParser.EdnContext ctx);
	void exitEdn(EdnParser.EdnContext ctx);

	void enterKeyword(EdnParser.KeywordContext ctx);
	void exitKeyword(EdnParser.KeywordContext ctx);

	void enterSet(EdnParser.SetContext ctx);
	void exitSet(EdnParser.SetContext ctx);

	void enterList(EdnParser.ListContext ctx);
	void exitList(EdnParser.ListContext ctx);

	void enterDiscarded(EdnParser.DiscardedContext ctx);
	void exitDiscarded(EdnParser.DiscardedContext ctx);

	void enterFalse(EdnParser.FalseContext ctx);
	void exitFalse(EdnParser.FalseContext ctx);

	void enterCharacter(EdnParser.CharacterContext ctx);
	void exitCharacter(EdnParser.CharacterContext ctx);

	void enterString(EdnParser.StringContext ctx);
	void exitString(EdnParser.StringContext ctx);

	void enterSymbol(EdnParser.SymbolContext ctx);
	void exitSymbol(EdnParser.SymbolContext ctx);

	void enterVector(EdnParser.VectorContext ctx);
	void exitVector(EdnParser.VectorContext ctx);

	void enterMap(EdnParser.MapContext ctx);
	void exitMap(EdnParser.MapContext ctx);

	void enterTrue(EdnParser.TrueContext ctx);
	void exitTrue(EdnParser.TrueContext ctx);

	void enterTagged(EdnParser.TaggedContext ctx);
	void exitTagged(EdnParser.TaggedContext ctx);

	void enterInteger(EdnParser.IntegerContext ctx);
	void exitInteger(EdnParser.IntegerContext ctx);

	void enterNil(EdnParser.NilContext ctx);
	void exitNil(EdnParser.NilContext ctx);
}