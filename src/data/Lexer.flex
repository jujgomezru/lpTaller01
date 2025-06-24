package model.scanner;
import model.scanner.Token;
import model.scanner.TokenType;
%%
%class Lexer
%type Token
%public
%line
%column
%{
    /*Estos son los lexemas, se implementan en otro orden por eso las listas de aqui abajo
    solo sirven para tener una idea de los lexemas que se pueden encontrar en el código*/
    // String[] palabrasReservadas_RW = {"if", "else", "while", "for", "return", "break", "continue", "fn", "bool", "int", "float", "string", "true", "false", "null"};
    // String[] palabrasReservadas_RW_Extra = {"mean", "max", "min", "median", "mode"};
    // String[] identificadores_ID = {"Nombres de variables", "funciones", "procedimientos", "tipos de datos"};
    // String[] operadoresAritmeticos_OP_AR = {"+", "-", "*", "/", "%"};
    // String[] operadoresRelacionales_OP_REL = {"<", "<=", ">", ">=", "==", "!="};
    // String[] operadoresLogicos_OP_LOG = {"&&", "||", "!"};
    // String[] operadorAsignacion_OP_ASIGN = {"="};
    // String[] delimitadoresParentesis_DEL_LR = {"(", ")", "[", "]", "{", "}"};
    // String[] delimitadoresComillasComentarios_COM = {"\"", "'"};
    // String[] delimitadoresPuntuacion_DEL_PUNT = {";", ",", ".", ":"};
    // String[] literalesNumericos = {"MAX_INT", "MAX_FLOAT"};
    // String[] literalesCadena = {"\"\"", "'C'"};
    // String[] literalesBooleanos_LIT_BOOL = {"true", "false"};
    // String[] comentarios = {"Se utilizan para documentar el código y no son interpretados por el compilador. comienzan con #"};

    // String[] ALL_TOKENS = ['IF', 'ELSE', 'ELIF', 'WHILE', 'FOR', 'RETURN', 'BREAK', 'CONTINUE', 'FN', 'BOOL', 'INT', 'FLOAT', 'STRING', 'TRUE', 'FALSE', 'NULL', 'MEAN', 'MAX', 'MIN', 'MEDIAN', 'MODE', 'OP_SUMA', 'OP_RESTA', 'OP_MULT', 'OP_DIV', 'OP_MOD', 'OP_MENOR', 'OP_MENOR_IGUAL', 'OP_MAYOR', 'OP_MAYOR_IGUAL', 'OP_IGUAL', 'OP_DIFERENTE', 'OP_AND', 'OP_OR', 'OP_NOT', 'OP_ASIGN', 'L_PARENTESIS', 'R_PARENTESIS', 'L_LLAVE', 'R_LLAVE', 'L_CORCHETE', 'R_CORCHETE', 'COMA', 'PUNTO_COMA', 'PUNTO', 'DOS_PUNTOS', 'NUM_ENTERO', 'NUM_FLOTANTE', 'STRING', 'E_NUM_START_ZERO', 'E_ID_NO_', 'E_SIMB_NOT_FOUND']

    /* Explicación: se usa un enum de java que "enumera" cada nombre con un numero, esto se hace porque aun no se puede usar Symbol y Token de java_cup.runtime porque se necesita un archivo CUP y aun no se puede usar la libreria CUP porque hace parte del taller 2 y el profe prohibió eso, asi que en taller 1 --> enum */
    
    private Token token(TokenType type, String lexeme, int line, int column){
        return new Token(type, lexeme, line, column);
    }
%}
  
/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "#" {EntradaDeCaracter}*
FinDeLineaComentario = "#" {EntradaDeCaracter}* {TerminadorDeLinea}?
ComentarioDeDocumentacion = "\\\"\\\"\\\"(\\\\.|[^\\\\])*\\\"\\\"\\\"" | "\\'\\'\\'(\\\\.|[^\\\\])*\\'\\'\\'"

Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Identificador */
Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*

