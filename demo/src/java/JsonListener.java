// Generated from Json.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface JsonListener extends ParseTreeListener {
	void enterValueTrue(JsonParser.ValueTrueContext ctx);
	void exitValueTrue(JsonParser.ValueTrueContext ctx);

	void enterValueArray(JsonParser.ValueArrayContext ctx);
	void exitValueArray(JsonParser.ValueArrayContext ctx);

	void enterValueObject(JsonParser.ValueObjectContext ctx);
	void exitValueObject(JsonParser.ValueObjectContext ctx);

	void enterJsonObject(JsonParser.JsonObjectContext ctx);
	void exitJsonObject(JsonParser.JsonObjectContext ctx);

	void enterValueString(JsonParser.ValueStringContext ctx);
	void exitValueString(JsonParser.ValueStringContext ctx);

	void enterPair(JsonParser.PairContext ctx);
	void exitPair(JsonParser.PairContext ctx);

	void enterJsonArray(JsonParser.JsonArrayContext ctx);
	void exitJsonArray(JsonParser.JsonArrayContext ctx);

	void enterValueNull(JsonParser.ValueNullContext ctx);
	void exitValueNull(JsonParser.ValueNullContext ctx);

	void enterValueNumber(JsonParser.ValueNumberContext ctx);
	void exitValueNumber(JsonParser.ValueNumberContext ctx);

	void enterObject(JsonParser.ObjectContext ctx);
	void exitObject(JsonParser.ObjectContext ctx);

	void enterValueFalse(JsonParser.ValueFalseContext ctx);
	void exitValueFalse(JsonParser.ValueFalseContext ctx);

	void enterArray(JsonParser.ArrayContext ctx);
	void exitArray(JsonParser.ArrayContext ctx);
}