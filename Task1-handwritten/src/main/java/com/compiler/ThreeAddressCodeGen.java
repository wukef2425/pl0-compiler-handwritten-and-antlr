package com.compiler;

import java.util.ArrayList;

public class ThreeAddressCodeGen {
    ArrayList<String> code = new ArrayList<>();

    int beginAddId=100;

    int addrId=beginAddId-1;

    public ThreeAddressCodeGen(){

    }

    public int  emit(String s){
        code.add(s);
        addrId++;

        return addrId;
    }

    public int getCurrentAddrId() {
         return addrId;
    }
    public int nextAddr(){
        code.add("");
        addrId++;
        return addrId;
    }

    public void setAddrCode(int id,String s)
    {
        code.set(id - beginAddId, s);
    }

    public void printAll() {
        for (int i = beginAddId; i <= addrId; i++) {
            System.out.println(i+"    "+code.get(i-beginAddId));
        }
    }

    public void printToFile(){

    }
}
