package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;

public class CornerJump extends Move {

	public CornerJump(Coordinate start, Coordinate end) {
		super(MoveType.CORNER_JUMP, start, end);
	}

	public static boolean isValidCornerJump(Coordinate start, Coordinate end) {
		if (start.getCol() != end.getCol()) {
			return false;
		}
		
		if (start.getCol() != 1 && start.getCol() != 6) {
			return false;
		}
		
		if (Math.abs(end.getRow() - start.getRow()) != 2) {
			return false;
		}
		
		return true;
	}

	@Override
	public Coordinate getCapturedPieceLocation() {
		if (start.getRow() < end.getRow()) {
			if (start.getCol() == 6) {
				return new Coordinate(7, start.getRow() + 1);
			}
			
			if (start.getCol() == 1) {
				return new Coordinate(0, start.getRow() + 1);
			}
		}
		
		if (start.getRow() > end.getRow()) {
			if (start.getCol() == 6) {
				return new Coordinate(7, start.getRow() - 1);
			}
			
			if (start.getCol() == 1) {
				return new Coordinate(0, start.getRow() - 1);
			}
		}
		
		return null;
	}

	@Override
	public boolean isValidMoveType() {
		return true;
	}

}
