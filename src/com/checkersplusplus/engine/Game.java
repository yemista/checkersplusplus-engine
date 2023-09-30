package com.checkersplusplus.engine;

import java.util.List;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.util.BoardUtil;

public class Game {
	private Color currentMove;
    private Board board;
    private Color winner;
    
    public Game() {
    	board = new Board();
    	currentMove = Color.BLACK;
    }
    
    public Game(String state) {
    	String[] parts = state.split("\\|");
    	
    	if (parts.length != 2) {
    		throw new IllegalArgumentException("Cannot instantiate game from invalid state");
    	}
    	
    	char[] nextMove = parts[0].toCharArray();
    	
    	if (nextMove[0] == 'W') {
    		winner = Color.fromSymbol(nextMove[1]);
    	} else if (nextMove[0] == 'N') {
    		currentMove = Color.fromSymbol(nextMove[1]);
    	} else {
    		throw new IllegalArgumentException("Cannot instantiate game from invalid state");
    	}
    	
    	board = new Board(parts[1]);
    }
    
    public boolean isMoveLegal(List<CoordinatePair> coordinates) {
    	if (coordinates == null || coordinates.isEmpty()) {
    		return false;
    	}
    	
    	Coordinate startSquare = coordinates.get(0).getStart();
    	Checker piece = board.getPiece(startSquare);
    	
    	if (piece.getColor() != currentMove) {
    		return false;
    	}
    	
    	return board.isMoveLegal(board, coordinates);
    }
    
    public String getGameState() {
    	if (winner != null) {
    		return String.format("%c%c|%s", 'W', winner.getSymbol(), board.getBoardState());
    	}
    	
    	return String.format("%c%c|%s", 'N', currentMove.getSymbol(), board.getBoardState());
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
}
