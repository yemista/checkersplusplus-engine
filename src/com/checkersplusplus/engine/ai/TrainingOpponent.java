package com.checkersplusplus.engine.ai;

import java.util.ArrayList;
import java.util.List;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.enums.MoveType;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;
import com.checkersplusplus.engine.util.BoardUtil;
import com.checkersplusplus.engine.util.MoveUtil;

public class TrainingOpponent {

	public static List<Move> getBestMove(String boardState, Color color) {
		Board board = new Board(boardState);
		List<MoveChain> allPossibleMoves = new ArrayList<>();
		
		for (int col = 0; col < BoardUtil.MAX_COLS; ++col) {
			for (int row = 0; row < BoardUtil.MAX_ROWS; ++row) {
				Coordinate pieceLocation = new Coordinate(col, row);
				Checker piece = board.getPiece(pieceLocation);
				
				if (piece != null && piece.getColor() == color) {
					List<MoveChain> moves = generatePossibleMoves(pieceLocation, boardState);
					allPossibleMoves.addAll(moves);
				}
			}
		}
		
		List<List<Move>> specificMoves = new ArrayList<>();
		
		for (MoveChain moveChain : allPossibleMoves) {
			specificMoves.addAll(createSpecificMovesFromMoveChain(moveChain));
		}
		
		List<Move> bestMove = null;
		
		for (List<Move> move : specificMoves) {
			if (bestMove == null) {
				bestMove = move;
			}
			
			if (getMoveScore(move, boardState) > getMoveScore(bestMove, boardState)) {
				bestMove = move;
			}
		}
		
		return bestMove;
	}
	
	private static int getMoveScore(List<Move> bestMove, String boardState) {
		Board board = new Board(boardState);
		int score = 0;
		
		for (Move move : bestMove) {
			score++;
			Checker capture = board.commitMove(move);
			
			if (capture != null) {
				score++;
			}
		}
		
		return score;
	}

	protected static List<List<Move>> createSpecificMovesFromMoveChain(MoveChain moveChain) {
		List<List<Move>> retVal = new ArrayList<>();
		List<Move> work = new ArrayList<>();
		work.add(moveChain.getMove());
		createSpecificMovesFromMoveChainInner(moveChain, work, retVal);
		return retVal;
	}

	private static void createSpecificMovesFromMoveChainInner(MoveChain current, List<Move> work, List<List<Move>> retVal) {
		if (current.getNextMove().isEmpty()) {
			List<Move> moveList = new ArrayList<>();
			moveList.addAll(work);
			retVal.add(moveList);
			work.remove(work.size() - 1);
			return;
		}
		
		for (MoveChain child : current.getNextMove()) {
			 work.add(child.getMove());
			createSpecificMovesFromMoveChainInner(child, work, retVal);
		}
	}

	private static Color getOtherColor(Color color) {
		return color == Color.RED ? Color.BLACK : Color.RED;
	}
	
	private static List<MoveChain> generateInitialMoves(Coordinate pieceLocation, String boardState) {
		List<MoveChain> moveList = new ArrayList<>();
		moveList.addAll(generateForwardMoves(pieceLocation, boardState));
		moveList.addAll(generateJumps(pieceLocation, boardState));
		moveList.addAll(generateRainbowJumps(pieceLocation, boardState));
		moveList.addAll(generateCornerJumps(pieceLocation, boardState));
		moveList.addAll(generateFlyingKings(pieceLocation, boardState, false));
		return moveList;
	}
	
	private static List<MoveChain> generateNextJump(Coordinate pieceLocation, String boardState) {
		List<MoveChain> moveList = new ArrayList<>();
		moveList.addAll(generateJumps(pieceLocation, boardState));
		moveList.addAll(generateRainbowJumps(pieceLocation, boardState));
		moveList.addAll(generateCornerJumps(pieceLocation, boardState));
		moveList.addAll(generateFlyingKings(pieceLocation, boardState, true));
		return moveList;
	}
	
