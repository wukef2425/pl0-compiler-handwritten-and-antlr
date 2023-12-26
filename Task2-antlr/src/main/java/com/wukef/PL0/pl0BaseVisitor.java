// Generated from D:/Code/javaProject/pl0-compiler-implementation/Task2-ANTLR/src/main/antlr4/pl0.g4 by ANTLR 4.13.1
package com.wukef.PL0;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link pl0Visitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class pl0BaseVisitor<T> extends AbstractParseTreeVisitor<T> implements pl0Visitor<T> {
	/**
	 * <程序> → <程序首部> <分程序>
	 */
	@Override public T visitProgram(pl0Parser.ProgramContext ctx) { return visitChildren(ctx); }
	/**
	 * <程序首部> → PROGRAM <标识符>
	 */
	@Override public T visitProgramHeader(pl0Parser.ProgramHeaderContext ctx) { return visitChildren(ctx); }
	/**
	 * <分程序> → [<常量说明>][<变量说明>]<语句>
	 */
	@Override public T visitBlock(pl0Parser.BlockContext ctx) { return visitChildren(ctx); }
	/**
	 * <常量说明> → CONST <常量定义>{,<常量定义>};
	 */
	@Override public T visitConstDeclaration(pl0Parser.ConstDeclarationContext ctx) { return visitChildren(ctx); }
	/**
	 * <常量定义> → <标识符>:=<无符号整数>
	 */
	@Override public T visitConstDefinition(pl0Parser.ConstDefinitionContext ctx) { return visitChildren(ctx); }
	/**
	 * <变量说明> → VAR <标识符>{,<标识符>};
	 */
	@Override public T visitVarDeclaration(pl0Parser.VarDeclarationContext ctx) { return visitChildren(ctx); }
	/**
	 * <语句> → <赋值语句> | <条件语句> | <循环语句> | <复合语句> | <空语句>
	 */
	@Override public T visitStatement(pl0Parser.StatementContext ctx) { return visitChildren(ctx); }
	/**
	 * <赋值语句> → <标识符>:=<表达式>
	 */
	@Override public T visitAssignmentStatement(pl0Parser.AssignmentStatementContext ctx) { return visitChildren(ctx); }
	/**
	 * <条件语句> → IF <条件> THEN <语句>
	 */
	@Override public T visitConditionStatement(pl0Parser.ConditionStatementContext ctx) { return visitChildren(ctx); }
	/**
	 * <循环语句> → WHILE <条件> DO <语句>
	 */
	@Override public T visitLoopStatement(pl0Parser.LoopStatementContext ctx) { return visitChildren(ctx); }
	/**
	 * <复合语句> → BEGIN <语句>{; <语句>} END
	 */
	@Override public T visitCompoundStatement(pl0Parser.CompoundStatementContext ctx) { return visitChildren(ctx); }
	/**
	 * <空语句> → nothing
	 */
	@Override public T visitEmptyStatement(pl0Parser.EmptyStatementContext ctx) { return visitChildren(ctx); }
	/**
	 * <表达式> → [+|-]项 | <表达式> <加法运算符> <项>
	 */
	@Override public T visitExpression(pl0Parser.ExpressionContext ctx) { return visitChildren(ctx); }
	/**
	 * <项> → <因子> | <项> <乘法运算符> <因子>
	 */
	@Override public T visitTerm(pl0Parser.TermContext ctx) { return visitChildren(ctx); }
	/**
	 * <因子> → <标识符> | <无符号整数> | (<表达式>)
	 */
	@Override public T visitFactor(pl0Parser.FactorContext ctx) { return visitChildren(ctx); }
	/**
	 * <加法运算符> → + | -
	 */
	@Override public T visitAddOp(pl0Parser.AddOpContext ctx) { return visitChildren(ctx); }
	/**
	 * <乘法运算符> → * | /
	 */
	@Override public T visitMultOp(pl0Parser.MultOpContext ctx) { return visitChildren(ctx); }
	/**
	 * <条件> → <表达式> <关系运算符> <表达式>
	 */
	@Override public T visitCondition(pl0Parser.ConditionContext ctx) { return visitChildren(ctx); }
	/**
	 * <关系运算符> → = | <> | < | <= | > | >=
	 */
	@Override public T visitRelationOp(pl0Parser.RelationOpContext ctx) { return visitChildren(ctx); }
	/**
	 * <标识符> → <字母>{ <字母> | <数字> }
	 * <字母> → a | b | ... | z
	 */
	@Override public T visitIdent(pl0Parser.IdentContext ctx) { return visitChildren(ctx); }
	/**
	 * <无符号整数> → <数字> {<数字>}
	 * <数字> → 0 | 1 | ... | 8 | 9
	 */
	@Override public T visitNumber(pl0Parser.NumberContext ctx) { return visitChildren(ctx); }
}