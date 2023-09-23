package com.checkersplusplus.engine.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

public class ForwardMoveTest {

	@ParameterizedTest
	@MethodSource("validForwardMoves")
	public void testForwardMove(int startCol, int startRow, int endCol, int endRow, Color color) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		Coordinate end = new Coordinate(endCol, endRow);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), color);
		assertTrue(ForwardMove.isValidForwardMove(board, start, end));
	}
	
	private static Stream<Arguments> validForwardMoves() {
	    return Stream.of(
	      Arguments.of(2, 2, 3, 3, Color.BLACK),
	      Arguments.of(2, 2, 1, 3, Color.BLACK),
	      Arguments.of(1, 5, 0, 4, Color.RED),
	      Arguments.of(1, 5, 2, 4, Color.RED)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("invalidForwardMoves")
	public void testInvalidForwardMove(int startCol, int startRow, int endCol, int endRow, Color color) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		Coordinate end = new Coordinate(endCol, endRow);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), color);
		assertFalse(ForwardMove.isValidForwardMove(board, start, end));
	}
	
	private static Stream<Arguments> invalidForwardMoves() {
	    return Stream.of(
	      Arguments.of(1, 1, 0, 2, Color.BLACK),
	      Arguments.of(1, 1, 2, 2, Color.BLACK),
	      Arguments.of(1, 1, 3, 3, Color.BLACK),
	      Arguments.of(1, 1, 1, 2, Color.BLACK),
	      Arguments.of(2, 6, 1, 5, Color.RED),
	      Arguments.of(2, 6, 3, 5, Color.RED),
	      Arguments.of(2, 6, 4, 4, Color.RED),
	      Arguments.of(2, 6, 2, 5, Color.RED)
	    );
	}
}
