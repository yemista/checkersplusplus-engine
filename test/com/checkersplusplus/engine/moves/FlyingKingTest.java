package com.checkersplusplus.engine.moves;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;

public class FlyingKingTest {
	//Test methods getCapturedPieceLocation and findObstructionsOnPath
	
	@Test
	public void checkerCannotActAsFlyingKing() {
		Board board = new Board();
		Coordinate start = new Coordinate(0, 0);
		board.clear();
		board.placePiece(new Checker(Color.BLACK), start);
		Coordinate end = new Coordinate(7, 7);
		assertFalse(FlyingKing.isValidFlyingKing(board, start, end));
	}

	@ParameterizedTest
	@MethodSource("validJumps")
	public void testValidFlyingKing(int startCol, int startRow, int endCol, int endRow) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		board.clear();
		board.placePiece(new King(Color.BLACK), start);
		Coordinate end = new Coordinate(endCol, endRow);
		assertTrue(FlyingKing.isValidFlyingKing(board, start, end));
	}
	
	private static Stream<Arguments> validJumps() {
	    return Stream.of(
	      Arguments.of(0, 0, 7, 7),
	      Arguments.of(0, 0, 6, 6),
	      Arguments.of(0, 0, 5, 5),
	      Arguments.of(0, 0, 4, 4),
	      Arguments.of(0, 0, 3, 3),
	      Arguments.of(0, 0, 2, 2),
	      Arguments.of(0, 0, 1, 1),
	      Arguments.of(7, 7, 0, 0),
	      Arguments.of(7, 0, 0, 7),
	      Arguments.of(0, 7, 7, 0),
	      Arguments.of(3, 3, 5, 5)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("invalidJumps")
	public void testInvalidFlyingKing(int startCol, int startRow, int endCol, int endRow) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		board.clear();
		board.placePiece(new King(Color.BLACK), start);
		Coordinate end = new Coordinate(endCol, endRow);
		assertFalse(FlyingKing.isValidFlyingKing(board, start, end));
	}
	
	private static Stream<Arguments> invalidJumps() {
	    return Stream.of(
	      Arguments.of(2, 2, 3, 4),
	      Arguments.of(2, 2, 4, 3),
	      Arguments.of(0, 3, 0, 1),
	      Arguments.of(3, 3, 5, 3)
	    );
	}
}
