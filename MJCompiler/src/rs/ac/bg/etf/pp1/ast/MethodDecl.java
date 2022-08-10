// generated with ast extension for cup
// version 0.8
// 24/0/2022 20:48:3


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodName MethodName;
    private VarDeclListMore VarDeclListMore;
    private Statements Statements;

    public MethodDecl (MethodName MethodName, VarDeclListMore VarDeclListMore, Statements Statements) {
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.VarDeclListMore=VarDeclListMore;
        if(VarDeclListMore!=null) VarDeclListMore.setParent(this);
        this.Statements=Statements;
        if(Statements!=null) Statements.setParent(this);
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public VarDeclListMore getVarDeclListMore() {
        return VarDeclListMore;
    }

    public void setVarDeclListMore(VarDeclListMore VarDeclListMore) {
        this.VarDeclListMore=VarDeclListMore;
    }

    public Statements getStatements() {
        return Statements;
    }

    public void setStatements(Statements Statements) {
        this.Statements=Statements;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodName!=null) MethodName.accept(visitor);
        if(VarDeclListMore!=null) VarDeclListMore.accept(visitor);
        if(Statements!=null) Statements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(VarDeclListMore!=null) VarDeclListMore.traverseTopDown(visitor);
        if(Statements!=null) Statements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(VarDeclListMore!=null) VarDeclListMore.traverseBottomUp(visitor);
        if(Statements!=null) Statements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclListMore!=null)
            buffer.append(VarDeclListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statements!=null)
            buffer.append(Statements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
