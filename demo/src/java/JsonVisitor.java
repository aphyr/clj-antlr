// Generated from Json.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface JsonVisitor<T> extends ParseTreeVisitor<T> {
	T visitValueTrue(JsonParser.ValueTrueContext ctx);

	T visitValueArray(JsonParser.ValueArrayContext ctx);

	T visitValueObject(JsonParser.ValueObjectContext ctx);

	T visitJsonObject(JsonParser.JsonObjectContext ctx);

	T visitValueString(JsonParser.ValueStringContext ctx);

	T visitPair(JsonParser.PairContext ctx);

	T visitJsonArray(JsonParser.JsonArrayContext ctx);

	T visitValueNull(JsonParser.ValueNullContext ctx);

	T visitValueNumber(JsonParser.ValueNumberContext ctx);

	T visitObject(JsonParser.ObjectContext ctx);

	T visitValueFalse(JsonParser.ValueFalseContext ctx);

	T visitArray(JsonParser.ArrayContext ctx);
}