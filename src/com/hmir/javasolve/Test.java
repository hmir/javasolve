package com.hmir.javasolve;

public class Test {

	public static void main(String[] args) {
		Expression x = new Expression("(2)(2)");
		System.out.println(x.simplify());
	}

}