/* Número */
Numero = -?(0 | [1-9][0-9]*)

/* Número entero */
NumeroEntero = {Numero}

/* Número flotante */
NumeroFlotante = {NumeroEntero} "." [0-9]+

/* Booleano */
//Booleano = "true" | "false"

/* String */
String = \"(\\.|[^\"\\])*\" | \'(\\.|[^\'\\])*\'

%%

{NumeroEntero} { return token(TokenType.LIT_INT, yytext(), yyline, yycolumn); }
{NumeroFlotante} { return token(TokenType.LIT_FLOAT, yytext(), yyline, yycolumn); }

/* Palabras reservadas */
"in" { return token(TokenType.IN, yytext(), yyline, yycolumn); }
"range" { return token(TokenType.RANGE, yytext(), yyline, yycolumn); }
"if" { return token(TokenType.IF, yytext(), yyline, yycolumn); }
"else" { return token(TokenType.ELSE, yytext(), yyline, yycolumn); }
"elif" { return token(TokenType.ELIF, yytext(), yyline, yycolumn); }
"while" { return token(TokenType.WHILE, yytext(), yyline, yycolumn); }
"for" { return token(TokenType.FOR, yytext(), yyline, yycolumn); }
"return" { return token(TokenType.RETURN, yytext(), yyline, yycolumn); }
"break" { return token(TokenType.BREAK, yytext(), yyline, yycolumn); }
"continue" { return token(TokenType.CONTINUE, yytext(), yyline, yycolumn); }
"fn" { return token(TokenType.FN, yytext(), yyline, yycolumn); }
"bool" { return token(TokenType.BOOL, yytext(), yyline, yycolumn); }
"int" { return token(TokenType.INT, yytext(), yyline, yycolumn); }
"float" { return token(TokenType.FLOAT, yytext(), yyline, yycolumn); }
"string" { return token(TokenType.STRING, yytext(), yyline, yycolumn); }
"modeloDosVias" {return token(TokenType.MODELODOSVIAS, yytext(), yyline, yycolumn); }
"efectos" {return token(TokenType.EFECTOS, yytext(), yyline, yycolumn); }
"racha" {return token(TokenType.RACHA, yytext(), yyline, yycolumn); }
"true" { return token(TokenType.TRUE, yytext(), yyline, yycolumn); }
"false" { return token(TokenType.FALSE, yytext(), yyline, yycolumn); }
"null" { return token(TokenType.NULL, yytext(), yyline, yycolumn); }
"print" { return token(TokenType.PRINT, yytext(), yyline, yycolumn); }

/* Extra: Palabras reservadas, para implementaciones estadisticas (Problema del proyecto)*/
"mean" { return token(TokenType.MEAN, yytext(), yyline, yycolumn); }
"max" { return token(TokenType.MAX, yytext(), yyline, yycolumn); }
"min" { return token(TokenType.MIN, yytext(), yyline, yycolumn); }
"median" { return token(TokenType.MEDIAN, yytext(), yyline, yycolumn); }
"mode" { return token(TokenType.MODE, yytext(), yyline, yycolumn); }
"sin" { return token(TokenType.SIN, yytext(), yyline, yycolumn); }
"cos" { return token(TokenType.COS, yytext(), yyline, yycolumn); }
"tan" { return token(TokenType.TAN, yytext(), yyline, yycolumn); }
"sec" { return token(TokenType.SEC, yytext(), yyline, yycolumn); }
"csc" { return token(TokenType.CSC, yytext(), yyline, yycolumn); }
"cot" { return token(TokenType.COT, yytext(), yyline, yycolumn); }
"sinh" { return token(TokenType.SINH, yytext(), yyline, yycolumn); }
"cosh" { return token(TokenType.COSH, yytext(), yyline, yycolumn); }
"tanh" { return token(TokenType.TANH, yytext(), yyline, yycolumn); }





