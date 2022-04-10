package com.checkersplusplus.engine;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.util.BoardUtil;

public class Board {
	private Checker[][] board;

    public Board() {
        initialize();
    }

    protected Board(Checker[][] board) {
        this.board = board;
    }

    private void initialize() {
        board  = new Checker[BoardUtil.MAX_ROWS][BoardUtil.MAX_COLS];
        BoardUtil.fillRow(board, 0, Color.BLACK);
        BoardUtil.fillRow(board, 1, Color.BLACK);
        BoardUtil.fillRow(board, 2, Color.BLACK);

        BoardUtil.fillRow(board, 7, Color.RED);
        BoardUtil.fillRow(board, 6, Color.RED);
        BoardUtil.fillRow(board, 5, Color.RED);
    }

    public Board copyOf() {
        Board copy = new Board();

        for (int rowCounter = 0; rowCounter < BoardUtil.MAX_ROWS; ++rowCounter) {
            for (int colCounter = 0; colCounter < BoardUtil.MAX_COLS; ++colCounter) {
                if (getPiece(rowCounter, colCounter) != null) {
                    copy.board[rowCounter][colCounter] = getPiece(rowCounter, colCounter).copyOf();
                }
            }
        }

        return copy;
    }

//    public void doMove(ServerMove move) {
//        movePiece(move.getMove().getFrom(), move.getMove().getTo());
//
//        for (Coordinate capturedPiece : move.getCapturedPieces()) {
//            board[capturedPiece.getRow()][capturedPiece.getCol()] = null;
//        }
//    }

    private void movePiece(Coordinate from, Coordinate to) {
        Checker piece = board[from.getRow()][from.getCol()];
        board[from.getRow()][from.getCol()] = null;
        board[to.getRow()][to.getCol()] = piece;
    }

    public boolean isMoveLegal(Move move) {
        Coordinate from = move.getFrom();
        Coordinate to = move.getTo();

        if (!BoardUtil.isWithinBounds(from)) {
            return false;
        }

        if (!BoardUtil.isWithinBounds(to)) {
            return false;
        }

        if (!isOccupied(from.getRow(), from.getCol())) {
            return false;
        }

        if (isOccupied(to.getRow(), to.getCol())) {
            return false;
        }

        Checker piece = board[from.getRow()][from.getCol()];

        return piece.isValidMove(from, to)
                    || (piece.isValidJump(from, to) && isPieceCapturedByJump(piece, from, to))
                    || (piece.isValidRainbowJump(from, to) && isPieceCapturedByRainbowJump(piece, from, to))
                    || (piece.isValidCornerJump(from, to) && isPieceCapturedByCornerJump(piece, from, to));
    }

    // TODO
    private boolean isPieceCapturedByCornerJump(Checker piece, Coordinate from, Coordinate to) {
        return false;
    }

    private boolean isPieceCapturedByRainbowJump(Checker piece, Coordinate from, Coordinate to) {
        int colDiff;

        if (to.getCol() > from.getCol()) {
            colDiff = -2;
        } else if (to.getCol() < from.getCol()) {
            colDiff = 2;
        } else {
            colDiff = 0;
        }

        int rowDiff;

        if (to.getRow() > from.getRow()) {
            rowDiff = -2;
        } else if (to.getRow() < from.getRow()) {
            rowDiff = 2;
        } else {
            rowDiff = 0;
        }

        Checker opposingPiece = getPiece(to.getRow() + rowDiff, to.getCol() + colDiff);
        return opposingPiece != null && opposingPiece.getColor() != piece.getColor();
    }

    private Coordinate getPieceCapturedByJumpCoordinate(Coordinate from, Coordinate to) {
        int colDiff;

        if (to.getCol() > from.getCol()) {
            colDiff = -1;
        } else {
            colDiff = 1;
        }

        int rowDiff;

        if (to.getRow() > from.getRow()) {
            rowDiff = -1;
        } else {
            rowDiff = 1;
        }

        return new Coordinate(to.getCol() + colDiff, to.getRow() + rowDiff);
    }

    private boolean isPieceCapturedByJump(Checker piece, Coordinate from, Coordinate to) {
        Coordinate pieceCapturedByJumpCoordinate = getPieceCapturedByJumpCoordinate(from, to);
        Checker pieceCapturedByJump = getPiece(pieceCapturedByJumpCoordinate.getRow(), pieceCapturedByJumpCoordinate.getCol());
        return pieceCapturedByJump != null && pieceCapturedByJump.getColor() != piece.getColor();
    }

    public boolean isOccupied(int row, int col) {
        return getPiece(row, col) != null;
    }

    public Checker getPiece(int row, int col) {
        return board[row][col];
    }

    @Override
    public String toString() {
        String separator = "---------------------------------\n";
        String cellFormat = "| %c ";
        StringBuilder stringBuilder = new StringBuilder();

        for (int rowIndex = BoardUtil.MAX_ROWS - 1; rowIndex >= 0; --rowIndex) {
            stringBuilder.append(separator);

            for (int colIndex = 0; colIndex < BoardUtil.MAX_COLS; ++colIndex) {
                char cellChar = ' ';

                if (board[rowIndex][colIndex] != null) {
                    cellChar = board[rowIndex][colIndex].getColor() == Color.BLACK ? 'X' : 'O';
                }

                stringBuilder.append(String.format(cellFormat, cellChar));
            }

            stringBuilder.append("|\n");
        }

        stringBuilder.append(separator);
        return stringBuilder.toString();
    }
}
