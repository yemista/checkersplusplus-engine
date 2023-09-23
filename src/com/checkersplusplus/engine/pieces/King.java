package com.checkersplusplus.engine.pieces;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.moves.CornerJump;
import com.checkersplusplus.engine.moves.FlyingKing;
import com.checkersplusplus.engine.moves.ForwardMove;
import com.checkersplusplus.engine.moves.Jump;
import com.checkersplusplus.engine.moves.RainbowJump;

public class King extends Checker {
	public King(Color color) {
        super(color);
    }
	
	@Override
	protected MoveType findMoveType(Board board, Coordinate start, Coordinate end) {
		if (FlyingKing.isValidFlyingKing(board, start, end)) {
			return MoveType.FLYING_KING;
		}
		
		return MoveType.INVALID;
	}
}
