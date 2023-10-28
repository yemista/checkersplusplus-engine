package com.checkersplusplus.engine.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.CoordinatePair;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;

public class FlyingKingTest {
	
	@ParameterizedTest
	@MethodSource("capturedPieceLocation")
	public void testgetCapturedPieceLocation(int startCol, int startRow, int endCol, int endRow, int capturedPieceCol, int capturedPieceRow) {
		Coordinate pieceStart = new Coordinate(startCol, startRow);
		Coordinate pieceEnd = new Coordinate(endCol, endRow);
		Coordinate capturedPieceLocation = new Coordinate(capturedPieceCol, capturedPieceRow);
		FlyingKing flyingKing = new FlyingKing(pieceStart, pieceEnd);
		assertEquals(flyingKing.getCapturedPieceLocation(), capturedPieceLocation);
	}
	
	private static Stream<Arguments> capturedPieceLocation() {
	    return Stream.of(
	      Arguments.of(0, 0, 7, 7, 6, 6),
	      Arguments.of(0, 0, 6, 6, 5, 5),
	      Arguments.of(7, 7, 0, 0, 1, 1),
	      Arguments.of(6, 6, 0, 0, 1, 1),
	      Arguments.of(0, 7, 7, 0, 6, 1),
	      Arguments.of(0, 7, 6, 1, 5, 2),
	      Arguments.of(7, 0, 0, 7, 1, 6),
	      Arguments.of(7, 0, 1, 6, 2, 5)
	    );
	}
	
	@ParameterizedTest
	@MethodSource("pathObstructions")
	public void testFindObstructionsOnPath(int startCol, int startRow, int endCol, int endRow, int obstructionCol, int obstructionRow, boolean obstructionExists) {
		Coordinate pieceStart = new Coordinate(startCol, startRow);
		Coordinate pieceEnd = new Coordinate(endCol, endRow);
		Coordinate opponentStart = new Coordinate(obstructionCol, obstructionRow);
		FlyingKing flyingKing = new FlyingKing(pieceStart, pieceEnd);
		Board board = new Board();
		board.clear();
		board.placePiece(new King(Color.BLACK), pieceStart);
		board.placePiece(new Checker(Color.RED), opponentStart);
		assertEquals(flyingKing.findObstructionsOnPath(board), obstructionExists);
	}
	
	private static Stream<Arguments> pathObstructions() {
	    return Stream.of(
	      Arguments.of(0, 0, 7, 7, 6, 6, false),
	      Arguments.of(0, 0, 7, 7, 5, 4, false),
	      Arguments.of(7, 7, 0, 0, 1, 1, false),
	      Arguments.of(7, 7, 0, 0, 5, 4, false),
	      Arguments.of(0, 7, 7, 0, 6, 1, false),
	      Arguments.of(0, 7, 7, 0, 5, 5, false),
	      Arguments.of(7, 0, 0, 7, 1, 6, false),
	      Arguments.of(7, 0, 0, 7, 5, 5, false)
	    );
	}
	
	@Test
	public void checkerCannotActAsFlyingKing() {
		Board board = new Board();
		Coordinate start = new Coordinate(0, 0);
		board.clear();
		board.placePiece(new Checker(Color.BLACK), start);
		Coordinate end = new Coordinate(7, 7);
		CoordinatePair pair = new CoordinatePair(start, end);
		assertFalse(Board.isMoveLegal(board, Arrays.asList(pair)));
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
	
	@ParameterizedTest
	@MethodSource("validCaptures")
	public void testValidCaptures(int startCol, int startRow, int endCol, int endRow, int opponentCol, int opponentRow, boolean validJump) {
		Coordinate pieceStart = new Coordinate(startCol, startRow);
		Coordinate pieceEnd = new Coordinate(endCol, endRow);
		Coordinate opponentStart = new Coordinate(opponentCol, opponentRow);
		FlyingKing flyingKing = new FlyingKing(pieceStart, pieceEnd);
		Board board = new Board();
		board.clear();
		board.placePiece(new King(Color.BLACK), pieceStart);
		board.placePiece(new Checker(Color.RED), opponentStart);
		System.out.println(board.toString());
		assertEquals(Board.isMoveLegal(board, flyingKing), validJump);
	}
	
	private static Stream<Arguments> validCaptures() {
	    return Stream.of(
	      Arguments.of(0, 0, 7, 7, 6, 6, true),
	      Arguments.of(0, 0, 7, 7, 5, 5, false),
	      Arguments.of(7, 7, 0, 0, 1, 1, true),
	      Arguments.of(7, 7, 0, 0, 2, 2, false),
	      Arguments.of(0, 7, 7, 0, 6, 1, true),
	      Arguments.of(0, 7, 7, 0, 3, 4, false),
	      Arguments.of(7, 0, 0, 7, 6, 1, false),
	      Arguments.of(7, 0, 0, 7, 1, 6, true)
	    );
	}
}
