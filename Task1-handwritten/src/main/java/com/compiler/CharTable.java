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
        keyWord.put("PROGRAM",EnumChar.procsy.ordinal());
        keyWord.put("BEGIN",EnumChar.beginsy.ordinal());
        keyWord.put("END",EnumChar.endsy.ordinal());
        keyWord.put("IF",EnumChar.ifsy.ordinal());
        keyWord.put("THEN",EnumChar.thensy.ordinal());
        keyWord.put("CONST",EnumChar.constsy.ordinal());
        keyWord.put("VAR",EnumChar.varsy.ordinal());
        keyWord.put("DO",EnumChar.dosy.ordinal());
        keyWord.put("WHILE",EnumChar.whilesy.ordinal());
        punctuationMark.put("+",EnumChar.plussy.ordinal());
        punctuationMark.put("-",EnumChar.minussy.ordinal());
        punctuationMark.put("*",EnumChar.multisy.ordinal());
        punctuationMark.put("/",EnumChar.divsy.ordinal());
        punctuationMark.put("(",EnumChar.lparent.ordinal());
        punctuationMark.put(")",EnumChar.rparent.ordinal());
        punctuationMark.put("=",EnumChar.eql.ordinal());
        punctuationMark.put(",",EnumChar.comma.ordinal());
        punctuationMark.put(":",EnumChar.colon.ordinal());
        punctuationMark.put(";",EnumChar.semicolon.ordinal());
    }

    // 判断是否是关键字，返回关键字的编码；如果不是关键字，返回22表示标识符
    public int isKeyWord(String key){
        if(keyWord.containsKey(key)){
            return (Integer) keyWord.get(key);
        }else {
            return EnumChar.ident.ordinal();//RETURN ident 返回22表示标识符
        }
    }

    // 判断是否是标点符号，返回标点符号的编码；如果不是标点符号，返回0表示空
    public int isPunctuationMark(String mark){
        if(punctuationMark.containsKey(mark)){
            return (Integer) punctuationMark.get(mark);
        }else {
            return EnumChar.nul.ordinal();// return nul
        }
    }
    public String[] getSymTable(){
        return symTable;
    }
    public String[] getConstTable(){
        return constTable;
    }
}
