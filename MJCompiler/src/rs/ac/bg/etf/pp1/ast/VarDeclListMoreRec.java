// generated with ast extension for cup
// version 0.8
// 24/0/2022 20:48:3


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListMoreRec extends VarDeclListMore {

    private VarDeclListMore VarDeclListMore;
    private VarDeclList VarDeclList;

    public VarDeclListMoreRec (VarDeclListMore VarDeclListMore, VarDeclList VarDeclList) {
        this.VarDeclListMore=VarDeclListMore;
        if(VarDeclListMore!=null) VarDeclListMore.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public VarDeclListMore getVarDeclListMore() {
        return VarDeclListMore;
    }

    public void setVarDeclListMore(VarDeclListMore VarDeclListMore) {
        this.VarDeclListMore=VarDeclListMore;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclListMore!=null) VarDeclListMore.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclListMore!=null) VarDeclListMore.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclListMore!=null) VarDeclListMore.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListMoreRec(\n");

        if(VarDeclListMore!=null)
            buffer.append(VarDeclListMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListMoreRec]");
        return buffer.toString();
    }
}
