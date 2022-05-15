package com.checkersplusplus.engine.enums;

public enum Color {
	RED('O'),
    BLACK('X');
	
	private char symbol;
	
	Color(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}
	
	public static Color fromSymbol(char symbol) {
		if (symbol == 'O') {
			return Color.RED;
		}
		
		if (symbol == 'X') {
			return Color.BLACK;
		}
		
		return null;
	}
}
