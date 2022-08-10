
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol; 

%% 

%{
		// ukljucivanje informacije o poziciji tokena
		private Symbol new_symbol(int type) {
				return new Symbol(type, yyline+1, yycolumn);
		}
		// ukljucivanje informacije o poziciji tokena
		private Symbol new_symbol(int type, Object value) {
				return new Symbol(type, yyline+1, yycolumn, value);
		}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{ 
return new_symbol(sym.EOF);
%eofval}

%%

" " {}
"\b" {}
"\t" {}
"\r\n" {}
"\f" {}


"program" {return new_symbol(sym.PROG,yytext());}
"else" {return new_symbol(sym.ELSE,yytext());}
"const" {return new_symbol(sym.CONST,yytext());}
"if" {return new_symbol(sym.IF,yytext());}
"new" {return new_symbol(sym.NEW,yytext());}
"print" {return new_symbol(sym.PRINT,yytext());}
"read" {return new_symbol(sym.READ,yytext());}
"return" {return new_symbol(sym.RETURN,yytext());}
"void" {return new_symbol(sym.VOID,yytext());}
"goto" {return new_symbol(sym.GOTO,yytext());}

"+" {return new_symbol(sym.ADD,yytext());}
"-" {return new_symbol(sym.SUB,yytext());}
"*" {return new_symbol(sym.MUL,yytext());}
"/" {return new_symbol(sym.DIV,yytext());}
"%" {return new_symbol(sym.MOD,yytext());}
"==" {return new_symbol(sym.EQUAL_TO,yytext());}
"!=" {return new_symbol(sym.NOT_EQUAL_TO,yytext());}
">" {return new_symbol(sym.GREATER_THAN,yytext());}
">=" {return new_symbol(sym.GREATER_THAN_OR_EQUAL,yytext());}
"<" {return new_symbol(sym.LESS_THAN,yytext());}
"<=" {return new_symbol(sym.LESS_THAN_OR_EQUAL,yytext());}
"&&" {return new_symbol(sym.LOGICAL_AND,yytext());}
"||" {return new_symbol(sym.LOGICAL_OR,yytext());}
"=" {return new_symbol(sym.EQUAL,yytext());}
"++" {return new_symbol(sym.INC,yytext());}
"--" {return new_symbol(sym.DEC,yytext());}
";" {return new_symbol(sym.SEMICOLON,yytext());}
"," {return new_symbol(sym.COMMA,yytext());}
"." {return new_symbol(sym.DOT,yytext());}
"(" {return new_symbol(sym.LPAREN,yytext());}
")" {return new_symbol(sym.RPAREN,yytext());}
"[" {return new_symbol(sym.LSQUARE,yytext());}
"]" {return new_symbol(sym.RSQUARE,yytext());}
"{" {return new_symbol(sym.LBRACE,yytext());}
"}" {return new_symbol(sym.RBRACE,yytext());}
":" {return new_symbol(sym.COLON,yytext());}




"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" {yybegin(YYINITIAL);}

[0-9]+ {return new_symbol(sym.NUM_CONST, new Integer (yytext()));}
"'"."'" {return new_symbol(sym.CHAR_CONST,new Character(yytext().charAt(1)));}
"true" | "false" {return new_symbol(sym.BOOL_CONST,yytext().equals("true")? 1 : 0);}
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

. {System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1));}


