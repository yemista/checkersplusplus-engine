package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.util.MoveUtil;

public class FlyingKing extends Move {

	public FlyingKing(Coordinate start, Coordinate end) {
		super(MoveType.FLYING_KING, start, end);
	}

	public static boolean isValidFlyingKing(Board board, Coordinate start, Coordinate end) {
		if (!MoveUtil.commonValidation(board, start, end)) {
			return false;
		}
		return false;
	}
	
	@Override
	public boolean isValidMoveType() {
		return true;
	}

	@Override
	public Coordinate getCapturedPieceLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean findObstructionsOnPath(Board workingBoard) {
		// TODO Auto-generated method stub
		return false;
	}

}
