package com.checkersplusplus.engine.util;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;

public class BoardUtil {
	public static final int MAX_ROWS = 8;
    public static final int MAX_COLS = 8;

    public static void fillRow(Checker[][] board, int row, Color color) {
        int col = row % 2 == 1 ? 1 : 0;

        for (int colIndex = col; colIndex < MAX_COLS; colIndex += 2) {
            board[row][colIndex] = new Checker(color);
        }
    }

    public static boolean isWithinBounds(Coordinate coordinate) {
        if (coordinate.getRow() >= MAX_ROWS || coordinate.getRow() < 0) {
            return false;
        }

        if (coordinate.getCol() >= MAX_COLS || coordinate.getCol() < 0) {
            return false;
        }

        return true;
    }
}
