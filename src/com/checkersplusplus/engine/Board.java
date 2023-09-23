package com.checkersplusplus.engine;

import java.util.List;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.moves.FlyingKing;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;
import com.checkersplusplus.engine.util.BoardUtil;

public class Board {
	private Checker[][] board;

    public Board() {
        initialize();
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
    
    /**
     * Clears all pieces off the board. Should only be used for testing.
     */
    public void clear() {
    	for (int rowIndex = BoardUtil.MAX_ROWS - 1; rowIndex >= 0; --rowIndex) {
            for (int colIndex = 0; colIndex < BoardUtil.MAX_COLS; ++colIndex) {
                board[rowIndex][colIndex] = null;
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
    	if (!validateMovesAreConnected(moves)) {
    		return false;
    	}
    	
		boolean isValid = false;
    	Board workingBoard = new Board(board.getBoardState());
    	
    	for (int moveNum = 0; moveNum < moves.size(); ++moveNum) {
    		Move move = moves.get(moveNum);
	    	isValid = move.isValidMoveType();
	    	
	        if (isValid == false) {
	        	return false;
	        }
	        
	        Checker capturedPiece = workingBoard.commitMove(move);
	        
	        // King cannot move again unless it captures a piece.
	        if (move.getMoveType() == MoveType.FLYING_KING) {
	        	FlyingKing flyingKingMove = (FlyingKing) move;
	        	
	        	if (flyingKingMove.findObstructionsOnPath(workingBoard)) {
	        		return false;
	        	}
	        	
	        	if (capturedPiece == null && moveNum != moves.size() - 1) {
	        		return false;
	        	}
	        } 
	        
	        if (moveMustCapturePiece(move) && capturedPiece == null) {
		        return false;	
	        }
	        
	        String updatedBoardState = workingBoard.getBoardState();
	        workingBoard = new Board(updatedBoardState);
        }
        
        return isValid;
    }
	
    public static boolean validateMovesAreConnected(List<Move> moves) {
		Coordinate lastEnd = null;
		
		for (Move move : moves) {
			if (lastEnd != null && !move.getStart().equals(lastEnd)) {
				return false;
			}
			
			lastEnd = move.getEnd();
		}
		
		return true;
	}

	private static boolean moveMustCapturePiece(Move move) {
    	return move.getMoveType() == MoveType.JUMP ||
        		move.getMoveType() == MoveType.RAINBOW_JUMP ||
        		move.getMoveType() == MoveType.CORNER_JUMP;
	}

	private Checker commitMove(Move move) {
		Checker playerPiece = getPiece(move.getStart());
		removePiece(move.getStart());
		placePiece(playerPiece, move.getEnd());
		Coordinate opponentLocation = move.getCapturedPieceLocation();	
		Checker capturedPiece = getPiece(opponentLocation);
		
		if (opponentLocation != null) {
			removePiece(opponentLocation);
		}
		
		return capturedPiece;
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
