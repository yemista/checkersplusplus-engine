package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.util.MoveUtil;

public class Jump extends Move {

	public Jump(Coordinate start, Coordinate end) {
		super(MoveType.JUMP, start, end);
	}

	public static boolean isValidJump(Board board, Coordinate start, Coordinate end) {
		if (!MoveUtil.commonValidation(board, start, end)) {
			return false;
		}
		
		// Valid jump should progress 2 rows.
		if (Math.abs(end.getRow() - start.getRow()) != 2) {
			return false;
		}
		
		// Valid jump should also progress 2 columns.
		if (Math.abs(start.getCol() - end.getCol()) != 2) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public Coordinate getCapturedPieceLocation() {
		Coordinate opponentLocation = null;
		
		// BLACK
		if (end.getRow() > start.getRow()) {
			if (end.getCol() > start.getCol()) {
				opponentLocation = new Coordinate(start.getRow() + 1, start.getCol() + 1);
			} else {
				opponentLocation = new Coordinate(start.getRow() + 1, start.getCol() - 1);
			}
		}
		
		// RED
		if (end.getRow() < start.getRow()) {
			if (end.getCol() > start.getCol()) {
				opponentLocation = new Coordinate(start.getRow() - 1, start.getCol() + 1);
			} else {
				opponentLocation = new Coordinate(start.getRow() - 1, start.getCol() - 1);
			}
		}
		
		return opponentLocation;
	}

	@Override
	public boolean isValidMoveType() {
		return true;
	}

}
