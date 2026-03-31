package io.github.szymmix.traffic_lights.logic.model;

import java.util.*;

public final class Lane {

    private final Deque<Vehicle> vehicles = new ArrayDeque<>();
    private final Way way;
    private LightState light = LightState.RED;

    public Lane(Way way) {
        this.way = way;
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

    public boolean isEmpty() {
        return vehicles.isEmpty();
    }

    public Way getWay() { return way; }
    public RoadDirection getDirection() { return way.startRoad(); }
    public WayType getType() { return way.type(); }
    public LightState getLight() { return light; }
    public void nextLight() { light = light.next(); }
}