	public static List<MoveChain> generatePossibleMoves(Coordinate pieceLocation, String boardState) {
		List<MoveChain> moveList = generateInitialMoves(pieceLocation, boardState);	
		List<MoveChain> startingNodes = new ArrayList<>();
		startingNodes.addAll(moveList);
		
		while (!moveList.isEmpty()) {
			MoveChain moveChain = moveList.remove(0);
			
			if (!moveChain.isPieceTaken()) {
				continue;
			}
			
			Move move = moveChain.getMove();
			Board board = new Board(moveChain.getBoardState());
			board.commitMove(move);
			String updatedBoardState = board.getBoardState();
			List<MoveChain> childMoves = generateNextJump(move.getEnd(), updatedBoardState);
			moveChain.getNextMove().addAll(childMoves);
			moveList.addAll(childMoves);
		}
		
		return startingNodes;
	}

	private static List<MoveChain> generateFlyingKings(Coordinate pieceLocation, String boardState, boolean mustCapture) {
		List<MoveChain> moves = new ArrayList<>();
		Board board = new Board(boardState);
		Checker piece = board.getPiece(pieceLocation);
		
		if (!(piece instanceof King)) {
			return moves;
		}
		
		for (int col = pieceLocation.getCol(), row = pieceLocation.getRow(); 
				col < BoardUtil.MAX_COLS && row < BoardUtil.MAX_ROWS; ++col, ++row) {
			Coordinate endLocation = new Coordinate(col, row);
			addFlyingKingMoveIfValid(moves, board, pieceLocation, endLocation, mustCapture);
		}
		
		for (int col = pieceLocation.getCol(), row = pieceLocation.getRow(); 
				col >= 0 && row >= 0; --col, --row) {
			Coordinate endLocation = new Coordinate(col, row);
			addFlyingKingMoveIfValid(moves, board, pieceLocation, endLocation, mustCapture);
		}
		
		for (int col = pieceLocation.getCol(), row = pieceLocation.getRow(); 
				col >= 0 && row < BoardUtil.MAX_ROWS; --col, ++row) {
			Coordinate endLocation = new Coordinate(col, row);
			addFlyingKingMoveIfValid(moves, board, pieceLocation, endLocation, mustCapture);
		}
		
		for (int col = pieceLocation.getCol(), row = pieceLocation.getRow(); 
				col < BoardUtil.MAX_COLS && row >= 0; ++col, --row) {
			Coordinate endLocation = new Coordinate(col, row);
			addFlyingKingMoveIfValid(moves, board, pieceLocation, endLocation, mustCapture);
		}
		
		return moves;
	}

	private static void addFlyingKingMoveIfValid(List<MoveChain> moves, Board board, Coordinate pieceLocation, Coordinate endLocation, boolean mustCapture) {
		if (endLocation.equals(pieceLocation)) {
			return;
		}
		
		Move move = MoveUtil.createMove(board, pieceLocation, endLocation);
		Coordinate capturedPieceLocation = move.getCapturedPieceLocation();
		
		if (Board.isMoveLegal(board, move)) {
			return;
		}
		
		if (move.getMoveType() == MoveType.FORWARD_MOVE) {
			moves.add(new MoveChain(move, board.getBoardState()));
			return;
		}
		
		if (mustCapture && capturedPieceLocation != null && board.getPiece(capturedPieceLocation) != null) {
			moves.add(new MoveChain(move, board.getBoardState()));
		} else {
			moves.add(new MoveChain(move, board.getBoardState()));
		}
	}

