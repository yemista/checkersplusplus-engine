package com.checkersplusplus.engine.config;

public class Rules {
    private boolean allowRainbowJumps;
    private boolean allowCornerJumps;
    private boolean allowFlyingKings;

    public Rules(boolean allowRainbowJumps, boolean allowCornerJumps, boolean allowFlyingKings) {
        this.allowRainbowJumps = allowRainbowJumps;
        this.allowCornerJumps = allowCornerJumps;
        this.allowFlyingKings = allowFlyingKings;
    }

    public boolean isAllowRainbowJumps() {
        return allowRainbowJumps;
    }

    public boolean isAllowCornerJumps() {
        return allowCornerJumps;
    }

    public boolean isAllowFlyingKings() {
        return allowFlyingKings;
    }
}
