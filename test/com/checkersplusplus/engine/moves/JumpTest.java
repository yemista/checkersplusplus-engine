package com.checkersplusplus.engine.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;

public class JumpTest {

	@ParameterizedTest
	@MethodSource("validJumps")
	public void testValidJump(int startCol, int startRow, int endCol, int endRow, Color color, int opponentCol, int opponentRow) {
		Board board = new Board();
		board.clear();
		Coordinate start = new Coordinate(startCol, startRow);
		Coordinate end = new Coordinate(endCol, endRow);
		Coordinate opponentLocation = new Coordinate(opponentCol, opponentRow);
		board.placePiece(new Checker(color), start);
		board.placePiece(new Checker(oppositeColor(color)), opponentLocation);
		System.out.print(board.toString());
		assertTrue(Jump.isValidJump(board, start, end));
	}
	
	private static Stream<Arguments> validJumps() {
	    return Stream.of(
	      Arguments.of(2, 2, 4, 4, Color.BLACK, 3, 3),
	      Arguments.of(2, 2, 0, 4, Color.BLACK, 1, 3),
	      Arguments.of(3, 3, 2, 2, Color.RED, 1, 1),
	      Arguments.of(3, 3, 2, 5, Color.RED, 3, 3)
	    );
	}
	
	private Color oppositeColor(Color color) {
		return color == Color.BLACK ? Color.RED : Color.BLACK;
	}
	
	@Test
	public void redJumpMustCapturePiece() {
		Board board = new Board();
		Coordinate start = new Coordinate(2, 5);
		board.placePiece(new Checker(Color.BLACK), new Coordinate(3, 4));
		Coordinate end = new Coordinate(4, 3);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), Color.RED);
		assertTrue(Jump.isValidJump(board, start, end));
	}
	
	@Test
	public void redMustJumpTwoRows() {
		Board board = new Board();
		Coordinate start = new Coordinate(2, 5);
		Coordinate end = new Coordinate(4, 4);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), Color.RED);
		assertFalse(Jump.isValidJump(board, start, end));
	}
	
	@Test
	public void redMustJumpTwoCols() {
		Board board = new Board();
		Coordinate start = new Coordinate(2, 5);
		Coordinate end = new Coordinate(3, 3);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), Color.RED);
		assertFalse(Jump.isValidJump(board, start, end));
	}
}
