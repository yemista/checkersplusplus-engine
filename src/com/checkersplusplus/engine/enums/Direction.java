package com.checkersplusplus.engine.enums;

public enum Direction {
	UP(1),
    DOWN(-1);

    private int numericValue;

    Direction(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }
}
