// Generated from Edn.g4 by ANTLR 4.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EdnLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__6=1, T__5=2, T__4=3, T__3=4, T__2=5, T__1=6, T__0=7, Character=8, NIL=9, 
		TRUE=10, FALSE=11, Integer=12, Float=13, String=14, DISCARD=15, COMMENT=16, 
		WS=17, Tag=18, Keyword=19, Symbol=20, SymbolPrefix=21, SymbolName=22;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "'{'", "')'", "'#{'", "'['", "'('", "'}'", "Character", "'nil'", 
		"'true'", "'false'", "Integer", "Float", "String", "'#_'", "COMMENT", 
		"WS", "Tag", "Keyword", "Symbol", "SymbolPrefix", "SymbolName"
	};
	public static final String[] ruleNames = {
		"T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "Character", "NIL", 
		"TRUE", "FALSE", "Integer", "Float", "Int", "FloatFrac", "Exponent", "String", 
		"EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "DISCARD", 
		"COMMENT", "LINE_END", "WS", "Tag", "Keyword", "Symbol", "SymbolPrefix", 
		"SymbolName", "SymbolPart", "FirstSymbolChar", "SymbolChar"
	};


	public EdnLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Edn.g4"; }

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
		case 22: COMMENT_action((RuleContext)_localctx, actionIndex); break;

		case 24: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: skip();  break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\30\u0105\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36"+
		"\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5"+
		"\tq\n\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\r\3\r\5\r\u0084\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u008d"+
		"\n\16\3\17\5\17\u0090\n\17\3\17\6\17\u0093\n\17\r\17\16\17\u0094\3\20"+
		"\3\20\6\20\u0099\n\20\r\20\16\20\u009a\3\21\3\21\5\21\u009f\n\21\3\21"+
		"\6\21\u00a2\n\21\r\21\16\21\u00a3\3\22\3\22\3\22\7\22\u00a9\n\22\f\22"+
		"\16\22\u00ac\13\22\3\22\3\22\3\23\3\23\3\23\3\23\5\23\u00b4\n\23\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\5\25\u00c6\n\25\3\26\3\26\3\27\3\27\3\27\3\30\3\30\7\30\u00cf\n"+
		"\30\f\30\16\30\u00d2\13\30\3\30\3\30\3\30\3\30\3\31\5\31\u00d9\n\31\3"+
		"\31\3\31\5\31\u00dd\n\31\3\32\6\32\u00e0\n\32\r\32\16\32\u00e1\3\32\3"+
		"\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u00f2"+
		"\n\35\3\36\3\36\3\37\3\37\3 \5 \u00f9\n \3 \3 \7 \u00fd\n \f \16 \u0100"+
		"\13 \3!\3!\3\"\3\"\2#\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n"+
		"\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\2\1\37\2\1!\2\1#\20\1%\2\1"+
		"\'\2\1)\2\1+\2\1-\21\1/\22\2\61\2\1\63\23\3\65\24\1\67\25\19\26\1;\27"+
		"\1=\30\1?\2\1A\2\1C\2\1\3\2\17\4--//\3\62;\3\62;\4GGgg\4--//\4$$^^\n$"+
		"$))^^ddhhppttvv\5\62;CHch\4\f\f\17\17\6\13\f\16\17\"\"..\5--/\60^^\f#"+
		"#&(,-/\60??AAC\\^^aac|\r##%(,-/\60\62<??AAC\\^^aac|\u0115\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2\33\3\2\2\2\2#\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2"+
		"\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\3E\3\2\2\2\5G\3\2\2\2"+
		"\7I\3\2\2\2\tK\3\2\2\2\13N\3\2\2\2\rP\3\2\2\2\17R\3\2\2\2\21p\3\2\2\2"+
		"\23r\3\2\2\2\25v\3\2\2\2\27{\3\2\2\2\31\u0081\3\2\2\2\33\u0085\3\2\2\2"+
		"\35\u008f\3\2\2\2\37\u0096\3\2\2\2!\u009c\3\2\2\2#\u00a5\3\2\2\2%\u00b3"+
		"\3\2\2\2\'\u00b5\3\2\2\2)\u00c5\3\2\2\2+\u00c7\3\2\2\2-\u00c9\3\2\2\2"+
		"/\u00cc\3\2\2\2\61\u00dc\3\2\2\2\63\u00df\3\2\2\2\65\u00e5\3\2\2\2\67"+
		"\u00e8\3\2\2\29\u00f1\3\2\2\2;\u00f3\3\2\2\2=\u00f5\3\2\2\2?\u00f8\3\2"+
		"\2\2A\u0101\3\2\2\2C\u0103\3\2\2\2EF\7_\2\2F\4\3\2\2\2GH\7}\2\2H\6\3\2"+
		"\2\2IJ\7+\2\2J\b\3\2\2\2KL\7%\2\2LM\7}\2\2M\n\3\2\2\2NO\7]\2\2O\f\3\2"+
		"\2\2PQ\7*\2\2Q\16\3\2\2\2RS\7\177\2\2S\20\3\2\2\2TU\7^\2\2UV\7p\2\2VW"+
		"\7g\2\2WX\7y\2\2XY\7n\2\2YZ\7k\2\2Z[\7p\2\2[q\7g\2\2\\]\7^\2\2]^\7t\2"+
		"\2^_\7g\2\2_`\7v\2\2`a\7w\2\2ab\7t\2\2bq\7p\2\2cd\7^\2\2de\7u\2\2ef\7"+
		"r\2\2fg\7c\2\2gh\7e\2\2hq\7g\2\2ij\7^\2\2jk\7v\2\2kl\7c\2\2lq\7d\2\2m"+
		"q\5%\23\2no\7^\2\2oq\13\2\2\2pT\3\2\2\2p\\\3\2\2\2pc\3\2\2\2pi\3\2\2\2"+
		"pm\3\2\2\2pn\3\2\2\2q\22\3\2\2\2rs\7p\2\2st\7k\2\2tu\7n\2\2u\24\3\2\2"+
		"\2vw\7v\2\2wx\7t\2\2xy\7w\2\2yz\7g\2\2z\26\3\2\2\2{|\7h\2\2|}\7c\2\2}"+
		"~\7n\2\2~\177\7u\2\2\177\u0080\7g\2\2\u0080\30\3\2\2\2\u0081\u0083\5\35"+
		"\17\2\u0082\u0084\7P\2\2\u0083\u0082\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"\32\3\2\2\2\u0085\u008c\5\35\17\2\u0086\u008d\7O\2\2\u0087\u008d\5\37"+
		"\20\2\u0088\u008d\5!\21\2\u0089\u008a\5\37\20\2\u008a\u008b\5!\21\2\u008b"+
		"\u008d\3\2\2\2\u008c\u0086\3\2\2\2\u008c\u0087\3\2\2\2\u008c\u0088\3\2"+
		"\2\2\u008c\u0089\3\2\2\2\u008d\34\3\2\2\2\u008e\u0090\t\2\2\2\u008f\u008e"+
		"\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0092\3\2\2\2\u0091\u0093\t\3\2\2\u0092"+
		"\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2"+
		"\2\2\u0095\36\3\2\2\2\u0096\u0098\7\60\2\2\u0097\u0099\t\4\2\2\u0098\u0097"+
		"\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b"+
		" \3\2\2\2\u009c\u009e\t\5\2\2\u009d\u009f\t\6\2\2\u009e\u009d\3\2\2\2"+
		"\u009e\u009f\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u00a2\4\62;\2\u00a1\u00a0"+
		"\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4"+
		"\"\3\2\2\2\u00a5\u00aa\7$\2\2\u00a6\u00a9\5%\23\2\u00a7\u00a9\n\7\2\2"+
		"\u00a8\u00a6\3\2\2\2\u00a8\u00a7\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8"+
		"\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad"+
		"\u00ae\7$\2\2\u00ae$\3\2\2\2\u00af\u00b0\7^\2\2\u00b0\u00b4\t\b\2\2\u00b1"+
		"\u00b4\5\'\24\2\u00b2\u00b4\5)\25\2\u00b3\u00af\3\2\2\2\u00b3\u00b1\3"+
		"\2\2\2\u00b3\u00b2\3\2\2\2\u00b4&\3\2\2\2\u00b5\u00b6\7^\2\2\u00b6\u00b7"+
		"\7w\2\2\u00b7\u00b8\5+\26\2\u00b8\u00b9\5+\26\2\u00b9\u00ba\5+\26\2\u00ba"+
		"\u00bb\5+\26\2\u00bb(\3\2\2\2\u00bc\u00bd\7^\2\2\u00bd\u00be\4\62\65\2"+
		"\u00be\u00bf\4\629\2\u00bf\u00c6\4\629\2\u00c0\u00c1\7^\2\2\u00c1\u00c2"+
		"\4\629\2\u00c2\u00c6\4\629\2\u00c3\u00c4\7^\2\2\u00c4\u00c6\4\629\2\u00c5"+
		"\u00bc\3\2\2\2\u00c5\u00c0\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6*\3\2\2\2"+
		"\u00c7\u00c8\t\t\2\2\u00c8,\3\2\2\2\u00c9\u00ca\7%\2\2\u00ca\u00cb\7a"+
		"\2\2\u00cb.\3\2\2\2\u00cc\u00d0\7=\2\2\u00cd\u00cf\n\n\2\2\u00ce\u00cd"+
		"\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1"+
		"\u00d3\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00d4\5\61\31\2\u00d4\u00d5\3"+
		"\2\2\2\u00d5\u00d6\b\30\2\2\u00d6\60\3\2\2\2\u00d7\u00d9\7\17\2\2\u00d8"+
		"\u00d7\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dd\7\f"+
		"\2\2\u00db\u00dd\7\1\2\2\u00dc\u00d8\3\2\2\2\u00dc\u00db\3\2\2\2\u00dd"+
		"\62\3\2\2\2\u00de\u00e0\t\13\2\2\u00df\u00de\3\2\2\2\u00e0\u00e1\3\2\2"+
		"\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4"+
		"\b\32\3\2\u00e4\64\3\2\2\2\u00e5\u00e6\7%\2\2\u00e6\u00e7\59\35\2\u00e7"+
		"\66\3\2\2\2\u00e8\u00e9\7<\2\2\u00e9\u00ea\59\35\2\u00ea8\3\2\2\2\u00eb"+
		"\u00f2\7\61\2\2\u00ec\u00f2\5=\37\2\u00ed\u00ee\5;\36\2\u00ee\u00ef\7"+
		"\61\2\2\u00ef\u00f0\5=\37\2\u00f0\u00f2\3\2\2\2\u00f1\u00eb\3\2\2\2\u00f1"+
		"\u00ec\3\2\2\2\u00f1\u00ed\3\2\2\2\u00f2:\3\2\2\2\u00f3\u00f4\5? \2\u00f4"+
		"<\3\2\2\2\u00f5\u00f6\5? \2\u00f6>\3\2\2\2\u00f7\u00f9\t\f\2\2\u00f8\u00f7"+
		"\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fe\5A!\2\u00fb"+
		"\u00fd\5C\"\2\u00fc\u00fb\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe\u00fc\3\2"+
		"\2\2\u00fe\u00ff\3\2\2\2\u00ff@\3\2\2\2\u0100\u00fe\3\2\2\2\u0101\u0102"+
		"\t\r\2\2\u0102B\3\2\2\2\u0103\u0104\t\16\2\2\u0104D\3\2\2\2\26\2p\u0083"+
		"\u008c\u008f\u0094\u009a\u009e\u00a3\u00a8\u00aa\u00b3\u00c5\u00d0\u00d8"+
		"\u00dc\u00e1\u00f1\u00f8\u00fe";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}