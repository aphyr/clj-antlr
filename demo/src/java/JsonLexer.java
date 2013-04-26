// Generated from Json.g4 by ANTLR 4.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JsonLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__8=1, T__7=2, T__6=3, T__5=4, T__4=5, T__3=6, T__2=7, T__1=8, T__0=9, 
		STRING=10, NUMBER=11, WS=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "'{'", "'null'", "','", "'['", "':'", "'false'", "'}'", "'true'", 
		"STRING", "NUMBER", "WS"
	};
	public static final String[] ruleNames = {
		"T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", 
		"STRING", "ESC", "UNICODE", "HEX", "NUMBER", "INT", "EXP", "WS"
	};


	public JsonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Json.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 16: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\16\u0084\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5"+
		"\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3"+
		"\13\3\13\3\13\7\13E\n\13\f\13\16\13H\13\13\3\13\3\13\3\f\3\f\3\f\5\fO"+
		"\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\17\5\17Z\n\17\3\17\3\17\3\17"+
		"\3\17\5\17`\n\17\3\17\5\17c\n\17\3\17\3\17\3\17\3\17\5\17i\n\17\3\17\5"+
		"\17l\n\17\3\20\3\20\3\20\7\20q\n\20\f\20\16\20t\13\20\5\20v\n\20\3\21"+
		"\3\21\5\21z\n\21\3\21\3\21\3\22\6\22\177\n\22\r\22\16\22\u0080\3\22\3"+
		"\22\2\23\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25"+
		"\f\1\27\2\1\31\2\1\33\2\1\35\r\1\37\2\1!\2\1#\16\2\3\2\n\4$$^^\n$$\61"+
		"\61^^ddhhppttvv\5\62;CHch\3\63;\3\62;\4GGgg\4--//\5\13\f\17\17\"\"\u008b"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\35\3\2\2\2"+
		"\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2\7)\3\2\2\2\t.\3\2\2\2\13\60\3\2\2\2"+
		"\r\62\3\2\2\2\17\64\3\2\2\2\21:\3\2\2\2\23<\3\2\2\2\25A\3\2\2\2\27K\3"+
		"\2\2\2\31P\3\2\2\2\33V\3\2\2\2\35k\3\2\2\2\37u\3\2\2\2!w\3\2\2\2#~\3\2"+
		"\2\2%&\7_\2\2&\4\3\2\2\2\'(\7}\2\2(\6\3\2\2\2)*\7p\2\2*+\7w\2\2+,\7n\2"+
		"\2,-\7n\2\2-\b\3\2\2\2./\7.\2\2/\n\3\2\2\2\60\61\7]\2\2\61\f\3\2\2\2\62"+
		"\63\7<\2\2\63\16\3\2\2\2\64\65\7h\2\2\65\66\7c\2\2\66\67\7n\2\2\678\7"+
		"u\2\289\7g\2\29\20\3\2\2\2:;\7\177\2\2;\22\3\2\2\2<=\7v\2\2=>\7t\2\2>"+
		"?\7w\2\2?@\7g\2\2@\24\3\2\2\2AF\7$\2\2BE\5\27\f\2CE\n\2\2\2DB\3\2\2\2"+
		"DC\3\2\2\2EH\3\2\2\2FD\3\2\2\2FG\3\2\2\2GI\3\2\2\2HF\3\2\2\2IJ\7$\2\2"+
		"J\26\3\2\2\2KN\7^\2\2LO\t\3\2\2MO\5\31\r\2NL\3\2\2\2NM\3\2\2\2O\30\3\2"+
		"\2\2PQ\7w\2\2QR\5\33\16\2RS\5\33\16\2ST\5\33\16\2TU\5\33\16\2U\32\3\2"+
		"\2\2VW\t\4\2\2W\34\3\2\2\2XZ\7/\2\2YX\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[\\\5"+
		"\37\20\2\\]\7\60\2\2]_\5\37\20\2^`\5!\21\2_^\3\2\2\2_`\3\2\2\2`l\3\2\2"+
		"\2ac\7/\2\2ba\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\5\37\20\2ef\5!\21\2fl\3\2"+
		"\2\2gi\7/\2\2hg\3\2\2\2hi\3\2\2\2ij\3\2\2\2jl\5\37\20\2kY\3\2\2\2kb\3"+
		"\2\2\2kh\3\2\2\2l\36\3\2\2\2mv\7\62\2\2nr\t\5\2\2oq\t\6\2\2po\3\2\2\2"+
		"qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2sv\3\2\2\2tr\3\2\2\2um\3\2\2\2un\3\2\2\2"+
		"v \3\2\2\2wy\t\7\2\2xz\t\b\2\2yx\3\2\2\2yz\3\2\2\2z{\3\2\2\2{|\5\37\20"+
		"\2|\"\3\2\2\2}\177\t\t\2\2~}\3\2\2\2\177\u0080\3\2\2\2\u0080~\3\2\2\2"+
		"\u0080\u0081\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0083\b\22\2\2\u0083$\3"+
		"\2\2\2\17\2DFNY_bhkruy\u0080";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}