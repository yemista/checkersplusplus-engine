package com.checkersplusplus.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.checkersplusplus.engine.enums.Color;
import com.checkersplusplus.engine.moves.CornerJump;
import com.checkersplusplus.engine.moves.Jump;
import com.checkersplusplus.engine.moves.Move;
import com.checkersplusplus.engine.moves.RainbowJump;
import com.checkersplusplus.engine.pieces.Checker;
import com.checkersplusplus.engine.pieces.King;

public class BoardTest {
	
	@ParameterizedTest
	@MethodSource("connectedMoves")
	public void testValidateMovesAreConnected(List<Move> moves, boolean connected) {
		assertEquals(Board.validateMovesAreConnected(moves), connected);
	}
	
	private static Stream<Arguments> connectedMoves() {
	    return Stream.of(
	      Arguments.of(Arrays.asList(new Jump(new Coordinate(0, 0), new Coordinate(2, 2)), new Jump(new Coordinate(2, 2), new Coordinate(4, 4))), true),
	      Arguments.of(Arrays.asList(new Jump(new Coordinate(0, 0), new Coordinate(2, 2)), new Jump(new Coordinate(1, 2), new Coordinate(4, 4))), false)
	    );
	}
	
	@Test
	public void testMoveInWrongDirectionForFlyingKings() {
		Board board = new Board();
		RainbowJump blackReverseRainbowJump = new RainbowJump(new Coordinate(5, 5), new Coordinate(1, 5));
		board.clear();
		board.placePiece(new King(Color.BLACK), blackReverseRainbowJump.getStart());
		assertFalse(Board.isMoveInWrongDirection(board, blackReverseRainbowJump));
	    RainbowJump redReverseRainbowJump = new RainbowJump(new Coordinate(1, 5), new Coordinate(5, 5));
	    board.clear();
		board.placePiece(new King(Color.RED), redReverseRainbowJump.getStart());
		assertFalse(Board.isMoveInWrongDirection(board, redReverseRainbowJump));
	}

	@Test
	public void testMoveInWrongDirectionForReverseRainbowJumps() {
		Board board = new Board();
		RainbowJump blackReverseRainbowJump = new RainbowJump(new Coordinate(5, 5), new Coordinate(5, 1));
		board.clear();
		board.placePiece(new Checker(Color.BLACK), blackReverseRainbowJump.getStart());
		assertTrue(Board.isMoveInWrongDirection(board, blackReverseRainbowJump));
	    RainbowJump redReverseRainbowJump = new RainbowJump(new Coordinate(1, 1), new Coordinate(1, 5));
	    board.clear();
		board.placePiece(new Checker(Color.RED), redReverseRainbowJump.getStart());
		assertTrue(Board.isMoveInWrongDirection(board, redReverseRainbowJump));
	}
	
	@ParameterizedTest
	@MethodSource("directionalMoves")
	public void testMoveInWrongDirection(Move move, boolean notValid) {
		Board board = new Board();
		assertEquals(Board.isMoveInWrongDirection(board, move), notValid);
	}
	
	private static Stream<Arguments> directionalMoves() {
	    return Stream.of(
	      Arguments.of(new CornerJump(new Coordinate(6, 2), new Coordinate(6, 4)), false),
	      Arguments.of(new CornerJump(new Coordinate(6, 6), new Coordinate(6, 4)), false),
	      Arguments.of(new CornerJump(new Coordinate(6, 2), new Coordinate(6, 0)), true),
	      Arguments.of(new CornerJump(new Coordinate(1, 5), new Coordinate(1, 7)), true),
	      Arguments.of(new Jump(new Coordinate(4, 2), new Coordinate(2, 4)), false),
	      Arguments.of(new Jump(new Coordinate(4, 2), new Coordinate(6, 4)), false),
	      Arguments.of(new Jump(new Coordinate(3, 5), new Coordinate(1, 3)), false),
	      Arguments.of(new Jump(new Coordinate(3, 5), new Coordinate(5, 3)), false),
	      Arguments.of(new Jump(new Coordinate(4, 2), new Coordinate(2, 0)), true),
	      Arguments.of(new Jump(new Coordinate(4, 2), new Coordinate(6, 0)), true),
	      Arguments.of(new Jump(new Coordinate(3, 5), new Coordinate(1, 7)), true),
	      Arguments.of(new Jump(new Coordinate(3, 5), new Coordinate(5, 7)), true),
	      Arguments.of(new RainbowJump(new Coordinate(2, 2), new Coordinate(2, 6)), false),
	      Arguments.of(new RainbowJump(new Coordinate(2, 2), new Coordinate(6, 2)), false),
	      Arguments.of(new RainbowJump(new Coordinate(6, 2), new Coordinate(2, 2)), false),
	      Arguments.of(new RainbowJump(new Coordinate(3, 5), new Coordinate(3, 1)), false),
	      Arguments.of(new RainbowJump(new Coordinate(3, 5), new Coordinate(7, 5)), false),
	      Arguments.of(new RainbowJump(new Coordinate(5, 5), new Coordinate(1, 5)), false)
	    );
	}
}
