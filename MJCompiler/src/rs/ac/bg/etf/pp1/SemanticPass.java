package rs.ac.bg.etf.pp1;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {
	
	boolean errorDetected = false;
	int nVars;
	
	Logger log = Logger.getLogger(getClass());
	private Struct boolType;
	private Obj currentProgram;
	private Struct currentType;
	private int constant;
	private Struct constantType;
	private Obj currentMethod;
	private boolean mainHappend = false;
	
	public SemanticPass(Struct boolType) {
		this.boolType = boolType;
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}

	@Override
	public void visit(ProgramName programName) {
		currentProgram = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(currentProgram);
		Tab.closeScope();
		
		if(!mainHappend)
			report_error("Program nema MAIN metodu!", program);
		
		boolean contain = false;
		for(String s : labelGotoList) {
			contain = labelList.contains(s);
			if(!contain) {
				report_error("problem sa labelama",null);
			}
		}
			
		
	}
	
	@Override
	public void visit(ConstDecl constDecl) {
		Obj conObj = Tab.find(constDecl.getI1());
		//Tab.currentScope().findSymbol(imepromenljive) ovo kod varijabli
		// globalne i lokalne variable i metode (main)
		if(conObj.equals(Tab.noObj)) {
			if(constantType.assignableTo(currentType)) {
				conObj = Tab.insert(Obj.Con, constDecl.getI1(), currentType);
				conObj.setAdr(constant);
			}
			else {
				report_error("Neadekvatni tipovi pri definiciji konstante " + constDecl.getI1(), constDecl);
			}
		}
		else {
			report_error("Dupla definicija konstante " + constDecl.getI1(), constDecl);
		}
	}

	
	@Override
	public void visit(VarDeclIdent varDeclIdent) {
		Obj varObj = Tab.currentScope.findSymbol(varDeclIdent.getI1());
		
		if(varObj==null) {
			varObj = Tab.insert(Obj.Var, varDeclIdent.getI1(), currentType);
		}else {
			report_error("Dupla definicija promenljive u istom opsegu" + varDeclIdent.getI1() , varDeclIdent);
		}		
	}		
			
	@Override
	public void visit(VarDeclIdentSquare varDeclIdentSquare) {
		Obj varObj = Tab.currentScope.findSymbol(varDeclIdentSquare.getI1());
		if(varObj==null) {
			Struct arrType = new Struct(Struct.Array,currentType);
			varObj = Tab.insert(Obj.Var, varDeclIdentSquare.getI1(), arrType);
			
		}else{
			report_error("Dupla definicija niza u istom opsegu" + varDeclIdentSquare.getI1(), varDeclIdentSquare);
		}
	}
	
	
	@Override
	public void visit(NumConst numConst) {
		constant = numConst.getN1();
		constantType = Tab.intType;
	}
	
	@Override
	public void visit(BoolConst boolConst) {
		constant = boolConst.getB1();
		constantType = boolType;
	}
	
	@Override
	public void visit(CharConst charConst) {
		constant = charConst.getC1();
		constantType = Tab.charType;
	}
	
	@Override
	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getI1());
		if(typeObj.equals(Tab.noObj) || typeObj.getKind() != Obj.Type) {
			report_error("Pogresan tip (" + type.getI1() + ") pri definiciji konstante", type);
			type.struct = currentType = Tab.noType;
		}
		else {
			type.struct = currentType = typeObj.getType();
		}
	}
	
	@Override
	public void visit(MethodName methodName) {
		if(methodName.getI1().equalsIgnoreCase("main"))
			mainHappend = true;
		methodName.obj = currentMethod = Tab.insert(Obj.Meth, methodName.getI1(), Tab.noType);
		currentMethod.setLevel(0);
    	Tab.openScope();		
	}
	
	
    @Override
    public void visit(MethodDecl methodDecl) {
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	currentMethod = null;
    }
    
   
  
    
    //A4
    
    @Override
    public void visit(DesignatorArrayName designatorArrayName) {
    	Obj arrObj = Tab.find(designatorArrayName.getI1());
    	if(arrObj == Tab.noObj) {
    		report_error("promenljiva "+designatorArrayName.getI1()+" nije deklarisana! ", designatorArrayName);
    		designatorArrayName.obj = Tab.noObj;
    	}
    	else if(arrObj.getKind() != Obj.Var || arrObj.getType().getKind() != Struct.Array) {
    		report_error("promenljiva "+designatorArrayName.getI1()+" nije niz! ", designatorArrayName);
    		designatorArrayName.obj = Tab.noObj;
    	}
    	else
    		designatorArrayName.obj = arrObj;
    }
    
    @Override
    public void visit(DesignatorIdentSquare designatorIdentSquare) {
    	if(designatorIdentSquare.getDesignatorArrayName().obj == Tab.noObj)
    		designatorIdentSquare.obj = Tab.noObj;
    	else {
    		if(designatorIdentSquare.getExpr().struct.equals(Tab.intType)) {
    			designatorIdentSquare.obj = new Obj(Obj.Elem, "elem[" + designatorIdentSquare.getDesignatorArrayName().obj.getName() + "]", designatorIdentSquare.getDesignatorArrayName().obj.getType().getElemType());
    			//INFO
    			report_info("naziv niza: "+designatorIdentSquare.getDesignatorArrayName().getI1()+" obj:"+designatorIdentSquare.obj.toString(), designatorIdentSquare);
    		}
    		else {
    			report_error("Indeks niza nije INT", designatorIdentSquare);
    			designatorIdentSquare.obj = Tab.noObj;
    		}    			
    	}
    }
    
    @Override
    public void visit(DesignatorIdent designatorIdent) {
    	Obj desObj = Tab.find(designatorIdent.getI1());
    	if(desObj == Tab.noObj) {
    		report_error("ime "+designatorIdent.getI1()+" nije deklarisano! ", designatorIdent);
    		designatorIdent.obj = Tab.noObj;
    	}
    	else if(desObj.getKind() == Obj.Var || desObj.getKind() == Obj.Con) {//videti jel treba Con
    		designatorIdent.obj = desObj;
    		//INFO
    		report_info("naziv promenljive: "+designatorIdent.getI1()+" ispis objekta:"+designatorIdent.obj.toString(),designatorIdent);
    	}
    	else {
    		report_error("ime "+designatorIdent.getI1()+" nije validno! ", designatorIdent);
    		designatorIdent.obj = Tab.noObj;
    	}
    		
    }
    
    @Override
    public void visit(FactorDesignator factorDesignator) {
    	factorDesignator.struct = factorDesignator.getDesignator().obj.getType();
    }
    
    @Override
    public void visit(FactorCharConst factorCharConst) {
    	factorCharConst.struct = Tab.charType;
    }
    
    @Override
    public void visit(FactorBoolConst factorBoolConst) {
    	factorBoolConst.struct = boolType;
    }
    
    @Override
    public void visit(FactorNumConst factorNumConst) {
    	factorNumConst.struct = Tab.intType;
    }
    
    @Override
    public void visit(FactorNewTypeSquare factorNewTypeSquare) {
    	if(factorNewTypeSquare.getExpr().struct != Tab.intType) {
    		report_error("FactorNewTypeSquare: Tip neterminala Expr mora biti int." , factorNewTypeSquare);
    	}
    	factorNewTypeSquare.struct = new Struct(Struct.Array, currentType);
    }
    
    @Override
    public void visit(FactorParensExpr factorParensExpr) { 
    	factorParensExpr.struct = factorParensExpr.getExpr().struct;
    }
    
    @Override
    public void visit(Factor factor) {
    	if(factor.getMinus() instanceof MinusYes) {
    		if(factor.getFactorSub().struct.equals(Tab.intType))
    			factor.struct = Tab.intType;
    		else
    		{
    			report_error("Negira se ne INT vrednost", factor);
    			factor.struct = Tab.noType;
    		}
    	}
    	else
    		factor.struct = factor.getFactorSub().struct;
    }
    
    @Override
    public void visit(MulopFactorListFirst mulopFactorListFirst) {
    	mulopFactorListFirst.struct = mulopFactorListFirst.getFactor().struct;
    }
    
    @Override
    public void visit(MulopFactorListMore mulopFactorListMore) {
    	if(mulopFactorListMore.getMulopFactorList().struct.equals(Tab.intType) && mulopFactorListMore.getFactor().struct.equals(Tab.intType))
    		mulopFactorListMore.struct = Tab.intType;
    	else {
    		report_error("Mnozenje/Deljenje ne INT cinioca", mulopFactorListMore);
    		mulopFactorListMore.struct = Tab.noType;
    	}
    }
    
    @Override
    public void visit(Term term) {
    	term.struct = term.getMulopFactorList().struct;
    }
    
    
    @Override
    public void visit(AddopTermListFirst addopTermListFirst) {
    	addopTermListFirst.struct = addopTermListFirst.getTerm().struct;
    }
    
    @Override
    public void visit(AddopTermListMore addopTermListMore) {
    	if(addopTermListMore.getAddopTermList().struct.equals(Tab.intType) && addopTermListMore.getTerm().struct.equals(Tab.intType))
    		addopTermListMore.struct = Tab.intType;
    	else {
    		report_error("Sabiranje/Oduzimanje ne INT sabiraka", addopTermListMore);
    		addopTermListMore.struct = Tab.noType;
    	}
    }
    
    @Override
    public void visit(Expr expr) {
    	expr.struct = expr.getAddopTermList().struct;
    }
    
    @Override
    public void visit(DesignatorStatementDec designatorStatementDec) {
    	if(designatorStatementDec.getDesignator().obj.getKind() == Obj.Var || designatorStatementDec.getDesignator().obj.getKind() == Obj.Elem) {
    		if(!designatorStatementDec.getDesignator().obj.getType().equals(Tab.intType))
    			report_error("Dekrementira se ne INT promenljiva", designatorStatementDec);
    	}
    	else
    		report_error("Dekrementira se nevalidna promenljiva", designatorStatementDec);
    }
    
    @Override
    public void visit(DesignatorStatementInc designatorStatementInc) {
    	if(designatorStatementInc.getDesignator().obj.getKind() == Obj.Var || designatorStatementInc.getDesignator().obj.getKind() == Obj.Elem) {
    		if(!designatorStatementInc.getDesignator().obj.getType().equals(Tab.intType))
    			report_error("Inkremetira se ne INT promenljiva", designatorStatementInc);
    	}
    	else
    		report_error("Inkremetira se nevalidna promenljiva", designatorStatementInc);
    }    
    @Override
    public void visit(DesignatorStatementAssign designatorStatementAssign) {
    	if(!designatorStatementAssign.getExpr().struct.assignableTo(designatorStatementAssign.getDesignator().obj.getType())) {
    		report_error("Nevalidna dodela vrednosti promenljivoj " + designatorStatementAssign.getDesignator().obj.getName(), designatorStatementAssign);
    	}
    	//dodata provera! ******
    	if(!(designatorStatementAssign.getDesignator().obj.getKind() == Obj.Var || designatorStatementAssign.getDesignator().obj.getKind() == Obj.Elem)) {
    		report_error("ne moze se dodeliti vrednost NE promenljivi/elementu niza",designatorStatementAssign);
    	}
    }
    @Override
    public void visit(SingleStatementRead singleStatementRead) {
    	if(singleStatementRead.getDesignator().obj.getKind() == Obj.Var ||
    		singleStatementRead.getDesignator().obj.getKind() == Obj.Elem) {
    		if(singleStatementRead.getDesignator().obj.getType()==Tab.charType ||
    		   singleStatementRead.getDesignator().obj.getType()==Tab.intType ||
    		   singleStatementRead.getDesignator().obj.getType()== boolType ||
    		   ((singleStatementRead.getDesignator().obj.getType().getKind() == Struct.Array) &&
    				   (singleStatementRead.getDesignator().obj.getType().getElemType() == Tab.intType ||
    				    singleStatementRead.getDesignator().obj.getType().getElemType() == Tab.charType ||
    				    singleStatementRead.getDesignator().obj.getType().getElemType() == boolType)
    			))
    		{    			
    			report_info("korektan tip designatora za read",singleStatementRead);
    		}else {
    			report_error("Designator kod read nije tipa int/char/bool ili niz od datih tipova", singleStatementRead);
    		}	
    	}else
    		report_error("Designator kod read nije promeljiva/element niza", singleStatementRead);	
    }
    
    @Override
    public void visit(SingleStatementPrint singleStatementPrint) {
    	Struct str = singleStatementPrint.getExpr().struct;
    	if(!(str == Tab.charType || str == Tab.intType || str == boolType ||
    	    (str.getKind() == Struct.Array && (str.getElemType()== Tab.charType || str.getElemType()== Tab.intType || str.getElemType()== boolType))
    	   ))
    	    	report_error("Print: expr nije tipa int/char/bool ili niz od int/char/bool", singleStatementPrint);
//    	else 
//    		report_info("SingleStatementPrint: "+singleStatementPrint.getExpr(),singleStatementPrint);    	
    }
    
    @Override
    public void visit(SingleStatementPrintNumConst singleStatementPrintNumConst) {
    	Struct str = singleStatementPrintNumConst.getExpr().struct;
    	if(!(str == Tab.charType || str == Tab.intType || str == boolType ||
    	    (str.getKind() == Struct.Array && (str.getElemType()== Tab.charType || str.getElemType()== Tab.intType || str.getElemType()== boolType))
    	  ))
    	    	report_error("PrintNumConst: expr nije tipa int/char/bool ili niz od int/char/bool", singleStatementPrintNumConst);
    }

    Set<String> labelList = new HashSet<>();
    Set<String> labelGotoList = new HashSet<>();

    @Override
    public void visit(Label label) {
    	if(currentMethod == null)
    		report_error("Labela nije definisana u okviru tekuce f-je", label);
    	else {
    		String label_s = label.getI1();
    		labelList.add(label_s);   		
    	}
    }
    
    @Override
    public void visit(LabelGoto labelGoto) {
    	String label_s = labelGoto.getI1();
    	labelGotoList.add(label_s);    	
    }    
    
    public boolean passed(){
    	return !errorDetected;
    }

}
