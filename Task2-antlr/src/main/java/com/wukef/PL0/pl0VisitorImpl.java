package com.wukef.PL0;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class pl0VisitorImpl extends pl0BaseVisitor<String> {
    private int tempVarCount = 0;
    private int labelCount = 100; // 假设基地址为 100
    private List<String> intermediateCode = new ArrayList<>();
    private String newLabel() {
        return "L" + labelCount++;
    }
    private String newTempVar() {
        return "T" + tempVarCount++;
    }
    public List<String> getIntermediateCode() {
        return intermediateCode;
    }
    /**
     * <程序首部> → PROGRAM <标识符>
     */
    @Override
    public String visitProgramHeader(pl0Parser.ProgramHeaderContext ctx) {
        String programName = ctx.ident().getText();
        System.out.println("PROGRAM " + programName);
        return visitChildren(ctx);
    }
    /**
     * <变量说明> → VAR <标识符>{,<标识符>};
     */
    @Override
    public String visitVarDeclaration(pl0Parser.VarDeclarationContext ctx) {
        List<pl0Parser.IdentContext> variables = ctx.ident();
        String joinedVariableNames = variables.stream()
                .map(identContext -> identContext.getText()) // 获取每个变量的文本
                .collect(Collectors.joining(", ")); // 使用逗号和空格来连接
        System.out.println("VAR " + joinedVariableNames + ";");
        return null;
    }
    /**
     * <复合语句> → BEGIN <语句>{; <语句>} END
     */
    @Override
    public String visitCompoundStatement(pl0Parser.CompoundStatementContext ctx) {
        System.out.println("BEGIN");
        visitChildren(ctx);
        System.out.println("END");
        return null;
    }
    /**
     * <赋值语句> → <标识符>:=<表达式>
     * TODO 要去查符号表，还需要处理语义错误
     */
    @Override
    public String visitAssignmentStatement(pl0Parser.AssignmentStatementContext ctx) {
        String ident = ctx.ident().getText();
        String expr = visit(ctx.expression());
        intermediateCode.add(ident + " := " + expr);
        return null;
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
                intermediateCode.add(result + " := -" + firstTerm);
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
        intermediateCode.add(tempVar + " := " + accumulatedResult + " " + operator + " " + currentTerm);

        return tempVar;
    }
    /**
     * <项> → <因子> | <项> <乘法运算符> <因子>
     * 与表达式的处理方式类似
     */
    @Override
    public String visitTerm(pl0Parser.TermContext ctx) {
        String result = null;
        String tempVar = null;

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
                intermediateCode.add(tempVar + " := " + result + " " + operator + " " + nextTerm);
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
            // 处理标识符
            return visit(ctx.ident());
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
        // 这里暂时返回变量名
        return ctx.getText();
    }
}