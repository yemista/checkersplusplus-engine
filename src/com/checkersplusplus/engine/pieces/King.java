package com.checkersplusplus.engine.pieces;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;

public class King extends Checker {
	public King(Color color) {
        super(color);
    }

    @Override
    public boolean isValidJump(Coordinate from, Coordinate to) {
        return false;
    }

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        return false;
    }

    @Override
    public boolean isValidRainbowJump(Coordinate from, Coordinate to) {
        return false;
    }

    @Override
    public boolean isValidCornerJump(Coordinate from, Coordinate to) {
        return false;
    }
}
