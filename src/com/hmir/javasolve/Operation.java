package com.hmir.javasolve;

import java.math.BigDecimal;
import java.math.MathContext;

public class Operation {

	private static final String PLUS = "+";
	private static final String MINUS = "-";
	private static final String TIMES = "*";
	private static final String DIVIDE = "/";
	private static final String POWER = "^";
	
	private static final String[] OPERATIONS = {PLUS, MINUS, TIMES, DIVIDE, POWER};
	
	private String type;
	private Expression expr;

	public Operation(String type){
		this.type = type;
	}
	
	public Operation(String type, Expression expr) { 
		this.type = type;
		this.expr = expr;
	}

	public void solve(int thisIndex) {
		int indexBefore = thisIndex - 1;
		int indexAfter = thisIndex + 1;
		
		BigDecimal x = new BigDecimal(expr.getArrayList().get(indexBefore).toString());
		BigDecimal y = new BigDecimal(expr.getArrayList().get(indexAfter).toString());
		
		if (type.equals(PLUS)) expr.replaceObjects(indexBefore, indexAfter, x.add(y));
		
		else if (type.equals(MINUS)) expr.replaceObjects(indexBefore, indexAfter, x.subtract(y));
		
		else if (type.equals(TIMES)) expr.replaceObjects(indexBefore, indexAfter, x.multiply(y, MathContext.DECIMAL64));
		
		else if (type.equals(DIVIDE)) expr.replaceObjects(indexBefore, indexAfter, x.divide(y, MathContext.DECIMAL64));
		
		else if (type.equals(POWER)) expr.replaceObjects(indexBefore, indexAfter, new BigDecimal(Math.pow(x.doubleValue(), y.doubleValue()), MathContext.DECIMAL64));
		
	}

	@Override
	public String toString() { //prints type of operation 
		return(type);
	}
	
	public static boolean isOperation(String s){
		for(String op : OPERATIONS){
			if(s.equals(op)) return true;
		}
		return false;
	}
	
	public int precedence(){ 
		if (type.equals(PLUS)) {
			return 0; 
		}
		else if (type.equals(MINUS)) {
			return 0;
		}
		else if (type.equals(TIMES)) {
			return 1;
		}
		else if (type.equals(DIVIDE)) {
			return 1;
		}
		else {
			return 2;
		}
	}
	
	public void setExpr(Expression expr){
		this.expr = expr;
	}

}
