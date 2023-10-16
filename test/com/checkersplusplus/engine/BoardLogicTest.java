package com.checkersplusplus.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;

/**
 * Purpose of this class is to test complicated move scenarios for the method isMoveLegal()
 */
public class BoardLogicTest {
	
	@Test
	public void testFlyingKingMovingTwiceWithoutCapture() {
		Board board = new Board();
		board.clear();
		board.placePiece(new King(Color.BLACK), new Coordinate(0, 0));
		List<CoordinatePair> moves = Arrays.asList(
				new CoordinatePair(new Coordinate(0, 0), new Coordinate(5, 5)),
				new CoordinatePair(new Coordinate(5, 5), new Coordinate(3, 7))
			);
		assertFalse(Board.isMoveLegal(board, moves));
	}
	
	@Test
	public void testMoveToJump() {
		Board board = new Board();
		board.clear();
		board.placePiece(new Checker(Color.BLACK), new Coordinate(0, 0));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 2));
		List<CoordinatePair> moves = Arrays.asList(
				new CoordinatePair(new Coordinate(0, 0), new Coordinate(1, 1)),
				new CoordinatePair(new Coordinate(1, 1), new Coordinate(3, 3))
			);
		assertFalse(Board.isMoveLegal(board, moves));
	}

	@Test
	public void testCornerToJumpToHorizontalRainbow() {
		Board board = new Board();
		board.clear();
		board.placePiece(new Checker(Color.BLACK), new Coordinate(6, 2));
		board.placePiece(new Checker(Color.RED), new Coordinate(7, 3));
		board.placePiece(new Checker(Color.RED), new Coordinate(5, 5));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 6));
		List<CoordinatePair> moves = Arrays.asList(
					new CoordinatePair(new Coordinate(6, 2), new Coordinate(6, 4)),
					new CoordinatePair(new Coordinate(6, 4), new Coordinate(4, 6)),
					new CoordinatePair(new Coordinate(4, 6), new Coordinate(0, 6))
				);
		assertTrue(Board.isMoveLegal(board, moves));
	}
	
	@Test
	public void testTripleJump() {
		Board board = new Board();
		board.clear();
		board.placePiece(new Checker(Color.BLACK), new Coordinate(0, 0));
		board.placePiece(new Checker(Color.RED), new Coordinate(1, 1));
		board.placePiece(new Checker(Color.RED), new Coordinate(1, 3));
		board.placePiece(new Checker(Color.RED), new Coordinate(1, 5));
		List<CoordinatePair> moves = Arrays.asList(
					new CoordinatePair(new Coordinate(0, 0), new Coordinate(2, 2)),
					new CoordinatePair(new Coordinate(2, 2), new Coordinate(0, 4)),
					new CoordinatePair(new Coordinate(0, 4), new Coordinate(2, 6))
				);
		assertTrue(Board.isMoveLegal(board, moves));
	}
	
	@Test
	public void testTripleJumpToKingToFlyingKing() {
		Board board = new Board();
		board.clear();
		board.placePiece(new Checker(Color.BLACK), new Coordinate(1, 1));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 2));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 4));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 6));
		board.placePiece(new Checker(Color.RED), new Coordinate(6, 4));
		List<CoordinatePair> moves = Arrays.asList(
					new CoordinatePair(new Coordinate(1, 1), new Coordinate(3, 3)),
					new CoordinatePair(new Coordinate(3, 3), new Coordinate(1, 5)),
					new CoordinatePair(new Coordinate(1, 5), new Coordinate(3, 7)),
					new CoordinatePair(new Coordinate(3, 7), new Coordinate(7, 3))
				);
		assertTrue(Board.isMoveLegal(board, moves));
	}
}
