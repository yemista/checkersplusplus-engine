package com.checkersplusplus.engine.ai;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.pieces.Checker;

public class TrainingOpponentTest {

	@Test
	public void generatePossibleMovesTest() {
		Board board = new Board();
		board.clear();
		board.placePiece(new Checker(Color.BLACK), new Coordinate(2, 1));
		board.placePiece(new Checker(Color.RED), new Coordinate(1, 2));
		board.placePiece(new Checker(Color.RED), new Coordinate(1, 4));
		board.placePiece(new Checker(Color.RED), new Coordinate(3, 2));
		board.placePiece(new Checker(Color.RED), new Coordinate(3, 4));
		List<MoveChain> moves = TrainingOpponent.generatePossibleMoves(new Coordinate(2, 1), board.getBoardState());
		assertEquals(moves.size(), 2);
		assertEquals(moves.get(0).getNextMove().size(), 1);
		assertEquals(moves.get(1).getNextMove().size(), 1);
	}
	
	@Test
	public void createSpecificMovesFromMoveChainTest() {
		Board board = new Board();
		board.clear();
		board.placePiece(new Checker(Color.BLACK), new Coordinate(1, 1));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 2));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 4));
		List<MoveChain> moveChains = TrainingOpponent.generatePossibleMoves(new Coordinate(1, 1), board.getBoardState());
		assertEquals(moveChains.size(), 2);
		List<List<Move>> firstMoveChainMoves = TrainingOpponent.createSpecificMovesFromMoveChain(moveChains.get(0));
		assertEquals(firstMoveChainMoves.size(), 1);
		assertEquals(firstMoveChainMoves.get(0).size(), 1);
		List<List<Move>> secondMoveChainMoves = TrainingOpponent.createSpecificMovesFromMoveChain(moveChains.get(1));
		assertEquals(secondMoveChainMoves.size(), 1);
		assertEquals(secondMoveChainMoves.get(0).size(), 2);
	}
	
	@Test
	public void getBestMoveTest() {
		Board board = new Board();
		board.clear();
		board.placePiece(new Checker(Color.BLACK), new Coordinate(1, 1));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 2));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 4));
		List<Move> bestMove = TrainingOpponent.getBestMove(board.getBoardState(), Color.BLACK);
		assertEquals(bestMove.size(), 2);
	}
}
