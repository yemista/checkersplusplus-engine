package com.checkersplusplus.engine;

public class CoordinatePair {
	private Coordinate start;
	private Coordinate end;
	
	public CoordinatePair(Coordinate start, Coordinate end) {
		this.start = start;
		this.end = end;
	}
	
	public Coordinate getStart() {
		return start;
	}
	
	public Coordinate getEnd() {
		return end;
	}
}
