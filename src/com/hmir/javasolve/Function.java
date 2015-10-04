package com.hmir.javasolve;

import java.math.BigDecimal;

public class Function extends Operation{

	private static final String SIN = "sin";
	private static final String COS = "cos";
	private static final String TAN = "tan";
	private static final String ASIN = "asin";
	private static final String ACOS = "acos";
	private static final String ATAN = "atan";
	
	private static final String[] FUNCTIONS = {SIN, COS, TAN, ASIN, ACOS, ATAN};
	
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