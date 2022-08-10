package rs.ac.bg.etf.pp1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	
	public int getMainPc(){
		return mainPc;
	}
	
	@Override
	public void visit(MethodName methodName) {
		mainPc = Code.pc;
		methodName.obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());
		Code.put(methodName.obj.getLocalSymbols().size());		
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(FactorNumConst factorNumConst) {
		Code.loadConst(factorNumConst.getN1());
	}
	
	@Override
	public void visit(FactorCharConst factorCharConst) {
		Code.loadConst(factorCharConst.getC1());
	}
	@Override
	public void visit(FactorBoolConst factorBoolConst) {
		Code.loadConst(factorBoolConst.getB1());
	}
	
	@Override
	public void visit(SingleStatementPrint singleStatementPrint) {		
		Code.loadConst(0);
		if(singleStatementPrint.getExpr().struct == Tab.charType)
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}
	
	@Override
	public void visit(SingleStatementPrintNumConst singleStatementPrintNumConst) {
		Code.loadConst(singleStatementPrintNumConst.getN2());
		if(singleStatementPrintNumConst.getExpr().struct == Tab.charType)
			Code.put(Code.bprint);
		else
			Code.put(Code.print);		
	}
	@Override
	public void visit(SingleStatementRead singleStatementRead) {
		if(singleStatementRead.getDesignator().obj.getType()==Tab.charType)
			Code.put(Code.bread);
		else
			Code.put(Code.read);
		Code.store(singleStatementRead.getDesignator().obj);
	}
	
	@Override
	public void visit(FactorDesignator factorDesignator) {
		Code.load(factorDesignator.getDesignator().obj);
	}
	
	@Override
	public void visit(FactorNewTypeSquare factorNewTypeSquare) {
		Code.put(Code.newarray);
		if(factorNewTypeSquare.getType().struct.equals(Tab.intType)) {
			Code.put(1);			
		}else {
			Code.put(0);
		}		
	}
	
	@Override
	public void visit(DesignatorStatementAssign designatorStatementAssign) {
		Code.store(designatorStatementAssign.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatementInc designatorStatementInc) {
		Code.load(designatorStatementInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatementInc.getDesignator().obj);
		//Code.put(Code.inc);
		//Code.load(designatorStatementInc.getDesignator().obj);
		//Code.put(1);				
	}
	@Override
	public void visit(DesignatorStatementDec designatorStatementDec) {
		Code.load(designatorStatementDec.getDesignator().obj);
		Code.loadConst(-1);
		Code.put(Code.add);
		Code.store(designatorStatementDec.getDesignator().obj);		
	}
	
	@Override
	public void visit(DesignatorArrayName designatorArrayName) {
		Code.load(designatorArrayName.obj);
	}
	
	@Override
	public void visit(AddopTermListMore addopTermListMore) {
		if(addopTermListMore.getAddop() instanceof AddopPlus)
			Code.put(Code.add);
		else
			Code.put(Code.sub);
	}
	@Override
	public void visit(MulopFactorListMore mulopFactorListMore) {
		if(mulopFactorListMore.getMulop() instanceof MulopMul)	
			Code.put(Code.mul);
		else if(mulopFactorListMore.getMulop() instanceof MulopDiv)
			Code.put(Code.div);
		else
			Code.put(Code.rem);
	}
	@Override
	public void visit(Factor factor) {
		if(factor.getMinus() instanceof MinusYes) {
			Code.put(Code.neg);
		}
	}
	//LABELS
	class Elem{
		String label;
		int patchAdr;
		Elem(String label,int patchAdr){
			this.label = label; this.patchAdr = patchAdr;
		}
	}
	
	Map<String, Integer> map = new HashMap<>();
	List<Elem> listOfAdrToFill = new ArrayList<>();
	
	@Override
	public void visit(Label label) {
		map.put(label.getI1(), Code.pc);
		for(Elem e: listOfAdrToFill) {
			if(e.label.equals(label.getI1())) {
				Code.fixup(e.patchAdr);
			}			
		}
	}
	
	@Override
	public void visit(LabelGoto labelGoto) {
		if(map.containsKey(labelGoto.getI1())) {
			int adr = map.get(labelGoto.getI1());
			Code.putJump(adr);
		}else {			
			Code.put(Code.jmp);			
			Elem e = new Elem(labelGoto.getI1(),Code.pc);
			Code.put(0);
			Code.put(0);
			listOfAdrToFill.add(e);
		}
	}
	
	/*
    
	@Override
    public void visit(FactorArrayHash factorArrayHash){
    	//modmax
    	Code.load(factorArrayHash.getDesignator().obj);
    	Code.put(Code.arraylength);
    	Code.loadConst(1);
    	Code.put(Code.sub);
    	Code.put(Code.dup);// n-1 n-1
    	Code.load(factorArrayHash.getDesignator().obj);// n-1 n-1 niz
    	Code.put(Code.dup_x1);// n-1 niz n-1 niz
    	Code.put(Code.pop);//n-1 niz n-1
    	Code.put(Code.aload);//n-1 max
    	
    	int whilePC = Code.pc;
    	//while
    	Code.put(Code.dup2);//n-1 max n-1 max
    	Code.put(Code.pop);//n-1 max n-1
    	Code.load(factorArrayHash.getDesignator().obj);
    	Code.put(Code.dup_x1);
    	Code.put(Code.pop);//n-1 max niz n-1 
    	
    	Code.put(Code.dup);//n-1 max niz n-1 n-1
    	Code.loadConst(0);//n-1 max niz n-1 n-1 0
    	Code.put(Code.jcc+Code.lt);//jmp if index<0
    	int adr1= Code.pc;
    	Code.put2(0);
    	//n-1 max niz n-1
    	Code.put(Code.aload);//n-1 max niz[n-1]
    	Code.put(Code.dup2);//n-1 max niz[n-1] max niz[n-1]
    	//if(niz[i]>max) max = niz[i];
    	Code.put(Code.jcc+Code.gt);
    	int adr2= Code.pc;
    	Code.put2(0);
    	//n-1 max niz[n-1]
    	//then
    	Code.put(Code.dup_x1);//n-1 niz[n-1] max niz[n-1]
    	Code.put(Code.pop);//n-1 niz[n-1] max
    	Code.put(Code.pop);//n-1 niz[n-1] 
    	
    	Code.putJump(0);
    	int adr3=Code.pc-2;
    	Code.fixup(adr2);
    	Code.put(Code.pop);//n-1 max
    	Code.fixup(adr3);
    	Code.put(Code.dup2);//n-1 niz[n-1] n-1 niz[n-1]
    	Code.put(Code.pop);//n-1 niz[n-1] n-1
    	Code.loadConst(1);
    	Code.put(Code.sub);//n-1 niz[n-1] n-2
    	Code.put(Code.dup_x2);//n-2 n-1 niz[n-1] n-2
    	Code.put(Code.pop);//n-2 n-1 niz[n-1]
    	Code.put(Code.dup_x1);//n-2 niz[n-1] n-1 niz[n-1]
    	Code.put(Code.pop);//n-2 niz[n-1] n-1 
    	Code.put(Code.pop);//n-2 niz[n-1] 
    	
    	Code.putJump(whilePC);
    	
    	Code.fixup(adr1);
    	//endOfLoop
    	Code.put(Code.pop);
    	Code.put(Code.pop);
    	Code.put(Code.dup_x1);
    	Code.put(Code.pop);
    	Code.put(Code.pop);
    }
	
	@Override
	public void visit(DesignatorIdentSquare designatorIdentSquare){
		//brojanjePristupaElemenataNiza
		Code.put(Code.dup2);//niz 2 niz 2
		Code.load(designatorIdentSquare.getDesignatorArrayName().obj);//niz 2 niz 2 niz
		Code.put(Code.arraylength);// niz 2 niz 2 length*2
		Code.loadConst(2);
		Code.put(Code.div);// niz 2 niz 2 length
		Code.put(Code.add);// niz 2 niz 2+length
		Code.put(Code.dup2);//niz 2 niz 2+length niz 2+length
		Code.put(Code.aload);//niz 2 niz 2+length niz[2+length]
		Code.loadConst(1); // niz 2 niz 2+length niz[2+length] 1
		Code.put(Code.add);// niz 2 niz 2+length niz[2+length]+1
		Code.put(Code.astore);//niz 2 						
	}
	@Override
	public void visit(DesignatorIdentSquareHash designatorIdentSquareHash){
		//BrojPristupaElementaNiza
		Code.load(designatorIdentSquareHash.getDesignatorArrayName().obj);//niz 2 niz 2 niz
		Code.put(Code.arraylength);// niz 2 length*2
		Code.loadConst(2);
		Code.put(Code.div);// niz 2 length
		Code.put(Code.add);// niz 2+length
	}
	
	
    @Override
    public void visit(FactorIdentSquareAt factorIdentSquareAt){
		//niz@br = niz[br]+niz[n-br-1]
    	Code.load(factorIdentSquareAt.getDesignator().obj);//niz
    	Code.loadConst(factorIdentSquareAt.getN2());//niz index
    	Code.put(Code.aload);//niz[index]
    	Code.load(factorIdentSquareAt.getDesignator().obj);//niz[index] niz
    	Code.load(factorIdentSquareAt.getDesignator().obj);//niz[index] niz niz
    	Code.put(Code.arraylength);//niz[index] niz length
    	Code.loadConst(2);
    	Code.put(Code.div);
    	Code.loadConst(factorIdentSquareAt.getN2());//niz[index] niz length N
    	Code.put(Code.sub);
    	Code.loadConst(1);////niz[index] niz length N 1
    	Code.put(Code.sub);
    	Code.put(Code.aload);
    	Code.put(Code.add);
    }
	@Override
    public void visit(DesignatorArrayCaretNumConst designatorArrayCaretNumConst){
		//mnozenjeElemNizaNumKonstDesnoOdSimbola^
    	Obj niz = designatorArrayCaretNumConst.getDesignator().obj;
    	int num = designatorArrayCaretNumConst.getN2();
    	
    	int adr;
    	Code.loadConst(0);
    	
    	adr = Code.pc;
    	Code.put(Code.dup);
    	Code.load(niz);
    	Code.put(Code.arraylength);
    	    	
    	Code.put(Code.jcc+Code.eq);//if
    	int pc1=Code.pc;
    	Code.put2(0);
    	
    	Code.put(Code.dup);
    	Code.load(niz);
    	Code.put(Code.dup_x1);
    	Code.put(Code.pop);
    	Code.put(Code.dup2);
    	Code.put(Code.aload);
    	Code.loadConst(num);
    	Code.put(Code.mul);
    	Code.put(Code.astore);
    	
    	Code.loadConst(1);
    	Code.put(Code.add);
    	
    	Code.putJump(adr);
    	
    	Code.fixup(pc1);
    	
    	Code.put(Code.pop);
    }
	
	@Override
	public void visit(SingleStatementPrintArrayNumConst singleStatementPrintArrayNumConst) {
		//ispis niza zadatog indeksa print(niz,index);
		Obj obj = singleStatementPrintArrayNumConst.getDesignator().obj;
		if(obj.getType().getKind() == Struct.Array) {
			Code.load(obj);
			int e = singleStatementPrintArrayNumConst.getN2();
			Code.loadConst(e);
			Code.put(Code.aload);			
			Code.loadConst(0);
			Code.put(Code.print);
						
		}
	}
	@Override
	public void visit(SingleStatementPrintArray singleStatementPrintArray) {
		//printSaZarezomIspisatiElemNizaSaIndexDesnoOdZareza
		Obj obj = singleStatementPrintArray.getDesignator().obj;
		if(obj.getType().getKind() == Struct.Array) {
			Code.load(obj);                 //niz
			Code.loadConst(0);				//niz 0
			int beginWhile = Code.pc;
			Code.put(Code.dup);				//niz 0 0
			Code.load(obj);					//niz 0 0 niz
			Code.put(Code.arraylength);		//niz 0 0 length
			Code.putFalseJump(Code.lt, 0);  
			int endWhile = Code.pc-2;		
			Code.put(Code.dup);				//niz 0 0
			Code.load(obj);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			Code.put(Code.aload);
			Code.loadConst(0);				//niz 0 0 0
			Code.put(Code.print);			//niz 0 
			Code.loadConst(1);				//niz 0 1
			Code.put(Code.add);				//niz 1
			Code.putJump(beginWhile);
			Code.fixup(endWhile);	
			Code.put(Code.pop);
			Code.put(Code.pop);
		}
		if(singleStatementPrintArray.getDesignator().obj.getName().equals("eol")) {
			Code.load(obj);
			Code.loadConst(0);
			Code.put(Code.print);
		}
	}
	//---
	private boolean isFinal;
	private String currentIdent;
	Set<String> finalArrs = new HashSet<>();

	@Override
	public void visit(FactorNewTypeSquare factorNewTypeSquare) {
		if(finalArrs.contains(currentIdent)){
			Code.loadConst(2);
			Code.put(Code.mul);	
		}
		Code.put(Code.newarray);
		if(factorNewTypeSquare.getType().struct.equals(Tab.intType)) {
			Code.put(1);			
		}else {
			Code.put(0);
		}		
	}
	
	@Override
	public void visit(DesignatorStatementAssign designatorStatementAssign) {
		if(designatorStatementAssign.getDesignator() instanceof DesignatorIdent)
			Code.store(designatorStatementAssign.getDesignator().obj);
		else{
//			isFinal=false;
			String nizIme = ((DesignatorIdentSquare)designatorStatementAssign.getDesignator()).getDesignatorArrayName().getI1();
			Obj niz = ((DesignatorIdentSquare)designatorStatementAssign.getDesignator()).getDesignatorArrayName().obj;
			
			if(finalArrs.contains(nizIme)){
				Code.put(Code.dup2);//niz 0 val 0 val
				Code.load(niz);//niz 0 val 0 val niz
				Code.put(Code.dup_x2);//niz 0 val niz 0 val niz
				Code.put(Code.pop);//niz 0 val niz 0 val 
				Code.put(Code.pop);//niz 0 val niz 0 
				Code.load(niz);
				Code.put(Code.arraylength);
				Code.loadConst(2);
				Code.put(Code.div);
				Code.put(Code.add);
				Code.put(Code.dup2);
				Code.put(Code.aload);
				Code.loadConst(0);
				Code.put(Code.jcc+Code.eq);
				int pc_ = Code.pc;
				Code.put2(0);
				Code.put(Code.trap);
				Code.put(1);
				
				Code.fixup(pc_);
				Code.loadConst(1);
				Code.put(Code.astore);
				Code.put(Code.astore);
				
			}else{
				Code.store(designatorStatementAssign.getDesignator().obj);
			}
		}
	}
	@Override
	public void visit(DesignatorIdent designatorIdent){
		currentIdent = designatorIdent.getI1();
	}
	@Override 
	public void visit(DesignatorIdentSquare designatorIdentSquare){
//		Code.put(Code.dup2); // adr ind adr ind
//		Code.put(Code.pop); // adr ind adr
//		Code.load(designatorIdentSquare.getDesignatorArrayName().obj);
//		Code.put(Code.arraylength); // adr ind length
//		Code.put(Code.dup2); // adr ind length ind length
//		Code.put(Code.pop); // adr ind length ind;
//		Code.put(Code.jcc+Code.lt); // adr ind -> ind < length
//		int jltpc = Code.pc;
//		Code.put2(0);
//		// adr ind
//		Code.put(Code.dup); // adr ind ind
//		Code.loadConst(0); // adr ind ind 0
//		Code.put(Code.jcc+Code.le);
//		int gtepc=Code.pc;
//		Code.put2(0);
//		Code.putJump(0);
//		int ok = Code.pc-2;
//		Code.fixup(jltpc);
//		Code.fixup(gtepc);
//		
//		Code.put(Code.trap);
//		Code.put(1); // trap arg
//		Code.fixup(ok);
	}
	
	@Override 
	public void visit(DesignatorHash designatorHash){
		//niz[i]#NumConst  ->  niz[i] += niz[(i+NumConst) % arraylength]
		DesignatorIdentSquare o = (DesignatorIdentSquare) designatorHash.getDesignator();
		Obj niz = o.getDesignatorArrayName().obj;
		int num_const = designatorHash.getN2();
    	Code.put(Code.dup2);//niz i niz i
    	Code.put(Code.dup2);//niz i niz i niz i 
    	Code.loadConst(num_const);//niz i niz i niz i NumConst
    	Code.put(Code.add);//niz i niz i niz i+NumConst
    	Code.load(niz);//niz i niz i niz i+NumConst niz
    	
    	Code.put(Code.arraylength);//niz i niz i niz i+NumConst length
    	Code.put(Code.rem);//niz i niz i niz (i+NumConst)%length
    	Code.put(Code.aload);//niz i niz i niz[(i+NumConst)%length]
    	Code.put(Code.dup_x2);//niz i niz[(i+NumConst)%length] niz i niz[(i+NumConst)%length]
    	Code.put(Code.pop);//niz i niz[(i+NumConst)%length] niz i 
    	Code.put(Code.aload);//niz i niz[(i+NumConst)%length] niz[i]
    	Code.put(Code.add);//niz i niz[i]+niz[(i+NumConst)%length]
    	Code.put(Code.astore);//niz[i] = niz[i] + niz[(i+NumConst)%length]
	}
	@Override 
	public void visit(FinalYes finalYes){
		isFinal=true;
	}
	@Override
	public void visit(VarDeclList varDeclList){
		isFinal = false;
	}
	@Override 
	public void visit(VarDeclIdentSquare varDeclIdentSquare){
		if(isFinal){
			finalArrs.add(varDeclIdentSquare.getI1());
		}
		
	}

	//---
	
	*/
	
}
