// generated with ast extension for cup
// version 0.8
// 24/0/2022 20:48:3


package rs.ac.bg.etf.pp1.ast;

public class Factor implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private Minus Minus;
    private FactorSub FactorSub;

    public Factor (Minus Minus, FactorSub FactorSub) {
        this.Minus=Minus;
        if(Minus!=null) Minus.setParent(this);
        this.FactorSub=FactorSub;
        if(FactorSub!=null) FactorSub.setParent(this);
    }

    public Minus getMinus() {
        return Minus;
    }

    public void setMinus(Minus Minus) {
        this.Minus=Minus;
    }

    public FactorSub getFactorSub() {
        return FactorSub;
    }

    public void setFactorSub(FactorSub FactorSub) {
        this.FactorSub=FactorSub;
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
        if(Minus!=null) Minus.accept(visitor);
        if(FactorSub!=null) FactorSub.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Minus!=null) Minus.traverseTopDown(visitor);
        if(FactorSub!=null) FactorSub.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Minus!=null) Minus.traverseBottomUp(visitor);
        if(FactorSub!=null) FactorSub.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor(\n");

        if(Minus!=null)
            buffer.append(Minus.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorSub!=null)
            buffer.append(FactorSub.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor]");
        return buffer.toString();
    }
}
