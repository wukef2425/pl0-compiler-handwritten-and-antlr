package com.compiler;

public enum EnumChar {
    nul,
    //关键字
    procsy,
    beginsy,
    endsy,
    constsy,
    varsy,
    whilesy,
    dosy,
    ifsy,
    thensy,
    //标识符，
    ident,
    //整数：数字开头的数字串
    intcon,
    //数值运算符
    minussy,
    plussy,
    multisy,
    divsy,
    //赋值运算符
    becomes,
    //关系运算符
    eql,neq,gtr,geq,lss,leq,
    //括号
    lparent,rparent,
    //标点符号
    comma,//逗号
    semicolon,//分号
}
