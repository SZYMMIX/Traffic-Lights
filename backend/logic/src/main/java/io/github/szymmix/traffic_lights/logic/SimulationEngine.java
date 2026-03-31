package io.github.szymmix.traffic_lights.logic;

import io.github.szymmix.traffic_lights.logic.model.*;

import java.util.*;

public final class SimulationEngine {
    private static final int MIN_GREEN_TIME = 5;

    private int stepsSinceLastChange = 0;


    private final Intersection intersection;
    private final CollisionManager collisionManager;

    public SimulationEngine(Intersection intersection, CollisionManager collisionManager) {
        this.intersection = intersection;
        this.collisionManager = collisionManager;
    }

    public void addVehicle(Vehicle vehicle) {
        Way way = vehicle.way();
        intersection.getLane(way.startRoad(), way.type()).add(vehicle);
    }

    public List<String> step() {
        stepsSinceLastChange++;

        if (shouldPhaseChange() && intersection.changePhase(collisionManager)) {
            stepsSinceLastChange = 0;
        }

        return intersection.allLanes()
                .filter(lane -> lane.getLight() == LightState.GREEN)
                .map(Lane::remove)
                .flatMap(Optional::stream)
                .map(Vehicle::id)
                .toList();
    }

    private boolean shouldPhaseChange() {
        if (stepsSinceLastChange == 1) {
            intersection.allLanes()
                    .filter(lane ->
                            lane.getLight() == LightState.RED_YELLOW || lane.getLight() == LightState.YELLOW
                    )
                    .forEach(Lane::nextLight);
            return false;
        }
        return intersection.getActiveLanes().stream()
                .anyMatch(Lane::isEmpty) || stepsSinceLastChange >= MIN_GREEN_TIME;
    }
}
