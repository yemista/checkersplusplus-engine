package com.checkersplusplus.engine.ai;

import java.util.ArrayList;
import java.util.List;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.pieces.Checker;

public class GameStateNode {
	private List<GameStateNode> children = new ArrayList<>();
	private boolean pieceTaken = false;
	private List<Move> move;
	private String boardState;
	private Color color;
	
	public GameStateNode(String boardState, List<Move> move, Color color) {
		this.boardState = boardState;
		this.move = move;
		this.color = color;
		Board board = new Board(boardState);
		
		if (move != null) {
			Checker checker = board.commitMove(move);
			pieceTaken = checker != null;
		}
	}

	public List<GameStateNode> getChildren() {
		return children;
	}

	public boolean isPieceTaken() {
		return pieceTaken;
	}

	public List<Move> getMove() {
		return move;
	}

	public String getBoardState() {
		return boardState;
	}

	public Color getColor() {
		return color;
	}
}
