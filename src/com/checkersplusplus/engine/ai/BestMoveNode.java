package com.checkersplusplus.engine.ai;

import java.util.ArrayList;
import java.util.List;

import com.checkersplusplus.engine.moves.Move;

public class BestMoveNode {
	private List<BestMoveNode> children = new ArrayList<>();
	private boolean pieceTaken;
	private Move move;
}
