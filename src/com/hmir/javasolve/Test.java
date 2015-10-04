package com.hmir.javasolve;

public class Test {

	public static void main(String[] args) {
		Expression x = new Expression("4csc(1)");
		System.out.println(x.simplify());
	}

}
