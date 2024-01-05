package com.compiler;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的代码路径：");
        String filepath = scanner.nextLine();
        //"src\main\resources\demo1.txt"
        //"src\main\resources\demo2.txt"
        //src\main\resources\demo3.txt
        //src\main\resources\demo4.txt

        //词法分析测试程序
//        LexAnalysis lex;
//        lex = new LexAnalysis(filename);
//        lex.preManage();//读取源文件内容到字符数组buffer中去，包括换行符
//
//
//        String token;
//        do {
//            token = lex.nextToken();
//            System.out.println("Token: " + token);
//        } while (!token.isEmpty());

        Parser p=new Parser(filepath);
        p.analysis();

    }
}
