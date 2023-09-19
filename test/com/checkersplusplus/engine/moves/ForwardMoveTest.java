package com.checkersplusplus.engine.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.checkersplusplus.engine.Board;
import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.pieces.Checker;

public class ForwardMoveTest {

	@Test
	public void testForwardMoveRightForRed() {
		Board board = new Board();
		Coordinate start = new Coordinate(2, 5);
		Coordinate end = new Coordinate(1, 4);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), Color.RED);
		assertTrue(ForwardMove.isValidForwardMove(board, start, end));
	}
	
	@Test
	public void testForwardMoveLeftForRed() {
		Board board = new Board();
		Coordinate start = new Coordinate(2, 5);
		Coordinate end = new Coordinate(3, 4);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), Color.RED);
		assertTrue(ForwardMove.isValidForwardMove(board, start, end));
	}
	
	@Test
	public void testForwardMoveRightBlockedForRed() {
		Board board = new Board();
		Coordinate start = new Coordinate(3, 6);
		Coordinate end = new Coordinate(2, 5);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), Color.RED);
		assertFalse(ForwardMove.isValidForwardMove(board, start, end));
		board.removePiece(end);
		assertTrue(ForwardMove.isValidForwardMove(board, start, end));
	}
	
	@Test
	public void testForwardMoveLeftBlockedForRed() {
		Board board = new Board();
		Coordinate start = new Coordinate(3, 6);
		Coordinate end = new Coordinate(4, 5);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), Color.RED);
		assertFalse(ForwardMove.isValidForwardMove(board, start, end));
		board.removePiece(end);
		assertTrue(ForwardMove.isValidForwardMove(board, start, end));
	}
	
	@Test
	public void testForwardMoveWithInvalidCoordinatesForRed() {
		Board board = new Board();
		Coordinate start = new Coordinate(3, 6);
		Checker playerPiece = board.getPiece(start);
		assertNotNull(playerPiece);
		assertEquals(playerPiece.getColor(), Color.RED);
		Coordinate end1 = new Coordinate(5, 4);
		assertFalse(ForwardMove.isValidForwardMove(board, start, end1));
		Coordinate end2 = new Coordinate(1, 4);
		assertFalse(ForwardMove.isValidForwardMove(board, start, end2));
		Coordinate end3 = new Coordinate(3, 5);
		assertFalse(ForwardMove.isValidForwardMove(board, start, end3));
	}
}
