package com.checkersplusplus.engine;

public class Coordinate {
	private int col, row;

    public Coordinate(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
    
    @Override
    public int hashCode() {
    	int hash = 7;
        hash = 31 * hash + col;
        hash = 31 * hash + row;
        return hash;
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other instanceof Coordinate) {
    		Coordinate otherCoordinate = (Coordinate) other;
    		return this.getCol() == otherCoordinate.getCol() && this.getRow() == otherCoordinate.getRow();
    	}
    	
    	return false;
    }
    
    @Override
    public String toString() {
    	return String.format("col: %d row: %d", col, row);
    }
}
