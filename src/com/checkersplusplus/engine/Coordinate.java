package com.checkersplusplus.engine;

import java.util.Objects;

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
    	return Objects.hashCode(this);
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other instanceof Coordinate) {
    		Coordinate otherCoordinate = (Coordinate) other;
    		return this.getCol() == otherCoordinate.getCol() && this.getRow() == otherCoordinate.getRow();
    	}
    	
    	return false;
    }
}
