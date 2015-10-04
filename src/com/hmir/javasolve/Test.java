package com.hmir.javasolve;

public class Test {

	public static void main(String[] args) {
		Expression x = new Expression("log10(10)");
		System.out.println(x.simplify());
	}

}
