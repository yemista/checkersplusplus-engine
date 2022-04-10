package com.checkersplusplus.engine;

public class Move {
	private int fromCol, fromRow, toCol, toRow;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromCol = fromCol;
        this.fromRow = fromRow;
        this.toCol = toCol;
        this.toRow = toRow;
    }

    public Move(Coordinate from, Coordinate to) {
        this.fromRow = from.getRow();
        this.fromCol = from.getCol();
        this.toRow = to.getRow();
        this.toCol = to.getCol();
    }

    public Coordinate getFrom() {
        return new Coordinate(fromCol, fromRow);
    }

    public Coordinate getTo() {
        return new Coordinate(toCol, toRow);
    }

    @Override
    public String toString() {
        return String.format("From: %d,%d To: %d,%d", fromRow, fromCol, toRow, toCol);
    }
}
