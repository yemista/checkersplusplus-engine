package com.checkersplusplus.engine.ai;

import java.util.ArrayList;
import java.util.List;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;
import com.checkersplusplus.engine.util.BoardUtil;
import com.checkersplusplus.engine.util.MoveUtil;

public class TrainingOpponent {

//	public static List<Move> getOpponentMove(String boardState, Color color) {
//		Board board = new Board(boardState);
//		List<Move> allPossibleMoves = new ArrayList<>();
//		
//		for (int col = 0; col < BoardUtil.MAX_COLS; ++col) {
//			for (int row = 0; row < BoardUtil.MAX_ROWS; ++row) {
//				Coordinate pieceLocation = new Coordinate(col, row);
//				Checker piece = board.getPiece(pieceLocation);
//				
//				if (piece != null && piece.getColor() == color) {
//					List<Move> moves = generatePossibleMoves(pieceLocation, boardState);
//					allPossibleMoves.addAll(moves);
//				}
//			}
//		}
//		
//		return Collections.emptyList();
//	}
//	
//	public static List<GameStateNode> constructMoveTree(String boardState, Color color, int depth) {
//		List<GameStateNode> rootNodes = generateLevel(boardState, color);
//		List<GameStateNode> queue = new ArrayList<>();
//		queue.addAll(rootNodes);
//		
//		for (int level = 1; level < depth; ++level) {
//			List<GameStateNode> newChildren = new ArrayList<>();
//			
//			while (!queue.isEmpty()) {
//				GameStateNode node = queue.remove(0);
//				List<GameStateNode> children = generateLevel(node.getBoardState(), getOtherColor(node.getColor()));
//				node.getChildren().addAll(children);
//				newChildren.addAll(children);
//			}
//		}
//				
//		return rootNodes;
//	}
	
//	private static List<GameStateNode> generateLevel(String boardState, Color color) {
//		List<GameStateNode> levelOfNodes = new ArrayList<>();
//		
//		for (int col = 0; col < BoardUtil.MAX_COLS; ++col) {
//			for (int row = 0; row < BoardUtil.MAX_ROWS; ++row) {
//				Board board = new Board(boardState);
//				Coordinate pieceLocation = new Coordinate(col, row);
//				Checker piece = board.getPiece(pieceLocation);
//				
//				if (piece != null && piece.getColor() == color) {
//					List<AiMove> moves = generatePossibleMoves(pieceLocation, boardState);
//					
//					for (AiMove move : moves) {
//						Board boardWithMove = new Board(boardState);
//						boardWithMove.commitMove(move);
//						GameStateNode node = new GameStateNode(boardWithMove.getBoardState(), move, color);
//						levelOfNodes.add(node);
//					}
//				}
//			}
//		}
//		
//		return levelOfNodes;
//	}
	
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
		
		for (int col = pieceLocation.getCol(); col < BoardUtil.MAX_COLS; ++col) {
			for (int row = pieceLocation.getRow(); row < BoardUtil.MAX_ROWS; ++row) {
				Coordinate endLocation = new Coordinate(col, row);
				addFlyingKingMoveIfValid(moves, board, pieceLocation, endLocation, mustCapture);
			}
		}
		
		for (int col = pieceLocation.getCol(); col >= 0; --col) {
			for (int row = pieceLocation.getRow(); row >= 0; --row) {
				Coordinate endLocation = new Coordinate(col, row);
				addFlyingKingMoveIfValid(moves, board, pieceLocation, endLocation, mustCapture);
			}
		}
		
		for (int col = pieceLocation.getCol(); col >= 0; --col) {
			for (int row = pieceLocation.getRow(); row < BoardUtil.MAX_ROWS; ++row) {
				Coordinate endLocation = new Coordinate(col, row);
				addFlyingKingMoveIfValid(moves, board, pieceLocation, endLocation, mustCapture);
			}
		}
		
		for (int col = pieceLocation.getCol(); col < BoardUtil.MAX_COLS; ++col) {
			for (int row = pieceLocation.getRow(); row >= 0; --row) {
				Coordinate endLocation = new Coordinate(col, row);
				addFlyingKingMoveIfValid(moves, board, pieceLocation, endLocation, mustCapture);
			}
		}
		
		return moves;
	}

	private static void addFlyingKingMoveIfValid(List<MoveChain> moves, Board board, Coordinate pieceLocation, Coordinate endLocation, boolean mustCapture) {
		if (endLocation.equals(pieceLocation)) {
			return;
		}
		
		Move move = MoveUtil.createMove(board, pieceLocation, endLocation);
		Coordinate capturedPieceLocation = move.getCapturedPieceLocation();
		
		if (mustCapture && board.getPiece(capturedPieceLocation) == null) {
			return;
		}
		
		if (Board.isMoveLegal(board, move)) {
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
			Move moveLeft = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() - 2, pieceLocation.getRow() - 2));
			
			if (Board.isMoveLegal(board, moveLeft)) {
				jumps.add(new MoveChain(moveLeft, boardState));
			}
			
			Move moveRight = MoveUtil.createMove(board, pieceLocation, new Coordinate(pieceLocation.getCol() + 2, pieceLocation.getRow() - 2));
			
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
