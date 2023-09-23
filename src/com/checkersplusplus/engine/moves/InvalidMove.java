package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;

public class InvalidMove extends Move {

	public InvalidMove(Coordinate start, Coordinate end) {
		super(MoveType.INVALID, start, end);
	}

	@Override
	public Coordinate getCapturedPieceLocation() {
		return null;
	}

	@Override
	public boolean isValidMoveType() {
		return false;
	}
}
