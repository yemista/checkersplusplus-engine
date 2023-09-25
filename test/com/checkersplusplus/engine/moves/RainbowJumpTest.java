package com.checkersplusplus.engine.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;

public class RainbowJumpTest {
	
	@ParameterizedTest
	@MethodSource("capturedPieceLocation")
	public void testgetCapturedPieceLocation(int startCol, int startRow, int endCol, int endRow, int capturedPieceCol, int capturedPieceRow) {
		Coordinate pieceStart = new Coordinate(startCol, startRow);
		Coordinate pieceEnd = new Coordinate(endCol, endRow);
		Coordinate capturedPieceLocation = new Coordinate(capturedPieceCol, capturedPieceRow);
		RainbowJump rainbowJump = new RainbowJump(pieceStart, pieceEnd);
		assertEquals(rainbowJump.getCapturedPieceLocation(), capturedPieceLocation);
	}
	
	private static Stream<Arguments> capturedPieceLocation() {
	    return Stream.of(
	      Arguments.of(1, 1, 1, 5, 1, 3),
	      Arguments.of(1, 3, 5, 3, 3, 3),
	      Arguments.of(5, 3, 1, 3, 3, 3),
	      Arguments.of(7, 7, 7, 3, 7, 5)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("validJumps")
	public void testValidRainbowJump(int startCol, int startRow, int endCol, int endRow, Color color) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		board.clear();
		board.placePiece(new Checker(color), start);
		Coordinate end = new Coordinate(endCol, endRow);
		assertTrue(RainbowJump.isValidRainbowJump(start, end));
	}
	
	private static Stream<Arguments> validJumps() {
	    return Stream.of(
	      Arguments.of(2, 2, 2, 6, Color.BLACK),
	      Arguments.of(0, 2, 4, 2, Color.BLACK),
	      Arguments.of(4, 2, 0, 2, Color.BLACK),
	      Arguments.of(1, 6, 1, 2, Color.RED),
	      Arguments.of(1, 6, 5, 6, Color.RED),
	      Arguments.of(5, 6, 1, 6, Color.RED)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("invalidJumps")
	public void testInvalidRainbowJump(int startCol, int startRow, int endCol, int endRow, Color color) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		board.clear();
		board.placePiece(new Checker(color), start);
		Coordinate end = new Coordinate(endCol, endRow);
		assertFalse(RainbowJump.isValidRainbowJump(start, end));
	}
	
	private static Stream<Arguments> invalidJumps() {
	    return Stream.of(
	      Arguments.of(2, 2, 2, 5, Color.RED),
	  	  Arguments.of(1, 6, 4, 6, Color.RED),
	  	  Arguments.of(5, 6, 2, 6, Color.RED),
	  	  Arguments.of(0, 2, 3, 2, Color.BLACK),
	  	  Arguments.of(4, 2, 1, 2, Color.BLACK),
	  	  Arguments.of(1, 6, 1, 3, Color.BLACK)
	    );
	}
}
