// Generated from D:/Code/javaProject/pl0-compiler-implementation/Task2-ANTLR/src/main/antlr4/pl0.g4 by ANTLR 4.13.1
package com.wukef.PL0;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link pl0Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface pl0Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link pl0Parser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(pl0Parser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#programHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgramHeader(pl0Parser.ProgramHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(pl0Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#constDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstDeclaration(pl0Parser.ConstDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#constDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstDefinition(pl0Parser.ConstDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(pl0Parser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(pl0Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(pl0Parser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#conditionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionStatement(pl0Parser.ConditionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(pl0Parser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(pl0Parser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#emptyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement(pl0Parser.EmptyStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(pl0Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(pl0Parser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(pl0Parser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#addOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddOp(pl0Parser.AddOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#multOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultOp(pl0Parser.MultOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(pl0Parser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#relationOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationOp(pl0Parser.RelationOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(pl0Parser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link pl0Parser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(pl0Parser.NumberContext ctx);
}