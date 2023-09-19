package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;

public class FlyingKing extends Move {

	protected FlyingKing(Board board, Coordinate start, Coordinate end) {
		super(MoveType.FLYING_KING, board, start, end);
	}

	public static boolean isValidFlyingKing(Board board, Coordinate start, Coordinate end) {
		if (!commonValidation(board, start, end)) {
			return false;
		}
		return false;
	}

	@Override
	public void commitMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Checker capturedPiece() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}

}
