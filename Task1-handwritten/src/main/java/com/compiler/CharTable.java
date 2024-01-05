package com.compiler;

import java.util.HashMap;
import java.util.Map;

public class CharTable {
    private HashMap keyWord = new HashMap<String,EnumChar>();
    private int symLength=100;
    private String[] symTable=new String[symLength];

    private int conLength=100;
    private String[] constTable=new String[conLength];

    /**
     * 字符表
     */
    public  CharTable(){
        keyWord.put("PROGRAM",EnumChar.procsy);
        keyWord.put("BEGIN",EnumChar.beginsy);
        keyWord.put("END",EnumChar.endsy );
        keyWord.put("IF",EnumChar.ifsy );
        keyWord.put("THEN",EnumChar.thensy);
        keyWord.put("CONST",EnumChar.constsy );
        keyWord.put("VAR",EnumChar.varsy );
        keyWord.put("DO",EnumChar.dosy );
        keyWord.put("WHILE",EnumChar.whilesy );

    }

    // 判断是否是关键字，返回关键字 ；如果不是关键字，返回标识符
    public EnumChar isKeyWord(String key){
        if(keyWord.containsKey(key)){
            return  (EnumChar) keyWord.get(key);
        }else {
            return EnumChar.nul;
        }
    }

    public String[] getSymTable(){
        return symTable;
    }
    public String[] getConstTable(){
        return constTable;
    }
}
