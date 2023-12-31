package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;

public class FlyingKingCornerJump extends Move {

	protected FlyingKingCornerJump(MoveType type, Coordinate start, Coordinate end) {
		super(MoveType.FLYING_KING_CORNER_JUMP, start, end);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isValidFlyingKingCornerJump(Board board, Coordinate start, Coordinate end) {
		Checker playerPiece = board.getPiece(start);

		if (!(playerPiece instanceof King)) {
			return false;	
		}
		
		return Math.abs(start.getCol() - end.getCol()) == Math.abs(start.getRow() - end.getRow());
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

}
