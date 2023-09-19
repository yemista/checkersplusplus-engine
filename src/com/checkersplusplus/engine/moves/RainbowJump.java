package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.enums.Direction;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;

public class RainbowJump extends Move {

	protected RainbowJump(Board board, Coordinate start, Coordinate end) {
		super(MoveType.RAINBOW_JUMP, board, start, end);
	}

	public static boolean isValidRainbowJump(Board board, Coordinate start, Coordinate end) {
		if (!commonValidation(board, start, end)) {
			return false;
		}
		
		if (!isValidHorizontalJump(start, end)) {
			return false;
		}
		
		Checker playerPiece = board.getPiece(start);
		Direction direction = playerPiece.getColor() == Color.BLACK ? Direction.UP : Direction.DOWN;
		
		if (!isValidVerticalJump(start, end, direction)) {
			return false;
		}
		
		Coordinate opponentPieceLocation = getCapturedPieceLocation(board, start, end);
		
		if (opponentPieceLocation == null) {
			return false;
		}
		
		Checker opponentPiece = board.getPiece(opponentPieceLocation);
		return opponentPiece.getColor() != playerPiece.getColor();
	}
	
	private static Coordinate getCapturedPieceLocationFromVerticalJump(Board board, Coordinate start, Coordinate end) {
		Coordinate opponentLocation = null;
		
		if (end.getRow() > start.getRow()) {
			opponentLocation = new Coordinate(start.getRow() + 2, start.getCol());
		} else {
			opponentLocation = new Coordinate(start.getRow() - 2, start.getCol());
		}
		
		return opponentLocation;
	}

	private static Coordinate getCapturedPieceLocationFromHorizontalJump(Board board, Coordinate start, Coordinate end) {
		Coordinate opponentLocation = null;
		
		if (end.getCol() > start.getCol()) {
			opponentLocation = new Coordinate(start.getRow(), start.getCol() + 2);
		} else {
			opponentLocation = new Coordinate(start.getRow(), start.getCol() - 2);
		}
		
		return opponentLocation;
	}
	
    private static boolean isValidHorizontalJump(Coordinate start, Coordinate end) {
        int toColLeft = start.getCol() - 4;
        int toColRight = start.getCol() + 4;
        return start.getRow() == end.getRow()
                    && (end.getCol() == toColLeft || end.getCol() == toColRight);
    }

    private static boolean isValidVerticalJump(Coordinate start, Coordinate end, Direction direction) {
        int toRow = start.getRow() + (direction.getNumericValue() * 4);
        return end.getRow() == toRow && end.getCol() == start.getCol();
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
		Coordinate opponentPieceLocation = null;
		
		if (isValidHorizontalJump(start, end)) {
			opponentPieceLocation = getCapturedPieceLocationFromHorizontalJump(board, start, end);
		}
		
		Direction movementDirection = playerPiece.getColor() == Color.BLACK ? Direction.UP : Direction.DOWN;
		
		if (isValidVerticalJump(start, end, movementDirection)) {
			opponentPieceLocation = getCapturedPieceLocationFromVerticalJump(board, start, end);
		}
		
		return opponentPieceLocation;
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
