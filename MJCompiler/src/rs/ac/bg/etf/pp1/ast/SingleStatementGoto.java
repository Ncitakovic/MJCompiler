// generated with ast extension for cup
// version 0.8
// 24/0/2022 20:48:3


package rs.ac.bg.etf.pp1.ast;

public class SingleStatementGoto extends SingleStatement {

    private LabelGoto LabelGoto;

    public SingleStatementGoto (LabelGoto LabelGoto) {
        this.LabelGoto=LabelGoto;
        if(LabelGoto!=null) LabelGoto.setParent(this);
    }

    public LabelGoto getLabelGoto() {
        return LabelGoto;
    }

    public void setLabelGoto(LabelGoto LabelGoto) {
        this.LabelGoto=LabelGoto;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LabelGoto!=null) LabelGoto.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LabelGoto!=null) LabelGoto.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LabelGoto!=null) LabelGoto.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatementGoto(\n");

        if(LabelGoto!=null)
            buffer.append(LabelGoto.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatementGoto]");
        return buffer.toString();
    }
}
