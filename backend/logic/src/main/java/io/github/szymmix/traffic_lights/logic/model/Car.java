package io.github.szymmix.traffic_lights.logic.model;

public record Car(String id, Way way) implements Vehicle {
}
