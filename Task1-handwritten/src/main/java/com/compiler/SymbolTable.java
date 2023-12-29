package com.compiler;

public class SymbolTable {

    //TableRow是符号表中的每一行
    //tablePtr指向符号表中已经填入值最后一项
    private int rowMax = 50;           //最大表长
    private SymbolTableRow[] table = new SymbolTableRow[rowMax+1];          //rowMax行
    private int tablePtr = 0;

    public boolean isFull() {
        return tablePtr == rowMax;
    }

    //记得是从index=1开始存的！
    public void enterTable(String name, EnumChar type) {
        tablePtr++;
        table[tablePtr].setName(name);
        table[tablePtr].setType(type);
    }

    public int getTablePtr() {
        return tablePtr;
    }

    public SymbolTableRow getRow(int i){return table[i];}

    public int checkExistence(String name){

        for (int i = 1; i <=tablePtr; i++) {

            if(name.equals(table[i].getName())){

                return i;
            }
        }
        return 0;
    }

    public void printTable() {
        System.out.println("    name    type");
        for (int i = 1; i <=tablePtr; i++) {
            table[i].print();
        }
    }
}
