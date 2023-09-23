package com.checkersplusplus.engine.net;

import java.util.List;

import com.checkersplusplus.engine.Coordinate;

public class ServerMove {
    private List<Coordinate> moves;

    public ServerMove(List<Coordinate> moves) {
        this.moves = moves;
    }

    public List<Coordinate> getMoves() {
        return moves;
    }
}
