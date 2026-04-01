package io.github.szymmix.traffic_lights.logic.model;

public record LaneSnapshot(LightState light, int vehicleCount) {
}
