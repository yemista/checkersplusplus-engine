package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;

public class FlyingKingCornerJump extends Move {

	public FlyingKingCornerJump(Coordinate start, Coordinate end) {
		super(MoveType.FLYING_KING_CORNER_JUMP, start, end);
	}

	public static boolean isValidFlyingKingCornerJump(Board board, Coordinate start, Coordinate end) {
		Checker playerPiece = board.getPiece(start);

		if (!(playerPiece instanceof King)) {
			return false;	
		}
		
		if (end.getCol() == 6 || end.getCol() == 1) {
			if (Math.abs(Math.abs(end.getRow() - start.getRow()) - Math.abs(end.getCol() - start.getCol())) == 1) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean isValidMoveType() {
		return true;
	}

	@Override
	public Coordinate getCapturedPieceLocation() {
		if (end.getCol() == 6) {
			if (end.getRow() > start.getRow()) {
				return new Coordinate(6, end.getRow() - 1);
			} else {
				return new Coordinate(6, end.getRow() + 1);
			}
		} else if (end.getCol() == 1) {
			if (end.getRow() > start.getRow()) {
				return new Coordinate(1, end.getRow() - 1);
			} else {
				return new Coordinate(1, end.getRow() + 1);
			}
		}
		
		return null;
	}

}
