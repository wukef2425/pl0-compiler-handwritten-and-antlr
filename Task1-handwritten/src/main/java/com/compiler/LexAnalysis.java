package com.compiler;

import java.io.*;

public class LexAnalysis {

    private KeywordTable keywordTable = new KeywordTable();// 词法分析表


    private char ch = ' '; // 当前字符
    private EnumChar sy = EnumChar.nul; // 当前符号
    private String strToken; // 当前标记字符串
    private String filename; // 文件名
    private char[] buffer; // 字符缓冲区
    private int searchPtr = 0; // 搜索指针
    private static int line = 1; // 当前行号
    static int isNewLine = 0; // 判断是否是新的一行

    // 构造函数
    public LexAnalysis(String _filename) {
        filename = _filename;
    }

    /**
     * 预处理函数：
     * 功能：读取源文件内容到字符数组buffer中去，包括换行符
     */
    public void preManage() {
        File file = new File(filename);
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(file));
            String temp1 = "", temp2 = "";
            // 逐行读取文件内容
            while ((temp1 = bf.readLine()) != null) {
                temp2 = temp2 + temp1 + String.valueOf('\n');
            }
            buffer = temp2.toCharArray(); // 将内容存入字符数组buffer
            bf.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
        } catch (IOException e) {
            System.out.println("文件无法打开");
        }
    }

    /**
     * 获取下一个符号DFA process
     *
     * @return {@link String}
     */
    public String nextToken() {
        isNewLine = 0;// 重置isNewLine标志，表示当前不是新的一行。
        strToken = "";//清空当前标记字符串
        getBC(); // 去除空格符

        if (isLetter()) { // 如果当前字符是字母，表示可能是标识符或关键字
            //读取字符并连接到当前标记字符串中，直到不是字母或数字为止
            do {
                concat();// 连接字符到当前标记字符串
                getChar();// 获取下一个字符
            } while (isLetter() || isDigit());

            if(isAllUpperCase(strToken)) {//全是大写
                sy = keywordTable.isKeyWord(strToken);// 判断当前标记是否是关键字。如果是关键字，将对应的符号值赋给sy,否则也不是标识符，将sy置为nul。
            } else if(isAllLowerCase(strToken)) {//全是小写和数字
                sy = EnumChar.ident;//表示标识符。
            }
            if(sy == EnumChar.nul){
                EnumErrors.error(EnumErrors.illegalChar);
            }

        } else if (isDigit()) { // 如果当前字符是数字，表示可能是整数。

            sy = EnumChar.intcon;//表示整数。
            do {//读取字符并连接到当前标记字符串中，直到不是数字为止。
                concat();
                getChar();
            } while (isDigit());

        } else if (ch=='+'){
            sy=EnumChar.plussy;
            getChar();
        }else if (ch=='-'){
            sy=EnumChar.minussy;
            getChar();
        }else if (ch=='*'){
            sy=EnumChar.multisy;
            getChar();
        }else if (ch=='/'){
            sy=EnumChar.divsy;
            getChar();
        }else if (ch == ':') { //  如果当前字符是冒号，表示可能是赋值符
            concat();
            getChar();
            if (ch == '=') {// 。
                concat();
                sy = EnumChar.becomes;
                getChar();
            } else {//如果不是等号，表示冒号，没有这个符号，猜测用户是想写赋值符
                getChar();
                EnumErrors.error(EnumErrors.assignEqual); // 赋值符应该有等号
            }
        } else if (ch=='='){
            sy=EnumChar.eql;
            getChar();
        }else if (ch == '<') { //如果当前字符是小于号，表示可能是小于或小于等于。
            concat();
            getChar();

            if (ch == '=') {//读取下一个字符，如果是等号，则表示小于等于，
                concat();
                sy = EnumChar.leq ;
                getChar();
            } else if (ch == '>') {//读取下一个字符，如果是大于号，则表示不等于
                concat();

                sy = EnumChar.neq ;
                getChar();
            } else sy = EnumChar.lss ;
        } else if (ch == '>') { // 大于，大于等于
            concat();
            getChar();

            if (ch == '=') {//读取下一个字符，如果是等号，则表示大于等于
                concat();
                sy = EnumChar.geq ;
                getChar();
            } else sy = EnumChar.gtr;
        }  else if (ch=='('){
            sy=EnumChar.lparent;
            getChar();
        } else if (ch==')'){
            sy=EnumChar.rparent;
            getChar();
        } else if (ch==';'){
            sy=EnumChar.semicolon;
            getChar();
        }else if (ch==','){
            sy=EnumChar.comma;
            getChar();
        }else if (ch == '\n') { //结束符
            System.out.println("扫描结束");
        }else{
            EnumErrors.error(EnumErrors.illegalChar);
            getChar();
        }
//        } else { // 读取其他合法字符
//            concat();
//            sy = charTable.isPunctuationMark(ch + "");//调用charTable.isPunctuationMark(ch + "")判断当前字符是否为合法字符，并将对应的符号值赋给sy。
//            if (EnumChar.nul==sy) {
//                EnumErrors.error(EnumErrors.illegalChar); //如果符号值为nul，表示字符非法，调用Errors.error(2)报告非法字符的错误。
//            }
//            getChar();
//        }
        return strToken;
    }

    // 获取当前字符
    public char getChar() {
        if (searchPtr < buffer.length) {// 判断是否到达文件尾
            ch = buffer[searchPtr];// 获取当前字符
            searchPtr++;        // 搜索指针后移
        }
        return ch;// 返回当前字符
    }

    // 去除空格符
    public void getBC() {
        while ((ch == ' ' || ch == '	' || ch == '\n') && (searchPtr < buffer.length)) {
            if (ch == '\n') {
                line++;
                isNewLine = 1;
            }
            getChar();
        }
    }

    // 连接字符到当前标记字符串
    public void concat() {
        strToken = strToken + String.valueOf(ch);
    }

    // 判断是否为字母
    public boolean isLetter() {
        return Character.isLetter(ch);
    }

    // 判断是否为数字
    public boolean isDigit() {
        return Character.isDigit(ch);
    }

    // 获取当前标记字符串
    public String getStrToken() {
        return strToken;
    }


    // 获取当前行号
    public static int getLine() {
        return line - isNewLine;
    }

    // 获取当前符号
    public EnumChar getType() {
        return sy;
    }

    //判断字符串中的字符是否全都是大写字母
    public boolean isAllUpperCase(String str){
        for(int i=0;i<str.length();i++){
            if(!Character.isUpperCase(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    //判断字符串中的字符只有小写字母和数字
    public boolean isAllLowerCase(String str){
        for(int i=0;i<str.length();i++){
           //如果既不是小写字母也不是数字，返回false
            if(!Character.isLowerCase(str.charAt(i))&&!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