/* Identificador */
{Identificador} { return token(TokenType.IDENTIFICADOR, yytext(), yyline, yycolumn); }

/* Operadores aritméticos */
"+" { return token(TokenType.OP_SUMA, yytext(), yyline, yycolumn); }
"-" { return token(TokenType.OP_RESTA, yytext(), yyline, yycolumn); }
"*" { return token(TokenType.OP_MULT, yytext(), yyline, yycolumn); }
"/" { return token(TokenType.OP_DIV, yytext(), yyline, yycolumn); }
"%" { return token(TokenType.OP_MOD, yytext(), yyline, yycolumn); }
"^" { return token(TokenType.OP_POT, yytext(), yyline, yycolumn); }
"~" { return token(TokenType.OP_MCTMZ, yytext(), yyline, yycolumn); }
/* Operadores relacionales */
"<" { return token(TokenType.OP_MENOR, yytext(), yyline, yycolumn); }
"<=" { return token(TokenType.OP_MENOR_IGUAL, yytext(), yyline, yycolumn); }
">" { return token(TokenType.OP_MAYOR, yytext(), yyline, yycolumn); }
">=" { return token(TokenType.OP_MAYOR_IGUAL, yytext(), yyline, yycolumn); }
"==" { return token(TokenType.OP_IGUAL, yytext(), yyline, yycolumn); }
"!=" { return token(TokenType.OP_DIFERENTE, yytext(), yyline, yycolumn); }

/* Operadores lógicos */
"&" { return token(TokenType.OP_AND, yytext(), yyline, yycolumn); }
"|" { return token(TokenType.OP_OR, yytext(), yyline, yycolumn); }
"!" { return token(TokenType.OP_NOT, yytext(), yyline, yycolumn); }

/* Operador de asignación */
"=" { return token(TokenType.OP_ASIGN, yytext(), yyline, yycolumn); }

/* Operadores de agrupación o delimitadores parentesis */
"(" { return token(TokenType.L_PARENTESIS, yytext(), yyline, yycolumn); }
")" { return token(TokenType.R_PARENTESIS, yytext(), yyline, yycolumn); }
"{" { return token(TokenType.L_LLAVE, yytext(), yyline, yycolumn); }
"}" { return token(TokenType.R_LLAVE, yytext(), yyline, yycolumn); }
"[" { return token(TokenType.L_CORCHETE, yytext(), yyline, yycolumn); }
"]" { return token(TokenType.R_CORCHETE, yytext(), yyline, yycolumn); }

/* Comentarios o espacios en blanco */
{Comentario} { return token(TokenType.COMENTARIO, yytext(), yyline, yycolumn); }
{EspacioEnBlanco} {/* Ignorar */}
/* Signos de puntuación */
"," { return token(TokenType.COMA, yytext(), yyline, yycolumn); }
";" { return token(TokenType.PUNTO_COMA, yytext(), yyline, yycolumn); }
"." { return token(TokenType.PUNTO, yytext(), yyline, yycolumn); }
":" { return token(TokenType.DOS_PUNTOS, yytext(), yyline, yycolumn); }

/* Literales */

{String} { return token(TokenType.LIT_STRING, yytext(), yyline, yycolumn); }

/* null, true, false definidos en palabras reservadas también cuentan como literales */

/* Errores */
0 {Numero}+ { return token(TokenType.E_NUM_START_ZERO, yytext(), yyline, yycolumn); }
. { return token(TokenType.E_SIMB_NOT_FOUND, yytext(), yyline, yycolumn); }
{Numero}+{Identificador} { return token(TokenType.E_ID_START_DIGIT, yytext(), yyline, yycolumn); }

/*Fin de archivo, importante a futuro para el analizador sintactico + semantica*/
<<EOF>> { return new Token(TokenType.EOF, "", yyline, yycolumn); }