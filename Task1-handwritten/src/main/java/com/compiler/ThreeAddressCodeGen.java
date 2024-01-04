package com.compiler;

import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

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

    public void printAllToFile(String filepath) {
        String targetFileName=filepath.substring(0, filepath.lastIndexOf('.'))+"-middle.txt";
        Path targetPath = Paths.get(targetFileName);
        System.out.println("输入中间代码至"+targetPath);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(targetPath.toFile(), true));
            for (int i = this.beginAddId; i <= this.addrId; i++) {

                System.out.println(i + "    " + code.get(i - beginAddId));
                writer.write(i + "    " + code.get(i - beginAddId));
                writer.newLine();  // 写入一个新行
            }
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
