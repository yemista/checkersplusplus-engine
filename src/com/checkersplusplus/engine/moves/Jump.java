package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;

public class Jump extends Move {

	protected Jump(Board board, Coordinate start, Coordinate end) {
		super(MoveType.JUMP, board, start, end);
	}

	public static boolean isValidJump(Board board, Coordinate start, Coordinate end) {
		if (!commonValidation(board, start, end)) {
			return false;
		}
		
		Checker playerPiece = board.getPiece(start);
		
		// Valid jump should progress 2 rows.
		if (Math.abs(end.getRow() - start.getRow()) != 2) {
			return false;
		}
		
		// Valid jump should also progress 2 columns.
		if (Math.abs(start.getCol() - end.getCol()) != 2) {
			return false;
		}
		
		Coordinate opponentLocation = getCapturedPieceLocation(board, start, end);
		Checker capturedPiece = board.getPiece(opponentLocation);
		
		if (capturedPiece == null) {
			return false;
		}
		
		// Valid jump MUST capture a piece
		if (playerPiece.getColor() == Color.BLACK) {
			return capturedPiece.getColor() == Color.RED;
		}
		
		if (playerPiece.getColor() == Color.RED) {
			return capturedPiece.getColor() == Color.BLACK;
		}		
		
		return false;
	}


	@Override
	public void commitMove() {
		Checker playerPiece = board.getPiece(start);
		board.removePiece(start);
		board.placePiece(playerPiece, end);
		Coordinate opponentLocation = getCapturedPieceLocation(board, start, end);	
		
		if (opponentLocation != null) {
			board.removePiece(opponentLocation);
		}
	}
	
	private static Coordinate getCapturedPieceLocation(Board board, Coordinate start, Coordinate end) {
		Checker playerPiece = board.getPiece(start);
		Coordinate opponentLocation = null;
		
		if (playerPiece.getColor() == Color.BLACK) {
			if (end.getCol() > start.getCol()) {
				opponentLocation = new Coordinate(start.getRow() + 1, start.getCol() + 1);
			} else {
				opponentLocation = new Coordinate(start.getRow() + 1, start.getCol() - 1);
			}
		}
		
		if (playerPiece.getColor() == Color.RED) {
			if (end.getCol() > start.getCol()) {
				opponentLocation = new Coordinate(start.getRow() - 1, start.getCol() + 1);
			} else {
				opponentLocation = new Coordinate(start.getRow() - 1, start.getCol() - 1);
			}
		}
		
		return opponentLocation;
	}


	@Override
	protected Checker capturedPiece() {
		Coordinate opponentLocation = getCapturedPieceLocation(board, start, end);
		
		if (opponentLocation == null) {
			return null;
		}
		
		return board.getPiece(opponentLocation);
	}


	@Override
	public boolean isValid() {
		return true;
	}

}
