package com.wukef;

import com.wukef.PL0.pl0VisitorImpl;
import com.wukef.PL0.pl0Lexer;
import com.wukef.PL0.pl0Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CharStream cs = CharStreams.fromString("PROGRAM add\n" +
                "CONST test1:=1,test2:=2;\n" +
                "VAR x,y;\n" +
                "BEGIN\n" +
                "x:=+10*2+3;\n" +
                "y:=2;\n" +
                "WHILE x<50 DO x:=x+1;\n" +
                "IF y>0 THEN y:=y-1;\n" +
                "y:=y+x\n" +
                "END\n");
//        CharStream cs = CharStreams.fromString("PROGRAM ad3d\n" +
//                "VAR x,dgey,gy6;\n");
//        CharStream cs = CharStreams.fromString("PROGRAM add\n" +
//                "VAR x,y;\n" +
//                "x:=-(2*2+3)+(y+z-zx)*2\n");
        pl0Lexer lexer = new pl0Lexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        pl0Parser parser = new pl0Parser(tokens);
        // 'program' 是顶级语法规则名
        ParseTree tree = parser.program();

        pl0VisitorImpl visitor = new pl0VisitorImpl();
        visitor.visit(tree);
    }
}