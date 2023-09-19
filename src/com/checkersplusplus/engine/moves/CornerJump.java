package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;

public class CornerJump extends Move {

	protected CornerJump(Board board, Coordinate start, Coordinate end) {
		super(MoveType.CORNER_JUMP, board, start, end);
		// TODO Auto-generated constructor stub
	}

	public static boolean isValidCornerJump(Board board, Coordinate start, Coordinate end) {
		if (!commonValidation(board, start, end)) {
			return false;
		}
		
		if (!validCornerJump(board, start, end)) {
			return false;
		}
		
		Checker playerPiece = board.getPiece(start);
		Coordinate opponentPieceLocation = getCapturedPieceLocation(board, start, end);
		
		if (opponentPieceLocation == null) {
			return false;
		}
		
		Checker opponentPiece = board.getPiece(opponentPieceLocation);
		return opponentPiece.getColor() != playerPiece.getColor();
	}

	private static boolean validCornerJump(Board board, Coordinate start, Coordinate end) {
		if (!commonValidation(board, start, end)) {
			return false;
		}
		
		if (start.getCol() != end.getCol()) {
			return false;
		}
		
		if (Math.abs(end.getRow() - start.getRow()) != 2) {
			return false;
		}
		
		Checker playerPiece = board.getPiece(start);
		Coordinate opponentPieceLocation = getCapturedPieceLocation(board, start, end);
		
		if (opponentPieceLocation == null) {
			return false;
		}
		
		Checker opponentPiece = board.getPiece(opponentPieceLocation);
		return opponentPiece.getColor() != playerPiece.getColor();
	}

	private static Coordinate getCapturedPieceLocation(Board board, Coordinate start, Coordinate end) {
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
	public void commitMove() {
		Checker playerPiece = board.getPiece(start);
		board.removePiece(start);
		board.placePiece(playerPiece, end);
		Coordinate opponentLocation = getCapturedPieceLocation(board, start, end);	
		
		if (opponentLocation != null) {
			board.removePiece(opponentLocation);
		}
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
