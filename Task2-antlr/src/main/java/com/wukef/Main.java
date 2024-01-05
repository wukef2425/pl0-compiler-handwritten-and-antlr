package com.wukef;

import com.wukef.PL0.pl0VisitorImpl;
import com.wukef.PL0.pl0Lexer;
import com.wukef.PL0.pl0Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String inputFilePath;

        if (args.length < 1) {
            // 用 Scanner 读文件
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the path to the source file:");
            inputFilePath = scanner.nextLine();
            scanner.close();
        } else {
            inputFilePath = args[0];
        }

        Path inputPath = Paths.get(inputFilePath);
        if (!Files.exists(inputPath) || Files.isDirectory(inputPath)) {
            System.out.println("The specified file does not exist or is a directory.");
            return;
        }

        try {
            CharStream cs = CharStreams.fromPath(inputPath);

            pl0Lexer lexer = new pl0Lexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            pl0Parser parser = new pl0Parser(tokens);
            ParseTree tree = parser.program();

            pl0VisitorImpl visitor = new pl0VisitorImpl();
            visitor.visit(tree);

            visitor.printSymbolTable();
            visitor.printIntermediateCode();

            writeToFile(visitor.getIntermediateCode(), inputPath);

        } catch (IOException e) {
            System.err.println("An error occurred while processing the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeToFile(List<String> intermediateCode, Path inputPath) throws IOException {
        // 生成目标文件名，会输出到输入文件的同一级
        String outputFileName = "output_" + inputPath.getFileName();
        Path outputPath = inputPath.getParent().resolve(outputFileName);
        // 把中间代码写入文件
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            for (String code : intermediateCode) {
                writer.write(code);
                writer.newLine();
            }
        }
        // 输出的文件路径
        System.out.println("Intermediate code has been written to: " + outputPath.toAbsolutePath());
    }
}