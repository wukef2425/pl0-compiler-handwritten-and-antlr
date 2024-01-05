package com.compiler;

public class SymbolTableRow {
    private String name ;
    private EnumChar type ;              //标志符基本类型

    private boolean isAssigned;

    public SymbolTableRow(){

    }

    public SymbolTableRow(String name, EnumChar type){
        setName(name);
        setType(type);
        isAssigned = false;
    }

    public String getName() {  return name; }

    public void setName(String name) {this.name = name;}


    public EnumChar getType() { return type; }

    public void setType(EnumChar type) {
        this.type = type;
    }

    public boolean isAssigned() { return this.isAssigned; }

    public void setIsAssigned(){this.isAssigned =  true;}



    public void print(){
        System.out.printf("%8s%8s%8s\n",name,type,isAssigned);
    }
}
