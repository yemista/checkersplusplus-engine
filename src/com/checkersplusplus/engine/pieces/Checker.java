package com.checkersplusplus.engine.pieces;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.enums.Direction;
import com.checkersplusplus.engine.util.BoardUtil;

public class Checker {
	private Color color;

    public Checker(Color color) {
        this.color = color;
    }

    private Color getOpponentColor() {
        return getColor() == Color.BLACK ? Color.RED : Color.BLACK;
    }

    public boolean isValidRainbowJump(Coordinate from, Coordinate to) {
        return isValidRainbowJump(from, to, getVerticalDirection());
    }

    public boolean isValidCornerJump(Coordinate from, Coordinate to) {
        return isValidCornerJump(from, to, getVerticalDirection());
    }

    public boolean isValidJump(Coordinate from, Coordinate to) {
        return isValidJump(from, to, getVerticalDirection());
    }

    public boolean isValidMove(Coordinate from, Coordinate to) {
        return isValidMove(from, to, getVerticalDirection());
    }

    private boolean isValidRainbowJump(Coordinate from, Coordinate to, Direction direction) {
        return isValidVerticalJump(from, to, direction) || isValidHorizontalJump(from, to);
    }

    private boolean isValidCornerJump(Coordinate from, Coordinate to, Direction direction) {
        int rowsMoved = direction.getNumericValue() * 2;
        int newRow = from.getRow() + rowsMoved;

        if (from.getCol() != to.getCol()) {
            return false;
        }

        if (from.getCol() != BoardUtil.MAX_COLS - 2 && from.getCol() != 1) {
            return false;
        }

        return from.getCol() == to.getCol() && newRow == to.getRow();
    }

    protected boolean isValidJump(Coordinate from, Coordinate to, Direction direction) {
        return isDiagonalMove(from, to, direction, 2);
    }

    protected boolean isValidMove(Coordinate from, Coordinate to, Direction direction) {
        return isDiagonalMove(from, to, direction, 1);
    }

    private boolean isValidHorizontalJump(Coordinate from, Coordinate to) {
        int toColLeft = from.getCol() - 4;
        int toColRight = from.getCol() + 4;
        return from.getRow() == to.getRow()
                    && (to.getCol() == toColLeft || to.getCol() == toColRight);
    }

    private boolean isValidVerticalJump(Coordinate from, Coordinate to, Direction direction) {
        int toRow = from.getRow() + (direction.getNumericValue() * 4);
        return to.getRow() == toRow && to.getCol() == from.getCol();
    }

    private boolean isDiagonalMove(Coordinate from, Coordinate to, Direction direction, int scale) {
        return from.getRow() + (direction.getNumericValue() * scale) == to.getRow()
                && (from.getCol() + scale == to.getCol() || from.getCol() - scale == to.getCol());
    }

    public Color getColor() {
        return color;
    }

    private Direction getVerticalDirection() {
        if (color == Color.BLACK) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }

    public Checker copyOf() {
        if (this instanceof King) {
            return new King(this.color);
        }

        return new Checker(this.color);
    }
}
