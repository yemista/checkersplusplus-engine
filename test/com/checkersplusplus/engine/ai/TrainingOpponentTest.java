package com.checkersplusplus.engine.ai;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
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
		assertEquals(moves.get(0).getNextMove().get(0).getNextMove().size(), 1);
		assertEquals(moves.get(1).getNextMove().get(0).getNextMove().size(), 1);
	}
}
