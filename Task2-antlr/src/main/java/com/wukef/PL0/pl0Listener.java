// Generated from D:/Code/javaProject/pl0-compiler-implementation/Task2-ANTLR/src/main/antlr4/pl0.g4 by ANTLR 4.13.1
package com.wukef.PL0;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link pl0Parser}.
 */
public interface pl0Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link pl0Parser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(pl0Parser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(pl0Parser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#programHeader}.
	 * @param ctx the parse tree
	 */
	void enterProgramHeader(pl0Parser.ProgramHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#programHeader}.
	 * @param ctx the parse tree
	 */
	void exitProgramHeader(pl0Parser.ProgramHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(pl0Parser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(pl0Parser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#constDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstDeclaration(pl0Parser.ConstDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#constDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstDeclaration(pl0Parser.ConstDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#constDefinition}.
	 * @param ctx the parse tree
	 */
	void enterConstDefinition(pl0Parser.ConstDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#constDefinition}.
	 * @param ctx the parse tree
	 */
	void exitConstDefinition(pl0Parser.ConstDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(pl0Parser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(pl0Parser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(pl0Parser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(pl0Parser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(pl0Parser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(pl0Parser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#conditionStatement}.
	 * @param ctx the parse tree
	 */
	void enterConditionStatement(pl0Parser.ConditionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#conditionStatement}.
	 * @param ctx the parse tree
	 */
	void exitConditionStatement(pl0Parser.ConditionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(pl0Parser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(pl0Parser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(pl0Parser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(pl0Parser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(pl0Parser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(pl0Parser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(pl0Parser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(pl0Parser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(pl0Parser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(pl0Parser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(pl0Parser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(pl0Parser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#addOp}.
	 * @param ctx the parse tree
	 */
	void enterAddOp(pl0Parser.AddOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#addOp}.
	 * @param ctx the parse tree
	 */
	void exitAddOp(pl0Parser.AddOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#multOp}.
	 * @param ctx the parse tree
	 */
	void enterMultOp(pl0Parser.MultOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#multOp}.
	 * @param ctx the parse tree
	 */
	void exitMultOp(pl0Parser.MultOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(pl0Parser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(pl0Parser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#relationOp}.
	 * @param ctx the parse tree
	 */
	void enterRelationOp(pl0Parser.RelationOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#relationOp}.
	 * @param ctx the parse tree
	 */
	void exitRelationOp(pl0Parser.RelationOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#ident}.
	 * @param ctx the parse tree
	 */
	void enterIdent(pl0Parser.IdentContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#ident}.
	 * @param ctx the parse tree
	 */
	void exitIdent(pl0Parser.IdentContext ctx);
	/**
	 * Enter a parse tree produced by {@link pl0Parser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(pl0Parser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link pl0Parser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(pl0Parser.NumberContext ctx);
}