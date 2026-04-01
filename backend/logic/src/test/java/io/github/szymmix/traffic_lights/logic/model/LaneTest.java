package io.github.szymmix.traffic_lights.logic.model;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LaneTest {

    @Test
    void addShouldManageQueueCorrectly() {
        Way way = new Way(RoadDirection.NORTH, WayType.FORWARD);
        Lane lane = new Lane(way);
        Vehicle car = new Car("car1", way);

        assertTrue(lane.isEmpty());
        lane.add(car);
        assertEquals(1, lane.length());
        assertFalse(lane.isEmpty());

        Optional<Vehicle> removed = lane.remove();
        assertTrue(removed.isPresent());
        assertEquals("car1", removed.get().id());
        assertTrue(lane.isEmpty());
    }

    @Test
    void nextLightShouldFollowLightCycle() {
        Lane lane = new Lane(new Way(RoadDirection.NORTH, WayType.FORWARD));

        assertEquals(LightState.RED, lane.getLight());

        lane.nextLight();
        assertEquals(LightState.RED_YELLOW, lane.getLight());

        lane.nextLight();
        assertEquals(LightState.GREEN, lane.getLight());

        lane.nextLight();
        assertEquals(LightState.YELLOW, lane.getLight());

        lane.nextLight();
        assertEquals(LightState.RED, lane.getLight());
    }

    @Test
    void removeShouldReturnEmptyOnEmptyLane() {
        Way way = new Way(RoadDirection.NORTH, WayType.FORWARD);
        Lane lane = new Lane(way);

        Optional<Vehicle> removed = lane.remove();
        assertFalse(removed.isPresent());
    }

    @Test
    void lengthShouldReturnCorrectCountAfterMultipleAdds() {
        Way way = new Way(RoadDirection.NORTH, WayType.FORWARD);
        Lane lane = new Lane(way);

        for (int i = 0; i < 5; i++) {
            lane.add(new Car("car-" + i, way));
        }

        assertEquals(5, lane.length());
    }

    @Test
    void isEmptyShouldBeFalseAfterAdd() {
        Way way = new Way(RoadDirection.NORTH, WayType.FORWARD);
        Lane lane = new Lane(way);
        Vehicle car = new Car("car", way);

        lane.add(car);
        assertFalse(lane.isEmpty());
    }

    @Test
    void getWayShouldReturnCorrectWay() {
        Way way = new Way(RoadDirection.NORTH, WayType.LEFT_TURN);
        Lane lane = new Lane(way);

        assertEquals(way, lane.getWay());
    }
}