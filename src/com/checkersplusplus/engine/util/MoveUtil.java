package com.checkersplusplus.engine.util;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;

public class MoveUtil {
	 
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
