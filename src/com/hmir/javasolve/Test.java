package com.hmir.javasolve;

public class Test {

	public static void main(String[] args) {
		Expression x = new Expression("log10(10)"); 
		System.out.println(x.simplify()); // returns 1
		
		System.out.println(Expression.simplify("5^2 * (2 + 3) + cos(0)")); // returns 126.0
	}

}
