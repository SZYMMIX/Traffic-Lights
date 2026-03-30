package io.github.szymmix.traffic_lights.logic.model;

import java.util.*;

public class Lane {

    private final Deque<Vehicle> vehicles = new ArrayDeque<>();
    private final RoadDirection direction;
    private final WayType type;
    private LightState light = LightState.RED;

    public Lane(RoadDirection direction, WayType type) {
        this.direction = direction;
        this.type = type;
    }

    public void add(Vehicle newVehicle){
        vehicles.add(newVehicle);
    }

    public Optional<Vehicle> remove() {
        return Optional.ofNullable(vehicles.poll());
    }

    public int length() {
        return vehicles.size();
    }

    public RoadDirection getDirection() { return direction; }
    public WayType getType() { return type; }
    public LightState getLight() { return light; }
    public void setLight(LightState light) { this.light = light; }
}
