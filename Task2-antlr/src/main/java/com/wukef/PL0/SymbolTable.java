package com.wukef.PL0;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private static class Symbol {
        String name;
        boolean isAssigned;
        boolean isUsed;
        boolean isVar; // 变量是true, 常量是false
        int lineDeclared;

        Symbol(String name, boolean isVar, int lineDeclared) {
            this.name = name;
            this.isVar = isVar;
            this.isAssigned = false;
            this.isUsed = false;
            this.lineDeclared = lineDeclared;
        }
    }

    private HashMap<String, Symbol> symbols = new HashMap<>();

    public void declare(String name, boolean isVar, int line) throws Exception {
        if (symbols.containsKey(name)) {
            Symbol existing = symbols.get(name);
            throw new Exception("Semantic Error: " + name + " is already defined at line " + existing.lineDeclared);
        }
        symbols.put(name, new Symbol(name, isVar, line));
    }

    public void assign(String name, int line) throws Exception {
        Symbol symbol = symbols.get(name);
        if (symbol == null) {
            throw new Exception("Semantic Error: " + name + " is not defined (at line " + line + ")");
        }
        if (!symbol.isVar) {
            throw new Exception("Semantic Error: Cannot assign to constant " + name + " (declared at line " + symbol.lineDeclared + ")");
        }
        symbol.isAssigned = true;
    }

    public void use(String name, int line) throws Exception {
        Symbol symbol = symbols.get(name);
        if (symbol == null) {
            throw new Exception("Semantic Error: " + name + " is not defined (at line " + line + ")");
        }
        if (!symbol.isAssigned && symbol.isVar) {
            throw new Exception("Semantic Error: " + name + " is not assigned before use (declared at line " + symbol.lineDeclared + ")");
        }
        symbol.isUsed = true;
    }

    public void checkUsage() throws Exception {
        for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
            Symbol symbol = entry.getValue();
            if (!symbol.isUsed) {
                throw new Exception("Semantic Error: The " + (symbol.isVar ? "variable" : "constant") +
                        " '" + symbol.name + "' declared at line " + symbol.lineDeclared +
                        " is not used.");
            }
        }
    }

    public void printSymbolTable() {
        System.out.println("Symbol Table:");
        System.out.println("----------------------------------------------------");
        System.out.printf("%-10s %-10s %-12s %-10s %-10s\n", "Name", "Type", "Declared At", "Assigned", "Used");
        for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
            Symbol symbol = entry.getValue();
            System.out.printf("%-10s %-10s %-12d %-10b %-10b\n",
                    symbol.name,
                    symbol.isVar ? "Variable" : "Constant",
                    symbol.lineDeclared,
                    symbol.isAssigned,
                    symbol.isUsed);
        }
        System.out.println("----------------------------------------------------");
    }
}