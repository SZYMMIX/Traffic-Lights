package io.github.szymmix.traffic_lights.logic.model;

import io.github.szymmix.traffic_lights.logic.CollisionManager;
import io.github.szymmix.traffic_lights.logic.utils.SetUtils;

import java.util.*;
import java.util.stream.Stream;

public final class Intersection {
    private final Map<RoadDirection, Map<WayType, Lane>> lanes = new EnumMap<>(RoadDirection.class);

    private Set<Lane> activeLanes = new HashSet<>();

    public Intersection() {
        for (RoadDirection direction : RoadDirection.values()) {
            Map<WayType, Lane> roadLanes = new EnumMap<>(WayType.class);

            for (WayType type : WayType.values()) {
                roadLanes.put(type, new Lane(new Way(direction, type)));
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

    public boolean changePhase(CollisionManager collisionManager) {
        Set<Lane> newLanes = collisionManager.calculateOptimalGreenLanes(this, activeLanes);

        if (!newLanes.equals(activeLanes)) {
            SetUtils.symmetricDifference(newLanes, activeLanes)
                    .forEach(Lane::nextLight);
            activeLanes = newLanes;
            return true;
        }
        return false;
    }

    public Set<Lane> getActiveLanes() {
        return activeLanes;
    }
}
