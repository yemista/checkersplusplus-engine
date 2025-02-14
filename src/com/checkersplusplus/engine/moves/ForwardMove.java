package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;

public class ForwardMove extends Move {

	public ForwardMove(Coordinate start, Coordinate end) {
		super(MoveType.FORWARD_MOVE, start, end);
	}

	@Override
	public Coordinate getCapturedPieceLocation() {
		// Forward move can never capture a piece. 
		return null;
	}

	@Override
	public boolean isValidMoveType() {
		return true;
	}

	/**
	 * A valid forward move is one square up or down(depending on team color), and one square left or one square right.
	 * Red team must move down one square while black team must move up one square. The left or right movement is
	 * independent or team color. The ending square must be empty for the move to be valid.
	 */
	public static boolean isValidForwardMove(Coordinate start, Coordinate end) {
		return Math.abs(end.getRow() - start.getRow()) == 1 && 
				Math.abs(end.getCol() - start.getCol()) == 1; 
	}

}
