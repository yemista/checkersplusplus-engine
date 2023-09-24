package com.checkersplusplus.engine.pieces;

import com.checkersplusplus.engine.enums.Color;

public class Checker {
	private final Color color;

    public Checker(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
