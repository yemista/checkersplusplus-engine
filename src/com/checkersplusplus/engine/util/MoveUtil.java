package com.checkersplusplus.engine.util;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.moves.CornerJump;
import com.checkersplusplus.engine.moves.FlyingKing;
import com.checkersplusplus.engine.moves.ForwardMove;
import com.checkersplusplus.engine.moves.InvalidMove;
import com.checkersplusplus.engine.moves.Jump;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.moves.RainbowJump;

public class MoveUtil {
	 
	public static Move createMove(Board board, Coordinate start, Coordinate end) {
    	MoveType type = findMoveType(board, start, end);
    	Move move = null;
    	
    	switch (type) {
    	case FORWARD_MOVE:
    		move = new ForwardMove(start, end);
    	case JUMP:
    		move = new Jump(start, end);
    	case RAINBOW_JUMP:
    		move = new RainbowJump(start, end);
    	case CORNER_JUMP:
    		move = new CornerJump(start, end);
    	case FLYING_KING:
    		move = new FlyingKing(start, end);
    	default:
    		move = new InvalidMove(start, end);
    	}
    	
		return move;
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
}
