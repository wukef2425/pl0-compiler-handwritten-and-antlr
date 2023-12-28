package com.compiler;

public class SymbolTableRow {
    private String name ;
    private int type ;              //标志符基本类型

    public SymbolTableRow(){

    }

    public SymbolTableRow(String name, int type){
        setName(name);
        setType(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public void print(){
        System.out.printf("%8s%8s\n",name,type);
    }
}
