package com.checkersplusplus.engine.enums;

public enum Color {
	RED('O', 'o'),
    BLACK('X', 'x');
	
	private char symbol;
	private char kingSymbol;
	
	Color(char symbol, char kingSymbol) {
		this.symbol = symbol;
		this.kingSymbol = kingSymbol;
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
		
		if (symbol == 'o') {
			return Color.RED;
		}
		
		if (symbol == 'x') {
			return Color.BLACK;
		}
		
		return null;
	}

	public char getKingSymbol() {
		return kingSymbol;
	}
}
