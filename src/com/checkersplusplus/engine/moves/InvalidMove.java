package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;

public class InvalidMove extends Move {

	protected InvalidMove(Board board, Coordinate start, Coordinate end) {
		super(MoveType.INVALID, board, start, end);
	}

	@Override
	public void commitMove() {
		
	}

	@Override
	protected Checker capturedPiece() {
		return null;
	}

	@Override
	public boolean isValid() {
		return false;
	}
}
