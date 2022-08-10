// generated with ast extension for cup
// version 0.8
// 24/0/2022 20:48:3


package rs.ac.bg.etf.pp1.ast;

public class RelopEqualTo extends Relop {

    public RelopEqualTo () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelopEqualTo(\n");

        buffer.append(tab);
        buffer.append(") [RelopEqualTo]");
        return buffer.toString();
    }
}
