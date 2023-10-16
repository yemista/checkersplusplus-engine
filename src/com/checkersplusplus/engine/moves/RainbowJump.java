package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;

public class RainbowJump extends Move {

	public RainbowJump(Coordinate start, Coordinate end) {
		super(MoveType.RAINBOW_JUMP, start, end);
	}

	public static boolean isValidRainbowJump(Coordinate start, Coordinate end) {
		return isValidHorizontalJump(start, end) || isValidVerticalJump(start, end);
	}
	
	private static Coordinate getCapturedPieceLocationFromVerticalJump(Coordinate start, Coordinate end) {
		Coordinate opponentLocation = null;
		
		if (end.getRow() > start.getRow()) {
			opponentLocation = new Coordinate(start.getCol(), start.getRow() + 2);
		} else {
			opponentLocation = new Coordinate(start.getCol(), start.getRow() - 2);
		}
		
		return opponentLocation;
	}

	private static Coordinate getCapturedPieceLocationFromHorizontalJump(Coordinate start, Coordinate end) {
		Coordinate opponentLocation = null;
		
		if (end.getCol() > start.getCol()) {
			opponentLocation = new Coordinate(start.getCol() + 2, start.getRow());
		} else {
			opponentLocation = new Coordinate(start.getCol() - 2, start.getRow());
		}
		
		return opponentLocation;
	}
	
    private static boolean isValidHorizontalJump(Coordinate start, Coordinate end) {
        int toColLeft = start.getCol() - 4;
        int toColRight = start.getCol() + 4;
        return start.getRow() == end.getRow()
                    && (end.getCol() == toColLeft || end.getCol() == toColRight);
    }

    private static boolean isValidVerticalJump(Coordinate start, Coordinate end) {
        int toRowUp = start.getRow() + 4;
        int toRowDown = start.getRow() - 4;
        return (end.getRow() == toRowUp || end.getRow() == toRowDown)
        		&& end.getCol() == start.getCol();
    }

    @Override
	public Coordinate getCapturedPieceLocation() {
		if (isValidHorizontalJump(start, end)) {
			return getCapturedPieceLocationFromHorizontalJump(start, end);
		}
		
		if (isValidVerticalJump(start, end)) {
			return getCapturedPieceLocationFromVerticalJump(start, end);
		}
		
		return null;
	}

	@Override
	public boolean isValidMoveType() {
		return true;
	}
}
