package com.checkersplusplus.engine.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.util.Pair;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;
import com.checkersplusplus.engine.util.BoardUtil;

public class TrainingOpponent {

	public static List<Move> getOpponentMove(String boardState, Color color) {
		Board board = new Board(boardState);
		
		for (int col = 0; col < BoardUtil.MAX_COLS; ++col) {
			for (int row = 0; row < BoardUtil.MAX_ROWS; ++row) {
				Coordinate pieceLocation = new Coordinate(col, row);
				Checker piece = board.getPiece(pieceLocation);
				
				if (piece != null && piece.getColor() == color) {
					Pair<Integer, List<Move>> bestOpponentResponse = getBestPossibleMove(pieceLocation, boardState);
				}
			}
		}
		
		return Collections.emptyList();
	}

	private static Pair<Integer, List<Move>> getBestPossibleMove(Coordinate pieceLocation, String boardState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static List<BestMoveNode> generatePossibleMoves(Coordinate pieceLocation, String boardState) {
		List<BestMoveNode> moveList = new ArrayList<>();
		moveList.addAll(generateForwardMoves(pieceLocation, boardState));
		moveList.addAll(generateJumps(pieceLocation, boardState));
		moveList.addAll(generateRainbowJumps(pieceLocation, boardState));
		moveList.addAll(generateCornerJumps(pieceLocation, boardState));
		
		Board board = new Board(boardState);
		
		if (board.getPiece(pieceLocation) instanceof King) {
			moveList.addAll(generateFlyingKings(pieceLocation, boardState));
		}
		
		return moveList;
	}

	private static List<BestMoveNode> generateFlyingKings(Coordinate pieceLocation, String boardState) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<BestMoveNode> generateCornerJumps(Coordinate pieceLocation, String boardState) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<BestMoveNode> generateRainbowJumps(Coordinate pieceLocation, String boardState) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<BestMoveNode> generateJumps(Coordinate pieceLocation, String boardState) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<BestMoveNode> generateForwardMoves(Coordinate pieceLocation, String boardState) {
		List<BestMoveNode> forwardMoves = new ArrayList<>();
		Board board = new Board(boardState);
		Checker checker = board.getPiece(pieceLocation);
		
		if (checker.getColor() == Color.BLACK) {
			if (pieceLocation.getCol() > 0) {
				
			}
		}
	}
}
