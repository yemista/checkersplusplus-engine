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
import com.checkersplusplus.engine.pieces.King;

public class CornerJumpTest {

	@ParameterizedTest
	@MethodSource("capturedPieceLocation")
	public void testgetCapturedPieceLocation(int startCol, int startRow, int endCol, int endRow, int capturedPieceCol, int capturedPieceRow) {
		Coordinate pieceStart = new Coordinate(startCol, startRow);
		Coordinate pieceEnd = new Coordinate(endCol, endRow);
		Coordinate capturedPieceLocation = new Coordinate(capturedPieceCol, capturedPieceRow);
		CornerJump cornerJump = new CornerJump(pieceStart, pieceEnd);
		assertEquals(cornerJump.getCapturedPieceLocation(), capturedPieceLocation);
	}
	
	private static Stream<Arguments> capturedPieceLocation() {
	    return Stream.of(
	      Arguments.of(1, 0, 1, 2, 0, 1),
	      Arguments.of(6, 0, 6, 2, 7, 1),
	      Arguments.of(1, 7, 1, 5, 0, 6),
	      Arguments.of(6, 7, 6, 5, 7, 6)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("validJumps")
	public void testValidCornerJump(int startCol, int startRow, int endCol, int endRow, Color color) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		board.clear();
		board.placePiece(new Checker(color), start);
		Coordinate end = new Coordinate(endCol, endRow);
		assertTrue(CornerJump.isValidCornerJump(board, start, end));
	}
	
	private static Stream<Arguments> validJumps() {
	    return Stream.of(
	      Arguments.of(1, 1, 1, 3, Color.BLACK),
	      Arguments.of(6, 1, 6, 3, Color.BLACK),
	      Arguments.of(1, 5, 1, 3, Color.RED),
	      Arguments.of(6, 6, 6, 4, Color.RED)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("invalidJumps")
	public void testInvalidCornerJump(int startCol, int startRow, int endCol, int endRow, Color color) {
		Board board = new Board();
		Coordinate start = new Coordinate(startCol, startRow);
		board.clear();
		board.placePiece(new Checker(color), start);
		Coordinate end = new Coordinate(endCol, endRow);
		assertFalse(CornerJump.isValidCornerJump(board, start, end));
	}
	
	private static Stream<Arguments> invalidJumps() {
		return Stream.of(
		  Arguments.of(0, 1, 0, 3, Color.BLACK),
		  Arguments.of(2, 1, 2, 3, Color.BLACK),
		  Arguments.of(2, 6, 2, 4, Color.RED),
		  Arguments.of(6, 6, 4, 6, Color.RED)
		);
	}
}
