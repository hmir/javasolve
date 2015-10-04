package com.hmir.javasolve;

import java.math.BigDecimal;

public class Function extends Operation{

	//trig functions
	private static final String SIN = "sin";
	private static final String COS = "cos";
	private static final String TAN = "tan";
	private static final String ASIN = "asin";
	private static final String ACOS = "acos";
	private static final String ATAN = "atan";
	private static final String CSC = "csc";
	private static final String SEC = "sec";
	private static final String COT = "cot";
	
	private static final String SQRT = "sqrt";
	private static final String LN = "ln";
	
	private static final String[] FUNCTIONS = {SIN, COS, TAN, ASIN, ACOS, ATAN, CSC, SEC, COT, SQRT, LN};
	
	private String type;
	private Expression expr;
	
	public Function(String type, Expression expr) {
		super(type, expr);
		this.type = type;
		this.expr = expr;
	}

	@Override
	public void solve(int thisIndex) {
		int indexAfter = thisIndex + 1;
		BigDecimal x = new BigDecimal(expr.getArrayList().get(indexAfter).toString());
		
		double angleOperator = 1;
		
		if(expr.getAngleMode() == Expression.DEGREE){
			angleOperator = Math.PI/180;
		}
		
		if (type.equals(SIN)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(Math.sin(x.doubleValue()) * angleOperator));
		else if (type.equals(COS)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(Math.cos(x.doubleValue()) * angleOperator));
		else if (type.equals(TAN)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(Math.tan(x.doubleValue()) * angleOperator));
		else if (type.equals(ASIN)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(Math.asin(x.doubleValue()) * angleOperator));
		else if (type.equals(ACOS)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(Math.acos(x.doubleValue()) * angleOperator));
		else if (type.equals(ATAN)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(Math.atan(x.doubleValue()) * angleOperator));
		else if (type.equals(CSC)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(1/(Math.sin(x.doubleValue()) * angleOperator)));
		else if (type.equals(SEC)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(1/(Math.cos(x.doubleValue()) * angleOperator)));
		else if (type.equals(COT)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(1/(Math.tan(x.doubleValue()) * angleOperator)));
		
		else if(type.equals(SQRT)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(Math.sqrt(x.doubleValue())));
		else if(type.equals(LN)) expr.replaceObjects(thisIndex, indexAfter, new BigDecimal(Math.log(x.doubleValue())));		

	}
	
	public static boolean isFunction(String s){
		for(String f : FUNCTIONS){
			if(s.equals(f)) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public int precedence() {
		return 3;
	}
	
	
}