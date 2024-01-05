package com.wukef.PL0;

import org.antlr.v4.runtime.RuleContext;

import java.util.List;
import java.util.stream.Collectors;

public class pl0VisitorImpl extends pl0BaseVisitor<String> {
    private int tempVarCount = 1;
    private int currentCodeLine = 100; // 基地址为 100
    private final String PLACEHOLDER = "???"; // 在生成跳转指令时使用占位符
    private SymbolTable symbolTable = new SymbolTable();
    private MyArrayList<String> intermediateCode = new MyArrayList<>();
    private String newTempVar() {
        return "T" + tempVarCount++;
    }
    private void emit(String code) {
        intermediateCode.add(code);
        currentCodeLine++;
    }
    public void printIntermediateCode() {
        intermediateCode.forEach(code -> {
            System.out.println(intermediateCode.indexOf(code) + ":  " + code);
        });
    }

    public void printSymbolTable() {
        symbolTable.printSymbolTable();
        symbolTable.checkUsage();
    }

    public MyArrayList<String> getIntermediateCode() {
        return intermediateCode;
    }
    /**
     * <程序首部> → PROGRAM <标识符>
     */
    @Override
    public String visitProgramHeader(pl0Parser.ProgramHeaderContext ctx) {
        String programName = ctx.ident().getText();
        System.out.println("PROGRAM " + programName);
        visitChildren(ctx);
        return null;
    }
    /**
     * <常量说明> → CONST <常量定义>{,<常量定义>};
     */
    @Override
    public String visitConstDeclaration(pl0Parser.ConstDeclarationContext ctx) {
        List<pl0Parser.ConstDefinitionContext> constDefs = ctx.constDefinition();
        // 拼接
        String joinedConstDefs = constDefs.stream()
        // 从RuleContext（即ConstDefinitionContext）中获取其文本表示，
        // 对应于单个常量定义的原始字符串
                .map(RuleContext::getText)
        // 使用 collect 操作来终结流，将流中的元素累积成一个最终的结果
        // Collectors.joining(", ")是一个收集器，它会在处理流的过程中
        // 将元素之间添加一个逗号和一个空格作为分隔符，最终拼接成一个字符串
                .collect(Collectors.joining(", "));
        System.out.println("CONST " + joinedConstDefs + ";");

        for (pl0Parser.ConstDefinitionContext constDef : constDefs) {
            String constName = constDef.ident().getText();
            String constValue = constDef.number().getText();
            int line = constDef.getStart().getLine();

            try {
                symbolTable.declare(constName, false, line);
                emit(constName + " := " + constValue);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    /**
     * <变量说明> → VAR <标识符>{,<标识符>};
     */
    @Override
    public String visitVarDeclaration(pl0Parser.VarDeclarationContext ctx) {
        List<pl0Parser.IdentContext> variables = ctx.ident();
        // 检查能不能声明
        for (pl0Parser.IdentContext variable : variables) {
            String varName = variable.getText();
            int line = variable.getStart().getLine();
            try {
                symbolTable.declare(varName, true, line);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        // 拼接
        String joinedVariableNames = variables.stream()
                .map(RuleContext::getText) // 获取每个变量的文本
                .collect(Collectors.joining(", ")); // 使用逗号和空格来连接
        System.out.println("VAR " + joinedVariableNames + ";");
        return null;
    }
    /**
     * <赋值语句> → <标识符>:=<表达式>
     */
    @Override
    public String visitAssignmentStatement(pl0Parser.AssignmentStatementContext ctx) {
        String ident = ctx.ident().getText();
        String expr = visit(ctx.expression());
        int line = ctx.getStart().getLine();
        try {
            symbolTable.assign(ident, line); // 标记为已赋值
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        emit(ident + " := " + expr);
        return null;
    }
    /**
     * <循环语句> → WHILE <条件> DO <语句>
     */
    @Override
    public String visitLoopStatement(pl0Parser.LoopStatementContext ctx) {
        // 记录循环条件开始的地址
        int whileCondStart = currentCodeLine;
        // 访问条件，并生成条件计算的中间代码
        String condition = visit(ctx.condition());
        // 记录跳转到循环体的地址的占位符位置
        int gotoDoStartIndex = currentCodeLine;
        emit("IF " + condition + " GOTO " + PLACEHOLDER);
        // 记录跳出循环的跳转指令的占位符位置
        int gotoAfterWhileIndex = currentCodeLine;
        emit("GOTO " + PLACEHOLDER);
        // 访问循环体之前，保存循环体开始的地址
        int doStart = currentCodeLine;
        // 访问循环体
        visit(ctx.statement());
        // 循环体访问完成后，设置结束循环条件后的地址
        // 替换占位符为正确的跳转地址
        intermediateCode.set(gotoDoStartIndex, "IF " + condition + " GOTO " + doStart);
        // 循环体结束后跳转回循环条件判断
        emit("GOTO " + whileCondStart);
        // emit("GOTO " + whileCondStart);做完了才是循环西真正结束了
        int afterWhile = currentCodeLine;
        intermediateCode.set(gotoAfterWhileIndex, "GOTO " + afterWhile);
        return null;
    }
    /**
     * <条件语句> → IF <条件> THEN <语句>
     * 需要记录 THEN 语句的开始和结束位置来实现跳转
     */
    @Override
    public String visitConditionStatement(pl0Parser.ConditionStatementContext ctx) {
        // 获取条件代码
        String condition = visit(ctx.condition());
        // 生成跳转到 THEN 的占位符代码
        int thenIndex = currentCodeLine;
        emit("IF " + condition + " GOTO " + PLACEHOLDER);
        // 生成跳转到 afterIf 的占位符代码
        int afterIfIndex = currentCodeLine;
        emit("GOTO " + PLACEHOLDER);
        // THEN 语句块的起始地址
        int thenStart = currentCodeLine;
        // 访问 THEN 语句块,生成代码
        visit(ctx.statement());
        // 填充跳转到 THEN 语句块的占位符（使用 thenStart）
        intermediateCode.set(thenIndex, "IF " + condition + " GOTO " + thenStart);
        // afterIf 的实际地址为当前代码行
        int afterIf = currentCodeLine;
        // 填充跳转到 afterIf 的占位符（使用 afterIf）
        intermediateCode.set(afterIfIndex, "GOTO " + afterIf);
        return null;
    }
    /**
     * <条件> → <表达式> <关系运算符> <表达式>
     */
    @Override
    public String visitCondition(pl0Parser.ConditionContext ctx) {
        String condition;
        // 得到左边的表达式存的位置
        String lexpr = visit(ctx.expression(0));
        // 得到右边的表达式存的位置
        String rexpr = visit(ctx.expression(1));
        // 获取操作符
        String op = ctx.relationOp().getText();
        condition = lexpr + " " + op + " " + rexpr;
        return condition;
    }
    /**
     * <表达式> → [+|-]项 | <表达式> <加法运算符> <项>
     * 可以是一个（正负）项，或者一个（正负）项后面跟一堆<加法运算符> <项>
     * 也就是 <表达式> → [<加法运算符>] <项> | <表达式> <加法运算符> <项>
     */
    @Override
    public String visitExpression(pl0Parser.ExpressionContext ctx) {
        String result = null;
        // 前缀正负号个数 0 或 1
        // termIndex - 1 + prefixNum 为 term 对应的加法运算符
        Integer prefixNum;

        // 没有正负号前缀的情况，项一定比符多一个，比如 1 + 2 - 3
        if ( ctx.term().size() == ctx.addOp().size() + 1) {
            prefixNum = 0;
            // 表达式不以正号或负号开始
            result = visit(ctx.term(0));
        } else {
            prefixNum = 1;
            // 表达式以正负号开始
            String firstOp = ctx.addOp(0).getText();
            String firstTerm = visit(ctx.term(0));

            if (firstOp.equals("-")) {
                result = newTempVar();
                emit(result + " := -" + firstTerm);
            } else {
                result = firstTerm;
            }

        }

        for (int i = 1; i < ctx.term().size(); i++) {
            result = processTerm(ctx, result, i, prefixNum);
        }

        return result;
    }
    private String processTerm(pl0Parser.ExpressionContext ctx, String accumulatedResult, int termIndex, int prefixNum) {
        // 获取当前的操作符，连接上一个结果和当前项
        String operator = ctx.addOp(termIndex - 1 + prefixNum).getText();
        // 访问当前项
        String currentTerm = visit(ctx.term(termIndex));
        // 生成一个新的临时变量来存放这次操作的结果
        String tempVar = newTempVar();
        // 确保操作符正确地应用到累积结果和当前项
        emit(tempVar + " := " + accumulatedResult + " " + operator + " " + currentTerm);

        return tempVar;
    }
    /**
     * <项> → <因子> | <项> <乘法运算符> <因子>
     * 与表达式的处理方式类似
     */
    @Override
    public String visitTerm(pl0Parser.TermContext ctx) {
        String result;
        String tempVar;

        // 如果只有一个因子
        if (ctx.factor().size() == 1) {
            tempVar = visit(ctx.factor(0));
            result = tempVar;
        } else {
            // 处理项和因子之间的递归关系
            // 先获取第一个因子
            result = visit(ctx.factor(0));
            // 递归地处理剩余的乘法运算符和因子
            for (int i = 1; i < ctx.factor().size(); i++) {
                String operator = ctx.multOp(i - 1).getText(); // 获取操作符
                String nextTerm = visit(ctx.factor(i)); // 获取下一个因子的临时变量
                tempVar = newTempVar(); // 生成新的临时变量用于存储结果
                emit(tempVar + " := " + result + " " + operator + " " + nextTerm);
                result = tempVar; // 更新结果变量
            }
        }
        return result;
    }
    /**
     * <因子> → <标识符> | <无符号整数> | (<表达式>)
     */
    @Override
    public String visitFactor(pl0Parser.FactorContext ctx) {
        if (ctx.ident() != null) {
            String ident = visit(ctx.ident());
            int line = ctx.getStart().getLine();
            try {
                symbolTable.use(ident, line); // 标记为已使用
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return ident;
        } else if (ctx.number() != null) {
            // 处理无符号整数
            return visit(ctx.number());
        } else if (ctx.expression() != null) {
            // 处理括号内的表达式
            return visit(ctx.expression());
        }
        return null;
    }
    /**
     * <无符号整数> → <数字> {<数字>}
     * <数字> → 0 | 1 | ... | 8 | 9
     * 会被别人用到，就需要返回 ctx.getText() 来进行记录
     * 否则会出现 null + null 的情况
     */
    @Override
    public String visitNumber(pl0Parser.NumberContext ctx) {
        // 返回无符号整数
        return ctx.getText();
    }
    /**
     * <标识符> → <字母>{ <字母> | <数字> }
     * <字母> → a | b | ... | z
     */
    @Override
    public String visitIdent(pl0Parser.IdentContext ctx) {
        // 这里返回变量名
        return ctx.getText();
    }
}