package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.util.BoardUtil;

public abstract class Move {
	protected Coordinate start;
	protected Coordinate end;
	private MoveType type;
	protected Board board;

    protected Move(MoveType type, Board board, Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.board = board;
    }
    
    public static Move createMove(Board board, Coordinate start, Coordinate end) {
    	MoveType type = findMoveType(board, start, end);
    	Move move = null;
    	
    	switch (type) {
    	case FORWARD_MOVE:
    		move = new ForwardMove(board, start, end);
    	case JUMP:
    		move = new Jump(board, start, end);
    	case RAINBOW_JUMP:
    		move = new RainbowJump(board, start, end);
    	case CORNER_JUMP:
    		move = new CornerJump(board, start, end);
    	case FLYING_KING:
    		move = new FlyingKing(board, start, end);
    	default:
    		move = new InvalidMove(board, start, end);
    	}
    	
		return move;
    }
    
    public static boolean commonValidation(Board board, Coordinate start, Coordinate end) {
    	if (!BoardUtil.isWithinBounds(start)) {
            return false;
        }

        if (!BoardUtil.isWithinBounds(end)) {
            return false;
        }

        if (!board.isOccupied(start)) {
            return false;
        }

        if (board.isOccupied(end)) {
            return false;
        }
        
        return true;
    }
    
    private static MoveType findMoveType(Board board, Coordinate start, Coordinate end) {
		if (ForwardMove.isValidForwardMove(board, start, end)) {
			return MoveType.FORWARD_MOVE;
		}
    	
    	if (Jump.isValidJump(board, start, end)) {
			return MoveType.JUMP;
		}
		
		if (RainbowJump.isValidRainbowJump(board, start, end)) {
			return MoveType.RAINBOW_JUMP;
		}
		
		if (CornerJump.isValidCornerJump(board, start, end)) {
			return MoveType.CORNER_JUMP;
		}
		
		if (FlyingKing.isValidFlyingKing(board, start, end)) {
			return MoveType.FLYING_KING;
		}
		
		return MoveType.INVALID;
	}
	
	public abstract void commitMove();
	
	protected abstract Checker capturedPiece();
	
	public abstract boolean isValid();
	
	public boolean isPieceCaptured() {
		return capturedPiece() != null;
	}

    @Override
    public String toString() {
        return String.format("Start: %d,%d End: %d,%d", start.getRow(), start.getCol(), end.getRow(), end.getCol());
    }
}
