package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.util.MoveUtil;

public class CornerJump extends Move {

	public CornerJump(Coordinate start, Coordinate end) {
		super(MoveType.CORNER_JUMP, start, end);
		// TODO Auto-generated constructor stub
	}

	public static boolean isValidCornerJump(Board board, Coordinate start, Coordinate end) {
		if (!MoveUtil.commonValidation(board, start, end)) {
			return false;
		}
		
		if (start.getCol() != end.getCol()) {
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
				return new Coordinate(1, start.getRow() + 1);
			}
		}
		
		if (start.getRow() > end.getRow()) {
			if (start.getCol() == 6) {
				return new Coordinate(7, start.getRow() - 1);
			}
			
			if (start.getCol() == 1) {
				return new Coordinate(1, start.getRow() - 1);
			}
		}
		
		return null;
	}

	@Override
	public boolean isValidMoveType() {
		return true;
	}

}
