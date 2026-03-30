package io.github.szymmix.traffic_lights.logic.model;

import java.util.*;

public class Intersection {
    private final Map<RoadDirection, Map<WayType, Deque<Car>>> lanes = new EnumMap<>(RoadDirection.class);

    private final Map<RoadDirection, Map<WayType, LightState>> lights = new EnumMap<>(RoadDirection.class);

    public Intersection() {
        for (RoadDirection dir : RoadDirection.values()) {
            Map<WayType, Deque<Car>> roadLanes = new EnumMap<>(WayType.class);
            Map<WayType, LightState> roadLights = new EnumMap<>(WayType.class);

            for (WayType type : WayType.values()) {
                roadLanes.put(type, new ArrayDeque<>());
                roadLights.put(type, LightState.RED);
            }

            lanes.put(dir, roadLanes);
            lights.put(dir, roadLights);
        }
    }

    public Deque<Car> getQueue(RoadDirection dir, WayType type) {
        return lanes.get(dir).get(type);
    }

    public LightState getLight(RoadDirection dir, WayType type) {
        return lights.get(dir).get(type);
    }

    public void setLight(RoadDirection dir, WayType type, LightState state) {
        lights.get(dir).put(type, state);
    }
}
