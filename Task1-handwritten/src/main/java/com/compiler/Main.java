package com.compiler;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename;
        Scanner scanner = new Scanner("src/main/resources/demo.txt");
        filename = scanner.next();
        LexAnalysis lex;
        lex = new LexAnalysis(filename);
        lex.preManage();//读取源文件内容到字符数组buffer中去，包括换行符

        String token;
        do {
            token = lex.nextSym();
            System.out.println("Token: " + token);
        } while (!token.isEmpty());
    }
}
