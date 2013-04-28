grammar Edn;

edn : element (WS element)* ;

element : literal
        | list
        | vector
        | map
        | set
//        | tagged
//        | discarded
        ;

// Lexer literals in their own branch
literal 
        : NIL       # Nil
        | FALSE     # False
        | TRUE      # True
        | String    # String
        | Character # Character
        | Symbol    # Symbol
        | Keyword   # Keyword
        | Integer   # Integer
        | Float     # Float
        ;

// Collections
list    : '(' element element* ')'
        | '(' ')' ;
vector  : '[' element element* ']'
        | '[' ']' ;
map     : '{' element element (element element)* '}'
        | '{' '}' ;
set     : '#{' element element* '}'
        | '#{' '}' ;

tagged: Tag WS element ;

discarded: DISCARD WS element ;

// LEXER

Character
    : '\\newline'
    | '\\return'
    | '\\space'
    | '\\tab'
    | EscapeSequence
    | ('\\' .)
    ;

NIL: 'nil';
TRUE: 'true';
FALSE: 'false';

Integer: Int 'N'?;

Float
  : Int ( 'M'
        | (FloatFrac)
        | (Exponent)
        | (FloatFrac Exponent)) ;

fragment Int: [+\-]? [0-9]+ ;

fragment FloatFrac: '.' [0-9]+ ;

fragment Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

String
    :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

DISCARD: '#_';

COMMENT : ';' ~[\r\n]* LINE_END -> skip ;
fragment LINE_END : ('\r'? '\n') | EOF ;
WS : [ ,\r\t\u000C\n]+ -> skip ;

Tag: '#' Symbol ;

Keyword: ':' Symbol ;

Symbol: '/'
      | SymbolName
      | (SymbolPrefix '/' SymbolName) ;

SymbolPrefix: SymbolPart;
SymbolName: SymbolPart;
fragment SymbolPart: [\-\+\.]? FirstSymbolChar SymbolChar*;

fragment FirstSymbolChar: [A-Za-z\.\*\+\!\-\_\?\$\%\&\=] ;
fragment SymbolChar:      [A-Za-z\.\*\+\!\-\_\?\$\%\&\=0-9\:\#] ;
