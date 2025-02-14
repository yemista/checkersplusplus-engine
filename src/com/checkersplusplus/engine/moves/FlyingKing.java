package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;

public class FlyingKing extends Move {

	public FlyingKing(Coordinate start, Coordinate end) {
		super(MoveType.FLYING_KING, start, end);
	}

	public static boolean isValidFlyingKing(Board board, Coordinate start, Coordinate end) {
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
		if (end.getCol() > start.getCol() && end.getRow() > start.getRow()) {
			return new Coordinate(end.getCol() - 1, end.getRow() - 1);
		} else if (end.getCol() > start.getCol() && end.getRow() < start.getRow()) {
			return new Coordinate(end.getCol() - 1, end.getRow() + 1);
		} else if (end.getCol() < start.getCol() && end.getRow() > start.getRow()) {
			return new Coordinate(end.getCol() + 1, end.getRow() - 1);
		} else {
			return new Coordinate(end.getCol() + 1, end.getRow() + 1);
		}
	}

	public boolean findObstructionsOnPath(Board board) {
		int colDirection = 0;
		int rowDirection = 0;
		
		if (end.getCol() > start.getCol() && end.getRow() > start.getRow()) {
			colDirection = 1;
			rowDirection = 1;
		} else if (end.getCol() > start.getCol() && end.getRow() < start.getRow()) {
			colDirection = 1;
			rowDirection = -1;
		} else if (end.getCol() < start.getCol() && end.getRow() > start.getRow()) {
			colDirection = -1;
			rowDirection = 1;
		} else {
			colDirection = -1;
			rowDirection = -1;
		}
		
		int obstructionCount = 0;
		Coordinate lastObstruction = null;
		
		for (Coordinate checkLocation = new Coordinate(start.getCol() + colDirection, start.getRow() + rowDirection); 
				!checkLocation.equals(end);
				checkLocation = new Coordinate(checkLocation.getCol() + colDirection, checkLocation.getRow() + rowDirection)) {
			if (board.getPiece(checkLocation) != null) {
				lastObstruction = checkLocation;
				obstructionCount++;
			}
		}
		
		if (obstructionCount > 1) {
			return true;
		}
		
		// If there is only one obstruction and that obstruction is not the piece we are capturing then it is invalid.
		if (obstructionCount == 1 && !lastObstruction.equals(getCapturedPieceLocation())) {
			return true;
		}
		
		return false;
	}

}
