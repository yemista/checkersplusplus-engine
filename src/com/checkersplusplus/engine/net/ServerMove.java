package com.checkersplusplus.engine.net;

import java.util.List;

import com.checkersplusplus.engine.Coordinate;
import com.checkersplusplus.engine.Move;

public class ServerMove {
    private Move move;
    private List<Coordinate> capturedPieces;

    public ServerMove(Move move, List<Coordinate> capturedPieces) {
        this.move = move;
        this.capturedPieces = capturedPieces;
    }

    public Move getMove() {
        return move;
    }

    public List<Coordinate> getCapturedPieces() {
        return capturedPieces;
    }
}
