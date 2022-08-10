// generated with ast extension for cup
// version 0.8
// 24/0/2022 20:48:3


package rs.ac.bg.etf.pp1.ast;

public class ConVarDeclListCon extends ConVarDeclList {

    private ConVarDeclList ConVarDeclList;
    private ConstDeclList ConstDeclList;

    public ConVarDeclListCon (ConVarDeclList ConVarDeclList, ConstDeclList ConstDeclList) {
        this.ConVarDeclList=ConVarDeclList;
        if(ConVarDeclList!=null) ConVarDeclList.setParent(this);
        this.ConstDeclList=ConstDeclList;
        if(ConstDeclList!=null) ConstDeclList.setParent(this);
    }

    public ConVarDeclList getConVarDeclList() {
        return ConVarDeclList;
    }

    public void setConVarDeclList(ConVarDeclList ConVarDeclList) {
        this.ConVarDeclList=ConVarDeclList;
    }

    public ConstDeclList getConstDeclList() {
        return ConstDeclList;
    }

    public void setConstDeclList(ConstDeclList ConstDeclList) {
        this.ConstDeclList=ConstDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConVarDeclList!=null) ConVarDeclList.accept(visitor);
        if(ConstDeclList!=null) ConstDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConVarDeclList!=null) ConVarDeclList.traverseTopDown(visitor);
        if(ConstDeclList!=null) ConstDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConVarDeclList!=null) ConVarDeclList.traverseBottomUp(visitor);
        if(ConstDeclList!=null) ConstDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConVarDeclListCon(\n");

        if(ConVarDeclList!=null)
            buffer.append(ConVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclList!=null)
            buffer.append(ConstDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConVarDeclListCon]");
        return buffer.toString();
    }
}
