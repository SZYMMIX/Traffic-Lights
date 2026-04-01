package io.github.szymmix.traffic_lights.logic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadDirectionTest {

    @Test
    void nextShouldRotateClockwise() {
        assertEquals(RoadDirection.SOUTH, RoadDirection.EAST.next());
        assertEquals(RoadDirection.WEST, RoadDirection.SOUTH.next());
        assertEquals(RoadDirection.NORTH, RoadDirection.WEST.next());
        assertEquals(RoadDirection.EAST, RoadDirection.NORTH.next());
    }

    @Test
    void previousShouldRotateCounterClockwise() {
        assertEquals(RoadDirection.NORTH, RoadDirection.EAST.previous());
        assertEquals(RoadDirection.EAST, RoadDirection.SOUTH.previous());
        assertEquals(RoadDirection.SOUTH, RoadDirection.WEST.previous());
        assertEquals(RoadDirection.WEST, RoadDirection.NORTH.previous());
    }

    @Test
    void oppositeShouldReturnOppositeDirection() {
        assertEquals(RoadDirection.NORTH, RoadDirection.SOUTH.opposite());
        assertEquals(RoadDirection.EAST, RoadDirection.WEST.opposite());
        assertEquals(RoadDirection.SOUTH, RoadDirection.NORTH.opposite());
        assertEquals(RoadDirection.WEST, RoadDirection.EAST.opposite());
    }
}