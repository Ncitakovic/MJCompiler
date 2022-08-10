// generated with ast extension for cup
// version 0.8
// 24/0/2022 20:48:3


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(Relop Relop);
    public void visit(MulopFactorList MulopFactorList);
    public void visit(FactorSub FactorSub);
    public void visit(StatementList StatementList);
    public void visit(Addop Addop);
    public void visit(CondTerm CondTerm);
    public void visit(Designator Designator);
    public void visit(Condition Condition);
    public void visit(ConVarDeclList ConVarDeclList);
    public void visit(ConstDeclMore ConstDeclMore);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Const Const);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(VarDeclMore VarDeclMore);
    public void visit(VarDeclListMore VarDeclListMore);
    public void visit(CondFact CondFact);
    public void visit(Minus Minus);
    public void visit(SingleStatement SingleStatement);
    public void visit(AddopTermList AddopTermList);
    public void visit(MulopMod MulopMod);
    public void visit(MulopDiv MulopDiv);
    public void visit(MulopMul MulopMul);
    public void visit(AddopMinus AddopMinus);
    public void visit(AddopPlus AddopPlus);
    public void visit(RelopLessThanOrEqual RelopLessThanOrEqual);
    public void visit(RelopLessThan RelopLessThan);
    public void visit(RelopGreaterThanOrEqual RelopGreaterThanOrEqual);
    public void visit(RelopGreaterThan RelopGreaterThan);
    public void visit(RelopNotEqualTo RelopNotEqualTo);
    public void visit(RelopEqualTo RelopEqualTo);
    public void visit(Assignop Assignop);
    public void visit(Label Label);
    public void visit(DesignatorArrayName DesignatorArrayName);
    public void visit(DesignatorIdentSquare DesignatorIdentSquare);
    public void visit(DesignatorIdent DesignatorIdent);
    public void visit(FactorParensExpr FactorParensExpr);
    public void visit(FactorNewTypeSquare FactorNewTypeSquare);
    public void visit(FactorBoolConst FactorBoolConst);
    public void visit(FactorCharConst FactorCharConst);
    public void visit(FactorNumConst FactorNumConst);
    public void visit(FactorDesignator FactorDesignator);
    public void visit(MinusNo MinusNo);
    public void visit(MinusYes MinusYes);
    public void visit(Factor Factor);
    public void visit(MulopFactorListFirst MulopFactorListFirst);
    public void visit(MulopFactorListMore MulopFactorListMore);
    public void visit(Term Term);
    public void visit(AddopTermListFirst AddopTermListFirst);
    public void visit(AddopTermListMore AddopTermListMore);
    public void visit(Expr Expr);
    public void visit(CondFactExprRelop CondFactExprRelop);
    public void visit(CondFactExpr CondFactExpr);
    public void visit(CondTermCondFact CondTermCondFact);
    public void visit(CondTermLogAnd CondTermLogAnd);
    public void visit(ConditionCondTerm ConditionCondTerm);
    public void visit(ConditionLogOr ConditionLogOr);
    public void visit(DesignatorStatementDec DesignatorStatementDec);
    public void visit(DesignatorStatementInc DesignatorStatementInc);
    public void visit(DesignatorStatementAssign DesignatorStatementAssign);
    public void visit(Statements Statements);
    public void visit(SingleStatementGoto SingleStatementGoto);
    public void visit(SingleStatementPrintNumConst SingleStatementPrintNumConst);
    public void visit(SingleStatementPrint SingleStatementPrint);
    public void visit(SingleStatementRead SingleStatementRead);
    public void visit(SingleStatementRet SingleStatementRet);
    public void visit(SingleStatementDes SingleStatementDes);
    public void visit(LabelGoto LabelGoto);
    public void visit(StatementStatements StatementStatements);
    public void visit(StatementSingleStatementLabel StatementSingleStatementLabel);
    public void visit(StatementSingleStatement StatementSingleStatement);
    public void visit(StatementListEpsilon StatementListEpsilon);
    public void visit(StatementListRecursion StatementListRecursion);
    public void visit(VarDeclListEpsilon VarDeclListEpsilon);
    public void visit(VarDeclListMoreRec VarDeclListMoreRec);
    public void visit(Type Type);
    public void visit(MethodName MethodName);
    public void visit(MethodDecl MethodDecl);
    public void visit(VarDeclMoreEpsilon VarDeclMoreEpsilon);
    public void visit(VarDeclMoreComma VarDeclMoreComma);
    public void visit(VarDeclIdentSquare VarDeclIdentSquare);
    public void visit(VarDeclIdent VarDeclIdent);
    public void visit(VarDeclList VarDeclList);
    public void visit(ConstDeclMoreEpsilon ConstDeclMoreEpsilon);
    public void visit(ConstDeclMoreComma ConstDeclMoreComma);
    public void visit(CharConst CharConst);
    public void visit(BoolConst BoolConst);
    public void visit(NumConst NumConst);
    public void visit(ConstDecl ConstDecl);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(ConVarDeclListEpsilon ConVarDeclListEpsilon);
    public void visit(ConVarDeclListVar ConVarDeclListVar);
    public void visit(ConVarDeclListCon ConVarDeclListCon);
    public void visit(ProgramName ProgramName);
    public void visit(Program Program);

}
