package io.github.szymmix.traffic_lights.logic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WayTest {

    @Test
    void constructorWithTwoRoadsShouldCalculateForwardType() {
        Way way = new Way(RoadDirection.NORTH, RoadDirection.SOUTH);

        assertEquals(RoadDirection.NORTH, way.startRoad());
        assertEquals(RoadDirection.SOUTH, way.endRoad());
        assertEquals(WayType.FORWARD, way.type());
    }

    @Test
    void constructorWithTwoRoadsShouldCalculateLeftTurnType() {
        Way way = new Way(RoadDirection.NORTH, RoadDirection.EAST);

        assertEquals(RoadDirection.NORTH, way.startRoad());
        assertEquals(RoadDirection.EAST, way.endRoad());
        assertEquals(WayType.LEFT_TURN, way.type());
    }

    @Test
    void constructorWithTwoRoadsShouldCalculateRightTurnType() {
        Way way = new Way(RoadDirection.NORTH, RoadDirection.WEST);

        assertEquals(RoadDirection.NORTH, way.startRoad());
        assertEquals(RoadDirection.WEST, way.endRoad());
        assertEquals(WayType.RIGHT_TURN, way.type());
    }

    @Test
    void constructorWithRoadAndForwardTypeShouldSetOppositeEnd() {
        Way way = new Way(RoadDirection.SOUTH, WayType.FORWARD);

        assertEquals(RoadDirection.SOUTH, way.startRoad());
        assertEquals(RoadDirection.NORTH, way.endRoad());
        assertEquals(WayType.FORWARD, way.type());
    }

    @Test
    void constructorWithRoadAndLeftTurnTypeShouldSetNextEnd() {
        Way way = new Way(RoadDirection.NORTH, WayType.LEFT_TURN);

        assertEquals(RoadDirection.NORTH, way.startRoad());
        assertEquals(RoadDirection.EAST, way.endRoad());
        assertEquals(WayType.LEFT_TURN, way.type());
    }

    @Test
    void constructorWithRoadAndRightTurnTypeShouldSetPreviousEnd() {
        Way way = new Way(RoadDirection.NORTH, WayType.RIGHT_TURN);

        assertEquals(RoadDirection.NORTH, way.startRoad());
        assertEquals(RoadDirection.WEST, way.endRoad());
        assertEquals(WayType.RIGHT_TURN, way.type());
    }

    @Test
    void constructorShouldThrowExceptionForInvalidRoadCombination() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Way(RoadDirection.NORTH, RoadDirection.NORTH);
        });
    }

    @Test
    void constructorShouldThrowExceptionForAdjacentInvalidRoads() {
       assertThrows(IllegalArgumentException.class, () -> {
            new Way(RoadDirection.NORTH, RoadDirection.NORTH);
       });
    }

    @Test
    void constructorWithRoadAndTypeShouldWorkForAllDirections() {
        for (RoadDirection direction : RoadDirection.values()) {
            Way forward = new Way(direction, WayType.FORWARD);
            Way left = new Way(direction, WayType.LEFT_TURN);
            Way right = new Way(direction, WayType.RIGHT_TURN);

            assertEquals(direction, forward.startRoad());
            assertEquals(direction, left.startRoad());
            assertEquals(direction, right.startRoad());

            assertNotEquals(direction, forward.endRoad());
            assertNotEquals(direction, left.endRoad());
            assertNotEquals(direction, right.endRoad());
        }
    }

    @Test
    void constructorWithTwoRoadsShouldWorkForAllForwardCombinations() {
        Way northSouth = new Way(RoadDirection.NORTH, RoadDirection.SOUTH);
        Way southNorth = new Way(RoadDirection.SOUTH, RoadDirection.NORTH);
        Way eastWest = new Way(RoadDirection.EAST, RoadDirection.WEST);
        Way westEast = new Way(RoadDirection.WEST, RoadDirection.EAST);

        assertEquals(WayType.FORWARD, northSouth.type());
        assertEquals(WayType.FORWARD, southNorth.type());
        assertEquals(WayType.FORWARD, eastWest.type());
        assertEquals(WayType.FORWARD, westEast.type());
    }

    @Test
    void constructorShouldConsistentlyCalculateTypeFromTwoRoads() {
        for (RoadDirection start : RoadDirection.values()) {
            Way forward = new Way(start, start.opposite());
            Way left = new Way(start, start.next());
            Way right = new Way(start, start.previous());

            assertEquals(WayType.FORWARD, forward.type());
            assertEquals(WayType.LEFT_TURN, left.type());
            assertEquals(WayType.RIGHT_TURN, right.type());
        }
    }
}

