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
			
			int startRow = start.getRow();
			int startCol = start.getCol();
			
			while (startCol < 7 && startCol > 0) {
				startRow += rowDirection;
				startCol += colDirection;
				
				if (startCol == 7 || startCol == 0) {
					break;
				}
			}
			
			if (Math.abs(end.getRow() - startRow) == 1 && Math.abs(end.getCol() - startCol) == 1) {
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
				return new Coordinate(7, end.getRow() - 1);
			} else {
				return new Coordinate(7, end.getRow() + 1);
			}
		} else if (end.getCol() == 1) {
			if (end.getRow() > start.getRow()) {
				return new Coordinate(0, end.getRow() - 1);
			} else {
				return new Coordinate(0, end.getRow() + 1);
			}
		}
		
		return null;
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
		int startRow = start.getRow();
		int startCol = start.getCol();
		
		while (startCol < 7 && startCol > 0) {
			startRow += rowDirection;
			startCol += colDirection;
			
			if (startCol == 7 || startCol == 0) {
				break;
			}
		}
		
		Coordinate endOfBoard = new Coordinate(startCol, startRow);
		
		for (Coordinate checkLocation = new Coordinate(start.getCol() + colDirection, start.getRow() + rowDirection); 
				!checkLocation.equals(endOfBoard);
				checkLocation = new Coordinate(checkLocation.getCol() + colDirection, checkLocation.getRow() + rowDirection)) {
			if (board.getPiece(checkLocation) != null) {
				obstructionCount++;
			}
		}
		
		if (obstructionCount > 0) {
			return true;
		}
		
		return false;
	}

}
