package com.checkersplusplus.engine.moves;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.MoveType;

public abstract class Move {
	protected final Coordinate start;
	protected final Coordinate end;
	private final MoveType type;

    protected Move(MoveType type, Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
        this.type = type;
    }
	
	public abstract boolean isValidMoveType();
	
	public abstract Coordinate getCapturedPieceLocation();
	
	public MoveType getMoveType() {
		return type;
	}

    @Override
    public String toString() {
        return String.format("Type: %s Start: %d,%d End: %d,%d", type.toString(), start.getCol(), start.getRow(), end.getCol(), end.getRow());
    }

	public Coordinate getStart() {
		return start;
	}
	
	public Coordinate getEnd() {
		return end;
	}
}
