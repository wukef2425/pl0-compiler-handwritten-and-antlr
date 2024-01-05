package com.wukef;

import com.wukef.PL0.SymbolTable;
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
        CharStream cs = CharStreams.fromString("PROGRAM test\n" +
                "VAR n, sum, i, temp, notused;\n" +
                "\n" +
                "BEGIN\n" +
                "n := 10;\n" +
                "sum := 0;\n" +
                "i := 1;\n" +
                "\n" +
                "WHILE i <= n DO\n" +
                "BEGIN\n" +
                "sum := sum + i;\n" +
                "temp := sum;\n" +
                "\n" +
                "IF temp = 55 THEN\n" +
                "BEGIN\n" +
                "temp := temp + 1;\n" +
                "END;\n" +
                "\n" +
                "i := i + 1;\n" +
                "END;\n" +
                "END");
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
        visitor.printResult();
    }
}