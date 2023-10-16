package com.checkersplusplus.engine;

import java.util.ArrayList;
import java.util.List;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.moves.FlyingKing;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;
import com.checkersplusplus.engine.util.BoardUtil;
import com.checkersplusplus.engine.util.MoveUtil;

public class Board {
	private Checker[][] board;

    public Board() {
        initialize();
    }

    public Board(String state) {
    	board = new Checker[BoardUtil.MAX_ROWS][BoardUtil.MAX_COLS];
    	updateBoardState(state);
	}
    
    private void updateBoardState(String state) {
    	char[] boardState = state.toCharArray();
    	int boardStateIndex = 0;
    	
    	for (int rowIndex = BoardUtil.MAX_ROWS - 1; rowIndex >= 0; --rowIndex) {
            for (int colIndex = 0; colIndex < BoardUtil.MAX_COLS; ++colIndex) {
                if (boardState[boardStateIndex] == 'E') {
                	board[rowIndex][colIndex] = null;
                } else if (Character.isLowerCase(boardState[boardStateIndex])) {
                	board[rowIndex][colIndex] = new King(Color.fromSymbol(boardState[boardStateIndex]));
                } else {
                	board[rowIndex][colIndex] = new Checker(Color.fromSymbol(boardState[boardStateIndex]));
                }
                
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

	public static boolean isMoveLegal(Board board, List<CoordinatePair> moveCoordinates) {
    	if (!validateMovesAreConnected(moveCoordinates)) {
    		return false;
    	}
    	
    	Board workingBoard = new Board(board.getBoardState());
    	List<Checker> capturedPieces = new ArrayList<>();
    	
    	// Special case for flying king. If there is only one move that move can be a flying king
    	// and it does not have to capture a piece. Otherwise if it is a chain of jumps with a flying
    	// king, it must capture a piece to continue the chain.
    	if (moveCoordinates.size() == 1) {
    		CoordinatePair moveCoordinate = moveCoordinates.get(0);
    		Move move = MoveUtil.createMove(workingBoard, moveCoordinate.getStart(), moveCoordinate.getEnd());

			if (!validateMove(move, workingBoard)) {
				return false;
			}
			
			if (moveMustCapturePiece(move) && workingBoard.commitMove(move) == null) {
		        return false;	
	        }
			
			return true;
    	}
    	
    	for (CoordinatePair moveCoordinate : moveCoordinates) {
    		Move move = MoveUtil.createMove(workingBoard, moveCoordinate.getStart(), moveCoordinate.getEnd());
    		
    		if (move.getMoveType() == MoveType.FORWARD_MOVE) {
    			return false;
    		}
    		
    		if (!validateMove(move, workingBoard)) {
    			return false;
    		}
    		
    		Checker capturedPiece = workingBoard.commitMove(move);
            
            if (capturedPiece != null) {
            	capturedPieces.add(capturedPiece);
            }
            
            if (capturedPiece == null) {
            	// King cannot fly again unless it captures a piece.
            	if (move.getMoveType() == MoveType.FLYING_KING) {
            		return false;
            	}
            	
            	// Jump, rainbow jump, and corner jump must capture a piece
            	if (moveMustCapturePiece(move)) {
    		        return false;	
    	        }
            }
	        
	        String updatedBoardState = workingBoard.getBoardState();
	        workingBoard = new Board(updatedBoardState);
        }
    	
    	if (capturedPieces.size() > 0 && capturedPieces.size() != moveCoordinates.size()) {
    		return false;
    	}
        
        return true;
    }
	
	private static boolean validateMove(Move move, Board workingBoard) {
		if (!move.isValidMoveType()) {
        	return false;
        }
		
		if (!MoveUtil.commonValidation(workingBoard, move)) {
			return false;
		}
        
        if (isMoveInWrongDirection(workingBoard, move)) {
        	return false;
        }
        
        if (move.getMoveType() == MoveType.FLYING_KING) {
        	FlyingKing flyingKing = (FlyingKing) move;
        	
        	if (flyingKing.findObstructionsOnPath(workingBoard)) {
        		return false;
        	}
        }
        
        return true;
	}

	/**
	 * Check if piece moves in correct direction based on color. Black pieces move up while red move down.
	 * If the piece is a King, it can go in any direction.
	 */
    public static boolean isMoveInWrongDirection(Board board, Move move) {
		Checker piece = board.getPiece(move.getStart());
    	
		if (piece instanceof King) {
			return false;
		}
		
		// Special case where we have a horizontal rainbow jump
		if (move.getMoveType() == MoveType.RAINBOW_JUMP && move.getStart().getRow() == move.getEnd().getRow()) {
			return false;
		}
		
		if (piece.getColor() == Color.BLACK) {
			return move.getEnd().getRow() <= move.getStart().getRow();
		}
		
		if (piece.getColor() == Color.RED) {
			return move.getEnd().getRow() >= move.getStart().getRow();
		}
		
    	return true;
	}

	public static boolean validateMovesAreConnected(List<CoordinatePair> moves) {
		Coordinate lastEnd = null;
		
		for (CoordinatePair move : moves) {
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
	
	public void commitMoves(List<CoordinatePair> coordinates) {
		for (CoordinatePair coordinatePair : coordinates) {
			Board workingBoard = new Board(getBoardState());
			Move move = MoveUtil.createMove(workingBoard, coordinatePair.getStart(), coordinatePair.getEnd());
			workingBoard.commitMove(move);
			updateBoardState(workingBoard.getBoardState());
		}
	}

	private Checker commitMove(Move move) {
		Checker playerPiece = getPiece(move.getStart());
		removePiece(move.getStart());
		
		if (playerPiece.getColor() == Color.BLACK && move.getEnd().getRow() == 7) {
			placePiece(new King(Color.BLACK), move.getEnd());
		} else if (playerPiece.getColor() == Color.RED && move.getEnd().getRow() == 0) {
			placePiece(new King(Color.RED), move.getEnd());
		} else {
			placePiece(playerPiece, move.getEnd());
		}
		
		Coordinate opponentLocation = move.getCapturedPieceLocation();	
		Checker capturedPiece = null;
		
		if (opponentLocation != null) {
			capturedPiece = getPiece(opponentLocation);
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
                	if (board[rowIndex][colIndex] instanceof King) {
                		cellChar = board[rowIndex][colIndex].getColor() == Color.BLACK ? Color.BLACK.getKingSymbol() : Color.RED.getKingSymbol();
                	} else {
                		cellChar = board[rowIndex][colIndex].getColor() == Color.BLACK ? Color.BLACK.getSymbol() : Color.RED.getSymbol();
                	}
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
