package com.compiler;

import java.util.ArrayList;

public class ThreeAddressCodeGen {
    ArrayList<String> code = new ArrayList<>();

    int beginAddId=100;

    int addrId=beginAddId-1;

    public ThreeAddressCodeGen(){

    }

    public int  emit(String s){
        this.code.add(s);
        this.addrId++;

        return addrId;
    }

    public int getCurrentAddrId() {
         return addrId;
    }
    public int nextAddr(){
        this.code.add("");
        this.addrId++;
        return this.addrId;
    }

    public void setAddrCode(int id,String s)
    {
        code.set(id - beginAddId, s);
    }

    public void printAll() {
        for (int i = this.beginAddId; i <= this.addrId; i++) {

            System.out.println(i+"    "+code.get(i-beginAddId));
        }
    }

    public void printToFile(){

    }
}
