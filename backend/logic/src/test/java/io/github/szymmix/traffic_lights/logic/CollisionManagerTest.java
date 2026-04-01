package io.github.szymmix.traffic_lights.logic;

import io.github.szymmix.traffic_lights.logic.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CollisionManagerTest {

    private CollisionManager collisionManager;
    private Intersection intersection;

    @BeforeEach
    void setUp() {
        collisionManager = new CollisionManager();
        intersection = new Intersection();
    }

    @Test
    void areConflictingShouldDetectForwardConflict() {
        Way northForward = new Way(RoadDirection.NORTH, RoadDirection.SOUTH);
        Way eastForward = new Way(RoadDirection.EAST, RoadDirection.WEST);

        assertTrue(collisionManager.areConflicting(northForward, eastForward));
    }

    @Test
    void areConflictingShouldNotDetectOppositeAsConflict() {
        Way northForward = new Way(RoadDirection.NORTH, RoadDirection.SOUTH);
        Way southForward = new Way(RoadDirection.SOUTH, RoadDirection.NORTH);

        assertFalse(collisionManager.areConflicting(northForward, southForward));
    }

    @Test
    void areConflictingShouldDetectLeftTurnConflict() {
        Way northLeft = new Way(RoadDirection.NORTH, RoadDirection.EAST);
        Way southForward = new Way(RoadDirection.SOUTH, RoadDirection.NORTH);

        assertTrue(collisionManager.areConflicting(northLeft, southForward));
    }

    @Test
    void areConflictingShouldDetectRightTurnConflict() {
        Way northLeft = new Way(RoadDirection.EAST, RoadDirection.NORTH);
        Way southForward = new Way(RoadDirection.SOUTH, RoadDirection.NORTH);

        assertTrue(collisionManager.areConflicting(northLeft, southForward));
    }

    @Test
    void calculateOptimalGreenLightsShouldPrioritizeCrowdedLanesWithSafety() {
        addVehicles(RoadDirection.NORTH, WayType.FORWARD, 10);
        addVehicles(RoadDirection.SOUTH, WayType.FORWARD, 5);
        addVehicles(RoadDirection.EAST, WayType.FORWARD, 8);

        Set<Lane> result = collisionManager.calculateOptimalGreenLights(intersection, Set.of());

        assertEquals(2, result.size());

        assertTrue(result.contains(intersection.getLane(RoadDirection.NORTH, WayType.FORWARD)));

        assertTrue(result.contains(intersection.getLane(RoadDirection.SOUTH, WayType.FORWARD)));

        assertFalse(result.contains(intersection.getLane(RoadDirection.EAST, WayType.FORWARD)));
    }

    @Test
    void calculateOptimalGreenLightsShouldMaintainCurrentLanesWhenEmpty() {
        Set<Lane> currentActive = Set.of(intersection.getLane(RoadDirection.NORTH, WayType.FORWARD));

        Set<Lane> result = collisionManager.calculateOptimalGreenLights(intersection, currentActive);

        assertEquals(currentActive, result);
    }

    @Test
    void calculateOptimalGreenLightsShouldAllowMultipleRightTurnsSimultaneously() {
        addVehicles(RoadDirection.NORTH, WayType.RIGHT_TURN, 1);
        addVehicles(RoadDirection.EAST, WayType.RIGHT_TURN, 1);

        Set<Lane> result = collisionManager.calculateOptimalGreenLights(intersection, Set.of());

        assertEquals(2, result.size());
    }

    @Test
    void areConflictingShouldDetectLeftTurnVsOpposite() {
        Way northLeft = new Way(RoadDirection.NORTH, RoadDirection.EAST);
        Way southForward = new Way(RoadDirection.SOUTH, RoadDirection.NORTH);

        assertTrue(collisionManager.areConflicting(northLeft, southForward));
    }

    @Test
    void calculateOptimalGreenLightsShouldPreferCrowdedLanes() {
        addVehicles(RoadDirection.NORTH, WayType.FORWARD, 15);
        addVehicles(RoadDirection.EAST, WayType.FORWARD, 3);

        Set<Lane> result = collisionManager.calculateOptimalGreenLights(intersection, Set.of());

        assertTrue(result.contains(intersection.getLane(RoadDirection.NORTH, WayType.FORWARD)));
        assertFalse(result.contains(intersection.getLane(RoadDirection.EAST, WayType.FORWARD)));
    }

    @Test
    void calculateOptimalGreenLightsShouldHandleAllTurnTypes() {
        addVehicles(RoadDirection.NORTH, WayType.LEFT_TURN, 1);
        addVehicles(RoadDirection.SOUTH, WayType.RIGHT_TURN, 1);

        Set<Lane> result = collisionManager.calculateOptimalGreenLights(intersection, Set.of());

        assertFalse(result.isEmpty());
    }

    private void addVehicles(RoadDirection direction, WayType type, int count) {
        for (int i = 0; i < count; i++) {
            Way way = new Way(direction, type);
            Car car = new Car("test-" + i, way);
            intersection.getLane(direction, type).add(car);
        }
    }
}