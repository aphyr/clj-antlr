// Generated from Edn.g4 by ANTLR 4.0
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EdnParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__6=1, T__5=2, T__4=3, T__3=4, T__2=5, T__1=6, T__0=7, Character=8, NIL=9, 
		TRUE=10, FALSE=11, Integer=12, Float=13, String=14, DISCARD=15, COMMENT=16, 
		WS=17, Tag=18, Keyword=19, Symbol=20, SymbolPrefix=21, SymbolName=22;
	public static final String[] tokenNames = {
		"<INVALID>", "']'", "'{'", "')'", "'#{'", "'['", "'('", "'}'", "Character", 
		"'nil'", "'true'", "'false'", "Integer", "Float", "String", "'#_'", "COMMENT", 
		"WS", "Tag", "Keyword", "Symbol", "SymbolPrefix", "SymbolName"
	};
	public static final int
		RULE_edn = 0, RULE_element = 1, RULE_literal = 2, RULE_list = 3, RULE_vector = 4, 
		RULE_map = 5, RULE_set = 6, RULE_tagged = 7, RULE_discarded = 8;
	public static final String[] ruleNames = {
		"edn", "element", "literal", "list", "vector", "map", "set", "tagged", 
		"discarded"
	};

	@Override
	public String getGrammarFileName() { return "Edn.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public EdnParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EdnContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public List<TerminalNode> WS() { return getTokens(EdnParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(EdnParser.WS, i);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public EdnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edn; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterEdn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitEdn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitEdn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdnContext edn() throws RecognitionException {
		EdnContext _localctx = new EdnContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_edn);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18); element();
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(19); match(WS);
				setState(20); element();
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public SetContext set() {
			return getRuleContext(SetContext.class,0);
		}
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public MapContext map() {
			return getRuleContext(MapContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_element);
		try {
			setState(31);
			switch (_input.LA(1)) {
			case Character:
			case NIL:
			case TRUE:
			case FALSE:
			case Integer:
			case Float:
			case String:
			case Keyword:
			case Symbol:
				enterOuterAlt(_localctx, 1);
				{
				setState(26); literal();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 2);
				{
				setState(27); list();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 3);
				{
				setState(28); vector();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 4);
				{
				setState(29); map();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 5);
				{
				setState(30); set();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FloatContext extends LiteralContext {
		public TerminalNode Float() { return getToken(EdnParser.Float, 0); }
		public FloatContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitFloat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitFloat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class KeywordContext extends LiteralContext {
		public TerminalNode Keyword() { return getToken(EdnParser.Keyword, 0); }
		public KeywordContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterKeyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitKeyword(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitKeyword(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringContext extends LiteralContext {
		public TerminalNode String() { return getToken(EdnParser.String, 0); }
		public StringContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SymbolContext extends LiteralContext {
		public TerminalNode Symbol() { return getToken(EdnParser.Symbol, 0); }
		public SymbolContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterSymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitSymbol(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitSymbol(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueContext extends LiteralContext {
		public TerminalNode TRUE() { return getToken(EdnParser.TRUE, 0); }
		public TrueContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterTrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitTrue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerContext extends LiteralContext {
		public TerminalNode Integer() { return getToken(EdnParser.Integer, 0); }
		public IntegerContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitInteger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitInteger(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseContext extends LiteralContext {
		public TerminalNode FALSE() { return getToken(EdnParser.FALSE, 0); }
		public FalseContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterFalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitFalse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CharacterContext extends LiteralContext {
		public TerminalNode Character() { return getToken(EdnParser.Character, 0); }
		public CharacterContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterCharacter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitCharacter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitCharacter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NilContext extends LiteralContext {
		public TerminalNode NIL() { return getToken(EdnParser.NIL, 0); }
		public NilContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterNil(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitNil(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitNil(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_literal);
		try {
			setState(42);
			switch (_input.LA(1)) {
			case NIL:
				_localctx = new NilContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(33); match(NIL);
				}
				break;
			case FALSE:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(34); match(FALSE);
				}
				break;
			case TRUE:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(35); match(TRUE);
				}
				break;
			case String:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(36); match(String);
				}
				break;
			case Character:
				_localctx = new CharacterContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(37); match(Character);
				}
				break;
			case Symbol:
				_localctx = new SymbolContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(38); match(Symbol);
				}
				break;
			case Keyword:
				_localctx = new KeywordContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(39); match(Keyword);
				}
				break;
			case Integer:
				_localctx = new IntegerContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(40); match(Integer);
				}
				break;
			case Float:
				_localctx = new FloatContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(41); match(Float);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_list);
		int _la;
		try {
			setState(56);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(44); match(6);
				setState(45); element();
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << 4) | (1L << 5) | (1L << 6) | (1L << Character) | (1L << NIL) | (1L << TRUE) | (1L << FALSE) | (1L << Integer) | (1L << Float) | (1L << String) | (1L << Keyword) | (1L << Symbol))) != 0)) {
					{
					{
					setState(46); element();
					}
					}
					setState(51);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(52); match(3);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(54); match(6);
				setState(55); match(3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VectorContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public VectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterVector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitVector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitVector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VectorContext vector() throws RecognitionException {
		VectorContext _localctx = new VectorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_vector);
		int _la;
		try {
			setState(70);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(58); match(5);
				setState(59); element();
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << 4) | (1L << 5) | (1L << 6) | (1L << Character) | (1L << NIL) | (1L << TRUE) | (1L << FALSE) | (1L << Integer) | (1L << Float) | (1L << String) | (1L << Keyword) | (1L << Symbol))) != 0)) {
					{
					{
					setState(60); element();
					}
					}
					setState(65);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(66); match(1);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(68); match(5);
				setState(69); match(1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MapContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public MapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_map; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterMap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitMap(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitMap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapContext map() throws RecognitionException {
		MapContext _localctx = new MapContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_map);
		int _la;
		try {
			setState(87);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(72); match(2);
				setState(73); element();
				setState(74); element();
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << 4) | (1L << 5) | (1L << 6) | (1L << Character) | (1L << NIL) | (1L << TRUE) | (1L << FALSE) | (1L << Integer) | (1L << Float) | (1L << String) | (1L << Keyword) | (1L << Symbol))) != 0)) {
					{
					{
					setState(75); element();
					setState(76); element();
					}
					}
					setState(82);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(83); match(7);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85); match(2);
				setState(86); match(7);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public SetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetContext set() throws RecognitionException {
		SetContext _localctx = new SetContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_set);
		int _la;
		try {
			setState(101);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(89); match(4);
				setState(90); element();
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << 4) | (1L << 5) | (1L << 6) | (1L << Character) | (1L << NIL) | (1L << TRUE) | (1L << FALSE) | (1L << Integer) | (1L << Float) | (1L << String) | (1L << Keyword) | (1L << Symbol))) != 0)) {
					{
					{
					setState(91); element();
					}
					}
					setState(96);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(97); match(7);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(99); match(4);
				setState(100); match(7);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TaggedContext extends ParserRuleContext {
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public TerminalNode WS() { return getToken(EdnParser.WS, 0); }
		public TerminalNode Tag() { return getToken(EdnParser.Tag, 0); }
		public TaggedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tagged; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterTagged(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitTagged(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitTagged(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TaggedContext tagged() throws RecognitionException {
		TaggedContext _localctx = new TaggedContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tagged);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103); match(Tag);
			setState(104); match(WS);
			setState(105); element();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DiscardedContext extends ParserRuleContext {
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public TerminalNode WS() { return getToken(EdnParser.WS, 0); }
		public TerminalNode DISCARD() { return getToken(EdnParser.DISCARD, 0); }
		public DiscardedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_discarded; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).enterDiscarded(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EdnListener ) ((EdnListener)listener).exitDiscarded(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EdnVisitor ) return ((EdnVisitor<? extends T>)visitor).visitDiscarded(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DiscardedContext discarded() throws RecognitionException {
		DiscardedContext _localctx = new DiscardedContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_discarded);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107); match(DISCARD);
			setState(108); match(WS);
			setState(109); element();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3\30r\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t"+
		"\t\4\n\t\n\3\2\3\2\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\3\3\3\3\3\3\3\3\3"+
		"\5\3\"\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4-\n\4\3\5\3\5\3\5\7"+
		"\5\62\n\5\f\5\16\5\65\13\5\3\5\3\5\3\5\3\5\5\5;\n\5\3\6\3\6\3\6\7\6@\n"+
		"\6\f\6\16\6C\13\6\3\6\3\6\3\6\3\6\5\6I\n\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7"+
		"Q\n\7\f\7\16\7T\13\7\3\7\3\7\3\7\3\7\5\7Z\n\7\3\b\3\b\3\b\7\b_\n\b\f\b"+
		"\16\bb\13\b\3\b\3\b\3\b\3\b\5\bh\n\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\2\13\2\4\6\b\n\f\16\20\22\2\2}\2\24\3\2\2\2\4!\3\2\2\2\6,\3\2\2\2\b"+
		":\3\2\2\2\nH\3\2\2\2\fY\3\2\2\2\16g\3\2\2\2\20i\3\2\2\2\22m\3\2\2\2\24"+
		"\31\5\4\3\2\25\26\7\23\2\2\26\30\5\4\3\2\27\25\3\2\2\2\30\33\3\2\2\2\31"+
		"\27\3\2\2\2\31\32\3\2\2\2\32\3\3\2\2\2\33\31\3\2\2\2\34\"\5\6\4\2\35\""+
		"\5\b\5\2\36\"\5\n\6\2\37\"\5\f\7\2 \"\5\16\b\2!\34\3\2\2\2!\35\3\2\2\2"+
		"!\36\3\2\2\2!\37\3\2\2\2! \3\2\2\2\"\5\3\2\2\2#-\7\13\2\2$-\7\r\2\2%-"+
		"\7\f\2\2&-\7\20\2\2\'-\7\n\2\2(-\7\26\2\2)-\7\25\2\2*-\7\16\2\2+-\7\17"+
		"\2\2,#\3\2\2\2,$\3\2\2\2,%\3\2\2\2,&\3\2\2\2,\'\3\2\2\2,(\3\2\2\2,)\3"+
		"\2\2\2,*\3\2\2\2,+\3\2\2\2-\7\3\2\2\2./\7\b\2\2/\63\5\4\3\2\60\62\5\4"+
		"\3\2\61\60\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\66\3\2"+
		"\2\2\65\63\3\2\2\2\66\67\7\5\2\2\67;\3\2\2\289\7\b\2\29;\7\5\2\2:.\3\2"+
		"\2\2:8\3\2\2\2;\t\3\2\2\2<=\7\7\2\2=A\5\4\3\2>@\5\4\3\2?>\3\2\2\2@C\3"+
		"\2\2\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2CA\3\2\2\2DE\7\3\2\2EI\3\2\2\2FG\7"+
		"\7\2\2GI\7\3\2\2H<\3\2\2\2HF\3\2\2\2I\13\3\2\2\2JK\7\4\2\2KL\5\4\3\2L"+
		"R\5\4\3\2MN\5\4\3\2NO\5\4\3\2OQ\3\2\2\2PM\3\2\2\2QT\3\2\2\2RP\3\2\2\2"+
		"RS\3\2\2\2SU\3\2\2\2TR\3\2\2\2UV\7\t\2\2VZ\3\2\2\2WX\7\4\2\2XZ\7\t\2\2"+
		"YJ\3\2\2\2YW\3\2\2\2Z\r\3\2\2\2[\\\7\6\2\2\\`\5\4\3\2]_\5\4\3\2^]\3\2"+
		"\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ac\3\2\2\2b`\3\2\2\2cd\7\t\2\2dh\3\2"+
		"\2\2ef\7\6\2\2fh\7\t\2\2g[\3\2\2\2ge\3\2\2\2h\17\3\2\2\2ij\7\24\2\2jk"+
		"\7\23\2\2kl\5\4\3\2l\21\3\2\2\2mn\7\21\2\2no\7\23\2\2op\5\4\3\2p\23\3"+
		"\2\2\2\r\31!,\63:AHRY`g";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}