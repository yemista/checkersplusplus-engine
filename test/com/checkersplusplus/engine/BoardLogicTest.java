package com.checkersplusplus.engine;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.moves.CornerJump;
import com.checkersplusplus.engine.moves.FlyingKing;
import com.checkersplusplus.engine.moves.Jump;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.moves.RainbowJump;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.util.MoveUtil;

/**
 * Purpose of this class is to test complicated move scenarios for the method isMoveLegal()
 */
public class BoardLogicTest {

	@Test
	public void testCornerToJumpToHorizontalRainbow() {
		Board board = new Board();
		board.clear();
		board.placePiece(new Checker(Color.BLACK), new Coordinate(6, 2));
		board.placePiece(new Checker(Color.RED), new Coordinate(7, 3));
		board.placePiece(new Checker(Color.RED), new Coordinate(5, 5));
		board.placePiece(new Checker(Color.RED), new Coordinate(2, 6));
		List<Move> moves = Arrays.asList(
					MoveUtil.createMove(board, new Coordinate(6, 2), new Coordinate(6, 4)),
					MoveUtil.createMove(board, new Coordinate(6, 4), new Coordinate(4, 6)),
					MoveUtil.createMove(board, new Coordinate(4, 6), new Coordinate(0, 6))
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
		List<Move> moves = Arrays.asList(
					MoveUtil.createMove(board, new Coordinate(0, 0), new Coordinate(2, 2)),
					MoveUtil.createMove(board, new Coordinate(2, 2), new Coordinate(0, 4)),
					MoveUtil.createMove(board, new Coordinate(0, 4), new Coordinate(2, 6))
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
		List<Move> moves = Arrays.asList(
					MoveUtil.createMove(board, new Coordinate(1, 1), new Coordinate(3, 3)),
					MoveUtil.createMove(board, new Coordinate(3, 3), new Coordinate(1, 5)),
					MoveUtil.createMove(board, new Coordinate(1, 5), new Coordinate(3, 7)),
					MoveUtil.createMove(board, new Coordinate(3, 7), new Coordinate(7, 3))
				);
		assertTrue(Board.isMoveLegal(board, moves));
	}
}
