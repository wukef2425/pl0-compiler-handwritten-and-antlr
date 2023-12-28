package com.compiler;

import java.util.HashMap;
import java.util.Map;

public class CharTable {
    private HashMap keyWord = new HashMap<String,Integer>();
    private HashMap punctuationMark = new HashMap<String,Integer>();
    private int symLength=100;
    private String[] symTable=new String[symLength];

    private int conLength=100;
    private String[] constTable=new String[conLength];

    /**
     * 字符表
     */
    public  CharTable(){
        keyWord.put("PROGRAM",21);
        keyWord.put("BEGIN",23);
        keyWord.put("END",30);
        keyWord.put("CONST",19);
        keyWord.put("VAR",20);
        keyWord.put("WHILE",25);
        keyWord.put("DO",32);
        keyWord.put("IF",24);
        keyWord.put("THEN",33);
        punctuationMark.put("+",3);
        punctuationMark.put("-",2);
        punctuationMark.put("*",4);
        punctuationMark.put("/",5);
        punctuationMark.put("(",12);
        punctuationMark.put(")",13);
        punctuationMark.put("=",6);
        punctuationMark.put(",",14);
        punctuationMark.put(":",17);
        punctuationMark.put(";",15);
    }

    // 判断是否是关键字，返回关键字的编码；如果不是关键字，返回22表示标识符
    public int isKeyWord(String key){
        if(keyWord.containsKey(key)){
            return (Integer) keyWord.get(key);
        }else {
            return 22;//RETURN ident 返回22表示标识符
        }
    }

    // 判断是否是标点符号，返回标点符号的编码；如果不是标点符号，返回0表示空
    public int isPunctuationMark(String mark){
        if(punctuationMark.containsKey(mark)){
            return (Integer) punctuationMark.get(mark);
        }else {
            return 0;// return nul
        }
    }
    public String[] getSymTable(){
        return symTable;
    }
    public String[] getConstTable(){
        return constTable;
    }
}
