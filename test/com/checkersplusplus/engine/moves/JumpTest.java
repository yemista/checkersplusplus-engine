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

public class JumpTest {

	@ParameterizedTest
	@MethodSource("capturedPieceLocation")
	public void testgetCapturedPieceLocation(int startCol, int startRow, int endCol, int endRow, int capturedPieceCol, int capturedPieceRow) {
		Coordinate pieceStart = new Coordinate(startCol, startRow);
		Coordinate pieceEnd = new Coordinate(endCol, endRow);
		Coordinate capturedPieceLocation = new Coordinate(capturedPieceCol, capturedPieceRow);
		Jump jump = new Jump(pieceStart, pieceEnd);
		assertEquals(jump.getCapturedPieceLocation(), capturedPieceLocation);
	}
	
	private static Stream<Arguments> capturedPieceLocation() {
	    return Stream.of(
	      Arguments.of(1, 1, 3, 3, 2, 2),
	      Arguments.of(3, 3, 1, 5, 2, 4),
	      Arguments.of(7, 7, 5, 5, 6, 6),
	      Arguments.of(5, 5, 7, 3, 6, 4)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("validJumps")
	public void testValidJump(int startCol, int startRow, int endCol, int endRow) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		Coordinate end = new Coordinate(endCol, endRow);
		assertTrue(Jump.isValidJump(board, start, end));
	}
	
	private static Stream<Arguments> validJumps() {
	    return Stream.of(
	      Arguments.of(0, 2, 2, 4),
	      Arguments.of(2, 2, 0, 4),
	      Arguments.of(3, 5, 1, 3),
	      Arguments.of(3, 5, 5, 3)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("invalidJumps")
	public void testInvalidJump(int startCol, int startRow, int endCol, int endRow) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		Coordinate end = new Coordinate(endCol, endRow);
		assertFalse(Jump.isValidJump(board, start, end));
	}
	
	private static Stream<Arguments> invalidJumps() {
	    return Stream.of(
	      Arguments.of(2, 2, 3, 3),
	      Arguments.of(2, 2, 1, 3),
	      Arguments.of(3, 3, 1, 4)
	    );
	}
}