	private static List<MoveChain> generateCornerJumps(Coordinate pieceLocation, String boardState) {
		List<MoveChain> jumps = new ArrayList<>();
		Board board = new Board(boardState);
		Checker checker = board.getPiece(pieceLocation);
		
		if (checker.getColor() == Color.BLACK) {
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol(), pieceLocation.getRow() + 2));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				jumps.add(new MoveChain(moveLeft, boardState));
			}
		}
		
		if (checker.getColor() == Color.RED) {
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol(), pieceLocation.getRow() - 2));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				jumps.add(new MoveChain(moveLeft, boardState));
			}
		}
		
		return jumps;
	}

	private static List<MoveChain> generateRainbowJumps(Coordinate pieceLocation, String boardState) {
		List<MoveChain> jumps = new ArrayList<>();
		Board board = new Board(boardState);
		Checker checker = board.getPiece(pieceLocation);
		
		if (checker.getColor() == Color.BLACK) {
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() - 4, pieceLocation.getRow()));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				jumps.add(new MoveChain(moveLeft, boardState));
			}
			
			Move moveRight = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() + 4, pieceLocation.getRow()));
			
			if (Board.isMoveLegal(board, moveRight)) {
				jumps.add(new MoveChain(moveRight, boardState));
			}
			
			Move moveUp = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol(), pieceLocation.getRow() + 4));
			
			if (Board.isMoveLegal(board, moveUp)) {
				jumps.add(new MoveChain(moveUp, boardState));
			}
		}
		
		if (checker.getColor() == Color.RED) {
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() - 4, pieceLocation.getRow()));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				jumps.add(new MoveChain(moveLeft, boardState));
			}
			
			Move moveRight = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() + 4, pieceLocation.getRow()));
			
			if (Board.isMoveLegal(board, moveRight)) {
				jumps.add(new MoveChain(moveRight, boardState));
			}
			
			Move moveUp = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol(), pieceLocation.getRow() - 4));
			
			if (Board.isMoveLegal(board, moveUp)) {
				jumps.add(new MoveChain(moveUp, boardState));
			}
		}
		
		return jumps;
	}

	private static List<MoveChain> generateJumps(Coordinate pieceLocation, String boardState) {
		List<MoveChain> jumps = new ArrayList<>();
		Board board = new Board(boardState);
		Checker checker = board.getPiece(pieceLocation);
		
		if (checker.getColor() == Color.BLACK) {
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() - 2, pieceLocation.getRow() + 2));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				jumps.add(new MoveChain(moveLeft, boardState));
			}
			
			Move moveRight = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() + 2, pieceLocation.getRow() + 2));
			
			if (Board.isMoveLegal(board, moveRight)) {
				jumps.add(new MoveChain(moveRight, boardState));
			}
		}
		
		if (checker.getColor() == Color.RED) {
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() - 2, pieceLocation.getRow() - 2));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				jumps.add(new MoveChain(moveLeft, boardState));
			}
			
			Move moveRight = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() + 2, pieceLocation.getRow() - 2));
			
			if (Board.isMoveLegal(board, moveRight)) {
				jumps.add(new MoveChain(moveRight, boardState));
			}
		}
		
		return jumps;
	}

	private static List<MoveChain> generateForwardMoves(Coordinate pieceLocation, String boardState) {
		List<MoveChain> forwardMoves = new ArrayList<>();
		Board board = new Board(boardState);
		Checker checker = board.getPiece(pieceLocation);
		
		if (checker.getColor() == Color.BLACK) {
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() - 1, pieceLocation.getRow() + 1));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				forwardMoves.add(new MoveChain(moveLeft, boardState));
			}
			
			Move moveRight = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() + 1, pieceLocation.getRow() + 1));
			
			if (Board.isMoveLegal(board, moveRight)) {
				forwardMoves.add(new MoveChain(moveRight, boardState));
			}
		}
		
		if (checker.getColor() == Color.RED) {
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() - 1, pieceLocation.getRow() - 1));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				forwardMoves.add(new MoveChain(moveLeft, boardState));
			}
			
			Move moveRight = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() + 1, pieceLocation.getRow() - 1));
			
			if (Board.isMoveLegal(board, moveRight)) {
				forwardMoves.add(new MoveChain(moveRight, boardState));
			}
		}
		
		return forwardMoves;
	}
}
