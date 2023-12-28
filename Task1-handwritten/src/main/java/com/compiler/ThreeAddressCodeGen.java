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

}
