package io.github.szymmix.traffic_lights.logic.model;

public enum LightState {
    GREEN,
    YELLOW,
    RED,
    RED_YELLOW;

    public LightState next() {
        return values()[(ordinal() + 1) % 4];
    }
}
