package com.checkersplusplus.engine;

import java.util.List;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.moves.Move;
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

    public Board(String state) {
    	board = new Checker[BoardUtil.MAX_ROWS][BoardUtil.MAX_COLS];
    	char[] boardState = state.toCharArray();
    	int boardStateIndex = 0;
    	
    	for (int rowIndex = BoardUtil.MAX_ROWS - 1; rowIndex >= 0; --rowIndex) {
            for (int colIndex = 0; colIndex < BoardUtil.MAX_COLS; ++colIndex) {
                board[rowIndex][colIndex] = boardState[boardStateIndex] == 'E' ? null : new Checker(Color.fromSymbol(boardState[boardStateIndex]));
                boardStateIndex++;
            }
        }
	}

	private void initialize() {
        board = new Checker[BoardUtil.MAX_ROWS][BoardUtil.MAX_COLS];
        BoardUtil.fillRow(board, 0, Color.BLACK);
        BoardUtil.fillRow(board, 1, Color.BLACK);
        BoardUtil.fillRow(board, 2, Color.BLACK);

        BoardUtil.fillRow(board, 7, Color.RED);
        BoardUtil.fillRow(board, 6, Color.RED);
        BoardUtil.fillRow(board, 5, Color.RED);
    }

	public static boolean isMoveLegal(Board board, List<Move> moves) throws Exception {
    	boolean isValid = false;
    	
    	for (Move move : moves) {
	    	isValid = move.isValid();
	    	
	        if (isValid == false) {
	        	return false;
	        }
	        
	        move.commitMove();
	        String updatedBoardState = board.getBoardState();
	        board = new Board(updatedBoardState);
        }
        
        return isValid;
    }
	
    public boolean isOccupied(Coordinate square) {
        return getPiece(square) != null;
    }

    public Checker getPiece(Coordinate square) {
        return board[square.getRow()][square.getCol()];
    }
    
    public void removePiece(Coordinate square) {
    	board[square.getRow()][square.getCol()] = null;
    }
    
    /**
     * Board state is stored as a String. The format is
     * 
     * G(B|R|E)^64
     * 
     * The first character is a G which stands for "game". Then the next 64 characters
     * can either be one of B, for "black", indicating the square is occupied by a black checker, R, indicating the square is occupied
     * by a red checker, or E, indicating the square is empty. The first character after the G corresponds to square (0,0) in the 
     * internal board matrix. The next character is (0,1), and so on and so on.
     */
    public String getBoardState() {
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	for (int rowIndex = BoardUtil.MAX_ROWS - 1; rowIndex >= 0; --rowIndex) {
            for (int colIndex = 0; colIndex < BoardUtil.MAX_COLS; ++colIndex) {
                char cellChar = 'E';

                if (board[rowIndex][colIndex] != null) {
                    cellChar = board[rowIndex][colIndex].getColor() == Color.BLACK ? Color.BLACK.getSymbol() : Color.RED.getSymbol();
                }

                stringBuilder.append(cellChar);
            }
        }
    	
    	return stringBuilder.toString();
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

	public void placePiece(Checker piece, Coordinate square) {
		board[square.getRow()][square.getCol()] = piece;
	}
}
