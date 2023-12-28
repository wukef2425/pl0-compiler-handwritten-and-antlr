package com.compiler;


public enum EnumErrors {
    longIdent("标识符过长"),
    longInt("整数过长"),
    illegalChar("一个非法的字符"),
    assignEqual("赋值符应该有等号"),
    noProgram("没有PROGRAME关键字"),




    private final String tip;
    private static int num = 0;
    EnumErrors(String tip) {
        this.tip = tip;
    }

    public static void error(EnumErrors error) {
        System.out.println("Line " + LexAnalysis.getLine() + ":Error :" + error.tip);
        num++;
    }

    public static int getNum() {
        return num;
    }
}
//public class Errors {
//    private static String[] msgs = {
//            "程序不完整",
//            "整数过长",
//            "一个非法的字符",
//            "符号表溢出",
//            "标识符重定义",
//            "未定义的标识符",
//            "非法的常量值",
//            "赋值符应该有等号",
//            "缺少右括号",
//            "常量赋值符应该为等号",
//            "应该有等号为常量赋值",
//            "每个常量之间应该有逗号分隔",
//            "常量声明结尾应该有分号",
//            "逗号后面必须有一个标识符",
//            "没有过程名",
//            "过程声明结尾应该有分号",
//            "过程不能参与运算",
//            "非法的因子",
//            "缺少赋值符号",
//            "复合语句中每条语句后面应该有分号",
//            "缺少end",
//            "应该有do",
//            "缺少左括号",
//            "应该是一个标识符",
//            "不合法的语句起始符",
//            "分程序必须有一个复合语句部分",
//            "常量不能被赋值",
//            "Read中实参必须是变量",
//            "Call中实参必须是分程序",
//            "程序结尾缺少句号",
//            "每个变量之间应该有逗号分隔",
//            "变量声明结尾应该有分号",
//            "嵌套层次过多",
//            "分程序表溢出",
//            "每个分程序结束应有分号",
//            "缺少then",
//            "非法的关系运算符",
//            "表达式有误"
//    };
//    private static int num = 0;
//
//    public static void error(int i) {
//        System.out.println("Line " + LexAnalysis.getLine() + ":Error" + i + ":" + msgs[i]);
//        num++;
//    }
//
//    public static int getNum() {
//        return num;
//    }
//}
