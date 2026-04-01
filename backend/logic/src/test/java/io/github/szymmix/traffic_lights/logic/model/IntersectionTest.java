package io.github.szymmix.traffic_lights.logic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {

    @Test
    void constructorShouldInitializeAllTwelveLanes() {
        Intersection intersection = new Intersection();

        long laneCount = intersection.allLanes().count();
        assertEquals(12, laneCount);

        Lane northForward = intersection.getLane(RoadDirection.NORTH, WayType.FORWARD);
        assertNotNull(northForward);
        assertEquals(RoadDirection.NORTH, northForward.getWay().startRoad());
        assertEquals(WayType.FORWARD, northForward.getWay().type());
    }

    @Test
    void getLaneShouldReturnCorrectLaneForAllDirections() {
        Intersection intersection = new Intersection();

        Lane north = intersection.getLane(RoadDirection.NORTH, WayType.FORWARD);
        Lane south = intersection.getLane(RoadDirection.SOUTH, WayType.FORWARD);
        Lane east = intersection.getLane(RoadDirection.EAST, WayType.FORWARD);
        Lane west = intersection.getLane(RoadDirection.WEST, WayType.FORWARD);

        assertNotNull(north);
        assertNotNull(south);
        assertNotNull(east);
        assertNotNull(west);
    }

    @Test
    void getLaneShouldReturnCorrectLaneForAllTurnTypes() {
        Intersection intersection = new Intersection();

        Lane forward = intersection.getLane(RoadDirection.NORTH, WayType.FORWARD);
        Lane left = intersection.getLane(RoadDirection.NORTH, WayType.LEFT_TURN);
        Lane right = intersection.getLane(RoadDirection.NORTH, WayType.RIGHT_TURN);

        assertNotNull(forward);
        assertNotNull(left);
        assertNotNull(right);
    }

    @Test
    void getSnapshotShouldReturnAllLanes() {
        Intersection intersection = new Intersection();

        var snapshot = intersection.getSnapshot();

        assertNotNull(snapshot.lanes());
        assertEquals(4, snapshot.lanes().size());

        assertTrue(snapshot.lanes().containsKey(RoadDirection.NORTH));
        assertTrue(snapshot.lanes().containsKey(RoadDirection.SOUTH));
        assertTrue(snapshot.lanes().containsKey(RoadDirection.EAST));
        assertTrue(snapshot.lanes().containsKey(RoadDirection.WEST));
    }

    @Test
    void getSnapshotShouldIncludeVehicleCount() {
        Intersection intersection = new Intersection();
        Lane northForward = intersection.getLane(RoadDirection.NORTH, WayType.FORWARD);
        northForward.add(new Car("car1", northForward.getWay()));
        northForward.add(new Car("car2", northForward.getWay()));

        var snapshot = intersection.getSnapshot();
        assertEquals(2, snapshot.lanes().get(RoadDirection.NORTH).get(WayType.FORWARD).vehicleCount());
    }

    @Test
    void allLanesShouldReturnExactlyTwelveLanes() {
        Intersection intersection = new Intersection();

        long laneCount = intersection.allLanes().count();
        assertEquals(12, laneCount);

        long uniqueLanes = intersection.allLanes().distinct().count();
        assertEquals(12, uniqueLanes);
    }
}