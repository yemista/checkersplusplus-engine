package com.checkersplusplus.engine;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.util.BoardUtil;

public class Game {
	private Color currentMove;
    private Board board;

    public boolean isWinner(Color team) {
        Color opponent = team == Color.BLACK ? Color.RED : Color.BLACK;
        boolean opponentFound = false;

        for (int rowCounter = 0; rowCounter < BoardUtil.MAX_ROWS; ++rowCounter) {
            for (int colCounter = 0; colCounter < BoardUtil.MAX_COLS; ++colCounter) {
                if (board.getPiece(rowCounter, colCounter) != null
                        && board.getPiece(rowCounter, colCounter).getColor() == opponent) {
                    opponentFound = true;
                    break;
                }
            }

            if (opponentFound) {
                break;
            }
        }

        return !opponentFound;
    }
}
