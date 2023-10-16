package com.checkersplusplus.engine;

import java.util.List;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.util.BoardUtil;

public class Game {
	private int currentMove;
    private Board board;
    
    public Game() {
    	board = new Board();
    	currentMove = 0;
    }
    
    public Game(String state) {
    	String[] parts = state.split("\\|");
    	
    	if (parts.length != 2) {
    		throw new IllegalArgumentException("Cannot instantiate game from invalid state");
    	}
    	
    	char[] nextMove = parts[1].toCharArray();
    	
    	try {
    		currentMove = Integer.parseInt(new String(nextMove));
    	} catch (Exception e) {
    		throw new IllegalArgumentException("Cannot instantiate game from invalid state");
    	}
    	
    	board = new Board(parts[0]);
    }
    
    public boolean isMoveLegal(List<CoordinatePair> coordinates) {
    	if (coordinates == null || coordinates.isEmpty()) {
    		return false;
    	}
    	
    	Coordinate startSquare = coordinates.get(0).getStart();
    	Checker piece = board.getPiece(startSquare);
    	
    	if (piece.getColor() != getCurrentMoveColor()) {
    		return false;
    	}
    	
    	return Board.isMoveLegal(board, coordinates);
    }
    
    public void doMove(List<CoordinatePair> coordinates) {
    	board.commitMoves(coordinates);
    	currentMove++;
    }
    
    public String getGameState() {
    	return String.format("%s|%d", board.getBoardState(), currentMove);
    }

    public boolean isWinner(Color team) {
        Color opponent = team == Color.BLACK ? Color.RED : Color.BLACK;
        boolean opponentFound = false;

        for (int rowCounter = 0; rowCounter < BoardUtil.MAX_ROWS; ++rowCounter) {
            for (int colCounter = 0; colCounter < BoardUtil.MAX_COLS; ++colCounter) {
            	Coordinate location = new Coordinate(rowCounter, colCounter);
            	
            	if (board.getPiece(location) != null && board.getPiece(location).getColor() == opponent) {
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

	public Board getBoard() {
		return board;
	}
	
	public Color getCurrentMoveColor() {
		return (currentMove + 1) % 2 == 1 ? Color.BLACK : Color.RED;
	}
	
	public int getCurrentMove() {
		return currentMove;
	}
}
