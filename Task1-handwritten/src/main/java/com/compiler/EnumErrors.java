package com.compiler;


public enum EnumErrors {
    longIdent("标识符过长"),
    longInt("整数过长"),
    illegalChar("一个非法的字符串"),
    assignEqual("赋值符应该有等号"),
    noProgram("没有PROGRAM关键字"),
    noProgramIdent("PROGRAM没有标识符"),
    symbolTableOverflow("符号表溢出"),
    identiOverDefine("标识符重定义"),
    undefinedIdent("标识符未定义"),
    illegalConstVal("非法的常量值"),
    noEqlforConst("没有等号为常量赋值"),
    noConstDeclaration("没有常量定义式"),

    noCommaSeperateConst("常量声明间没有逗号分隔"),
    noCommaSeperateVar("变量声明间没有逗号分隔"),
    noVarDeclaration("没有变量定义式"),
    constAssigenment("常量不能被赋值"),
    noAssignToUse("使用的变量没有被赋值"),
    noBecomes("缺少赋值符号"),
    noRparent("缺少右括号"),
    illegalRelationalOperator("非法的关系运算符"),
    noThen("IF语句缺少THEN"),

    noDo("WHILE语句缺少DO"),
    exInnerFault("表达式内部有误"),
    noEnd("复合语句缺少END"),
    noEof("额外的输入,应有文件结束符");


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

