package com.checkersplusplus.engine.ai;

import java.util.ArrayList;
import java.util.List;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.moves.Move;

public class MoveChain {
	
	// Move to make in this node.
	private Move move;
	
	// Board state before the move is made
	private String boardState;
	
	// All possible child moves
	private List<MoveChain> nextMove = new ArrayList<>();
	
	private boolean pieceTaken;
	
	public MoveChain(Move move, String state) {
		this.move = move;
		this.boardState = state;
		Board board = new Board(boardState);
		pieceTaken = board.commitMove(move) != null;
	}

	public Move getMove() {
		return move;
	}

	public String getBoardState() {
		return boardState;
	}

	public List<MoveChain> getNextMove() {
		return nextMove;
	}
	
	public boolean isPieceTaken() {
		return pieceTaken;
	}
}
