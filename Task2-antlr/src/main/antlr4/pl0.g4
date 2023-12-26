grammar pl0;

program     : programHeader block EOF ;
programHeader : 'PROGRAM' ident ;

block       : (constDeclaration)? (varDeclaration)? statement ;

constDeclaration : 'CONST' constDefinition (',' constDefinition)* ';' ;
constDefinition  : ident ':=' number ;

varDeclaration : 'VAR' ident (',' ident)* ';' ;

statement   : assignmentStatement
            | conditionStatement
            | loopStatement
            | compoundStatement
            | emptyStatement
            ;

assignmentStatement : ident ':=' expression ;
conditionStatement  : 'IF' condition 'THEN' statement ;
loopStatement       : 'WHILE' condition 'DO' statement ;
compoundStatement   : 'BEGIN' statement (';' statement)* 'END' ;
emptyStatement      : /* nothing */ ;

expression  : (addOp)? term (addOp term)* ;
term        : factor (multOp factor)* ;

factor      : ident
            | number
            | '(' expression ')'
            ;

addOp       : '+' | '-' ;
multOp      : '*' | '/' ;

condition   : expression relationOp expression ;
relationOp  : '=' | '<>' | '<' | '<=' | '>' | '>=' ;

ident       : LETTER (LETTER | DIGIT)* ;
number      : DIGIT+ ;
LETTER      : [a-z] ;
DIGIT       : [0-9] ;

WS          : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines