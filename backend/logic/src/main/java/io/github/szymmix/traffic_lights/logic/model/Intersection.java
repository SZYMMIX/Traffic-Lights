package io.github.szymmix.traffic_lights.logic.model;

import java.util.*;
import java.util.stream.Stream;

public class Intersection {
    private final Map<RoadDirection, Map<WayType, Lane>> lanes = new EnumMap<>(RoadDirection.class);

    public Intersection() {
        for (RoadDirection direction : RoadDirection.values()) {
            Map<WayType, Lane> roadLanes = new EnumMap<>(WayType.class);

            for (WayType type : WayType.values()) {
                roadLanes.put(type, new Lane(direction, type));
            }
            lanes.put(direction, roadLanes);
        }
    }

    public Lane getLane(RoadDirection direction, WayType type) {
        return lanes.get(direction).get(type);
    }

    public Stream<Lane> allLanes() {
        return lanes.values().stream()
                .flatMap(m -> m.values().stream());
    }
}
