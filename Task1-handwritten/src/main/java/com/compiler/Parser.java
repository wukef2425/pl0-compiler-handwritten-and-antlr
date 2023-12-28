package com.compiler;

public class Parser {
    LexAnalysis lex;

    private SymbolTable STable = new SymbolTable();
    private String filename;
    public Parser(String filename) {
        lex = new LexAnalysis(filename);
        lex.preManage();
        this.filename=filename;
    }
    int tempId=0;
    private ThreeAddressCodeGen codeGen=new ThreeAddressCodeGen(); //生成及存放三地址代码
    private int getNewTempId(){
        tempId++;
        return tempId;
    }
    public boolean ParserAnalysis() {
        lex.nextSym();
        block();
        if (EnumErrors.getNum() == 0 || true) {
            System.out.println("三地址代码：");
            codeGen.printAll();
            System.out.println("符号表：");
            STable.printTable();
        }
        return EnumErrors.getNum() != 0;
    }

    public void block() {
        header();


    }
    public void header(){
        if(lex.getSy()!=EnumChar.procsy.ordinal()){
            EnumErrors.error(EnumErrors.noProgram);
        }

    }
}
