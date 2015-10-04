package com.hmir.javasolve;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Expression {

	public static final int RADIAN = 0;
	public static final int DEGREE = 1;
	
	public static final char OPEN_PAREN = '(';
	public static final char CLOSED_PAREN = ')';
	
	private int angleMode; //radian by default
	
	private ArrayList<Object> a = new ArrayList<Object>(); // ArrayList of numbers, operations, and parenthesis that make up expression

	int currentIndex;
	
	public Expression(){
		
	}	
	
	public Expression(double x){
		a.add(new BigDecimal(x));
		currentIndex++;
	}	
	
	public Expression(double x, int angleMode){
		a.add(new BigDecimal(x));
		currentIndex++;
		setAngleMode(angleMode);
	}
	
	
	public Expression(String x){
		parseString(x);
	}
	
	public Expression(String x, int angleMode){
		parseString(x);
	}	
	
	
	public Expression(Expression x){
		a.add(x.getArrayList());
		currentIndex = x.getArrayList().size();
	}
	
	
	public Expression(Expression x, int angleMode){
		this(x);
		setAngleMode(angleMode);
	}
	
	
	public void setAngleMode(int angleMode) {
		this.angleMode = angleMode;
	}
	
	public int getAngleMode(){
		return angleMode;
	}

	public void add(Object e) { // adds object to ArrayList
		a.add(e);

	}

	public void clear() { // clears entire ArrayList
		a.clear();
	}

	public int size() {
		return a.size();
	}

	public ArrayList<Object> getArrayList() { // returns ArrayList of Expression
		return a;
	}

	public void replaceObjects(int startIndex, int endIndex, Object o) { // replaces objects in array (endIndex inclusive)
		int offset = 0; // offset because indexes will not point to the same values once other values are removed
		for (int i = startIndex; i <= endIndex; i++) {
			if (i == endIndex) { // if i has reached the endIndex
				a.set(i - offset, o);
			} else {
				a.remove(i - offset);
				offset++;
			}
		}
	}
	
	public double simplify(){
		while(locationOfLastOpen() != -1){ //start from innermost parentheses and work outwards
			while(locationOfCorrespondingClosed(locationOfLastOpen()) - locationOfLastOpen() > 2){ //check that expression has been fully simplified
				
				int highestPrecedence = -1;
				for(int i = locationOfLastOpen() + 1; i < locationOfCorrespondingClosed(locationOfLastOpen()); i++){ //search for highest precedence
					if((a.get(i) instanceof Operation || a.get(i) instanceof Function) && ((Operation)a.get(i)).precedence() > highestPrecedence){
						highestPrecedence = ((Operation)a.get(i)).precedence();
					}
				}
			
				for(int i = locationOfLastOpen() + 1; i < locationOfCorrespondingClosed(locationOfLastOpen()); i++){ //simplify
					if((a.get(i) instanceof Operation || a.get(i) instanceof Function) && ((Operation)a.get(i)).precedence() == highestPrecedence){
						((Operation)a.get(i)).solve(i);
					}
				}
			}
			a.remove(locationOfCorrespondingClosed(locationOfLastOpen()));
			a.remove(locationOfLastOpen());
		}
		
		while(size() > 1){ //check that expression has been fully simplified
			int highestPrecedence = -1;
			for(int i = 0; i < size(); i++){ //search for highest precedence
				if((a.get(i) instanceof Operation || a.get(i) instanceof Function) && ((Operation)a.get(i)).precedence() > highestPrecedence){
					highestPrecedence = ((Operation)a.get(i)).precedence();
				}
			}
			for(int i = 0; i < size(); i++){ //simplify
				if((a.get(i) instanceof Operation || a.get(i) instanceof Function) && ((Operation)a.get(i)).precedence() == highestPrecedence){
					((Operation)a.get(i)).solve(i);
				}
			}
		}
		
		return Double.parseDouble(a.get(0).toString());
	}
	
	public static double simplify(String s){
		return (new Expression(s)).simplify();
	}
	
	@Override
	public String toString() { //returns string of ArrayList, values/operations delimited by spaces
		String str = "";
		for (int i = 0; i < a.size(); i++) {
			str += a.get(i).toString().trim() + " ";
		}
		
		return str;
	}
	
	private void parseString(String s){ //parses string into numbers, operations, functions, and parenthesis that are added to arraylist
		s = s.replace(" ", "");
		String currentStr = "";
		for(int i = 0; i < s.length(); i++){
			if(i != s.length() - 1 && Character.isDigit(s.charAt(i)) && Character.isAlphabetic(s.charAt(i+1))) { //e.g. 2sin becomes 2 * sin
				currentStr += s.charAt(i);
				
				a.add(new BigDecimal(currentStr));
				currentIndex++;
				
				a.add(new Operation("*", this));
				currentIndex++;
				
				currentStr = "";
				
			}
			else if(Operation.isOperation(s.charAt(i) + "")){ //adds operation to arraylist
				if(!currentStr.isEmpty()){
					if(Function.isFunction(currentStr)){
						a.add(new Function(currentStr, this));
					}
					else{
						a.add(new BigDecimal(Double.parseDouble(currentStr)));
					}
				}
				currentIndex++;
				
				a.add(new Operation(s.charAt(i) + "", this));
				currentIndex++;
				
				currentStr = "";
			}
			else if(s.charAt(i) == OPEN_PAREN || s.charAt(i) == CLOSED_PAREN){ //e.g. (23)(43) becomes (23) * (43)
				if(!currentStr.isEmpty()) { //current number is added to arraylist
					if (Function.isFunction(currentStr)) {
						a.add(new Function(currentStr, this));
					}
					else {
						a.add(new BigDecimal(Double.parseDouble(currentStr)));
					}
				}
				if(i != s.length() - 1 && s.charAt(i) == CLOSED_PAREN && s.charAt(i+1) == OPEN_PAREN){
					a.add(')');
					a.add(new Operation("*", this));
				}
				else{
					a.add(s.charAt(i));
				}
				currentStr = "";
			}
			else{
				currentStr += s.charAt(i);
			}
		}
		if(!currentStr.isEmpty()) a.add(new BigDecimal(Double.parseDouble(currentStr)));
	}

	private int locationOfLastOpen(){ //returns location of last open parenthesis
		for (int i = size() - 1; i >= 0; i--) {
			if(a.get(i).equals(OPEN_PAREN)) return i;
		}
		return -1;
	}
	
	private int locationOfCorrespondingClosed(int openIndex) { //returns location of the corresponding closed parenthesis to an open parenthesis
		for (int i = openIndex; i < a.size(); i++) {
			if (a.get(i).equals(CLOSED_PAREN)) {
				return i;
			}
		}
		a.add(CLOSED_PAREN);
		return a.size()-1;
	}
}