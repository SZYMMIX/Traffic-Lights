package io.github.szymmix.traffic_lights.logic;

import io.github.szymmix.traffic_lights.logic.model.Intersection;
import io.github.szymmix.traffic_lights.logic.model.Lane;
import io.github.szymmix.traffic_lights.logic.model.RoadDirection;
import io.github.szymmix.traffic_lights.logic.model.Way;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class CollisionManager {

    private record ConflictPair(Way way1, Way way2) {}

    private final Set<ConflictPair> conflicts = new HashSet<>();

    public CollisionManager() {
        initializeConflicts();
    }

    public boolean areConflicting(Way way1, Way way2) {
        if (way1.equals(way2)) return false;
        return conflicts.contains(new ConflictPair(way1, way2));
    }

    private void initializeConflicts() {
        for (RoadDirection direction : RoadDirection.values()) {
            Way forward = new Way(direction, direction.opposite());
            Way left = new Way(direction, direction.next());
            Way right = new Way(direction, direction.previous());

            RoadDirection nextDirection = direction.next();
            RoadDirection previousDirection = direction.previous();
            RoadDirection oppositeDirection = direction.opposite();

            addConflict(forward, new Way(nextDirection, previousDirection));
            addConflict(forward, new Way(nextDirection, oppositeDirection));
            addConflict(forward, new Way(previousDirection, nextDirection));
            addConflict(forward, new Way(previousDirection, direction));
            addConflict(forward, new Way(previousDirection, oppositeDirection));
            addConflict(forward, new Way(oppositeDirection, previousDirection));

            addConflict(left, new Way(nextDirection, previousDirection));
            addConflict(left, new Way(nextDirection, oppositeDirection));
            addConflict(left, new Way(previousDirection, nextDirection));
            addConflict(left, new Way(previousDirection, direction));
            addConflict(left, new Way(oppositeDirection, direction));
            addConflict(left, new Way(oppositeDirection, nextDirection));

            addConflict(right, new Way(nextDirection, previousDirection));
            addConflict(right, new Way(oppositeDirection, previousDirection));
        }
    }

    private void addConflict(Way way1, Way way2) {
        conflicts.add(new ConflictPair(way1, way2));
        conflicts.add(new ConflictPair(way2, way1));
    }

    public Set<Lane> calculateOptimalGreenLights(Intersection intersection, Set<Lane> currentTargetLanes) {
        Set<Lane> selectedLanes = new HashSet<>();
        intersection.allLanes()
                .filter(l -> !l.isEmpty())
                .sorted(Comparator.comparingInt(Lane::length).reversed())
                .forEach(lane -> {
                    boolean hasConflict = selectedLanes.stream()
                            .anyMatch(selected -> areConflicting(
                                    lane.getWay(),
                                    selected.getWay())
                            );
                    if (!hasConflict) {
                        selectedLanes.add(lane);
                    }
                });

        return selectedLanes.isEmpty() ? currentTargetLanes : selectedLanes;
    }
}