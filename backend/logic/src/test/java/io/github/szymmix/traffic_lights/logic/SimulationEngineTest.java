package io.github.szymmix.traffic_lights.logic;

import io.github.szymmix.traffic_lights.logic.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SimulationEngineTest {

    private SimulationEngine engine;
    private Intersection intersection;

    @BeforeEach
    void setUp() {
        intersection = new Intersection();
        CollisionManager collisionManager = new CollisionManager();
        engine = new SimulationEngine(intersection, collisionManager);
    }

    @Test
    void stepShouldProcessVehicleLifeCycleCorrectly() {
        Vehicle vehicle1 = new Car("vehicle1", new Way(RoadDirection.NORTH, RoadDirection.SOUTH));
        engine.addVehicle(vehicle1);

        List<String> left1 = engine.step();
        assertTrue(left1.isEmpty());
        assertEquals(LightState.RED_YELLOW, intersection.getLane(RoadDirection.NORTH, WayType.FORWARD).getLight());

        List<String> left2 = engine.step();
        assertEquals(1, left2.size());
        assertTrue(left2.contains("vehicle1"));
        assertEquals(LightState.GREEN, intersection.getLane(RoadDirection.NORTH, WayType.FORWARD).getLight());
    }

    @Test
    void stepShouldPreventConflictingVehicles() {
        engine.addVehicle(new Car("v-north", new Way(RoadDirection.NORTH, WayType.FORWARD)));
        engine.addVehicle(new Car("v-east", new Way(RoadDirection.EAST, WayType.FORWARD)));

        for (int i = 0; i < 10; i++) {
            List<String> left = engine.step();
            assertFalse(left.contains("v-north") && left.contains("v-east"));
        }
    }

    @Test
    void stepShouldMaintainMinimumGreenTime() {
        for(int i=0; i<10; i++) engine.addVehicle(new Car("vehicle"+i, new Way(RoadDirection.NORTH, WayType.FORWARD)));

        engine.step();
        engine.step();

        engine.addVehicle(new Car("east-car", new Way(RoadDirection.EAST, WayType.FORWARD)));

        for (int i = 0; i < 3; i++) {
            List<String> left = engine.step();
            assertFalse(left.contains("east-car"));
            assertEquals(LightState.GREEN, intersection.getLane(RoadDirection.NORTH, WayType.FORWARD).getLight());
        }
    }

    @Test
    void stepShouldSwitchLightsEarlyWhenNoVehicles() {
        engine.addVehicle(new Car("north-only", new Way(RoadDirection.NORTH, WayType.FORWARD)));
        engine.step();
        engine.step();

        engine.addVehicle(new Car("east-waiting", new Way(RoadDirection.EAST, WayType.FORWARD)));

        engine.step();

        assertEquals(LightState.YELLOW, intersection.getLane(RoadDirection.NORTH, WayType.FORWARD).getLight());
        assertEquals(LightState.RED_YELLOW, intersection.getLane(RoadDirection.EAST, WayType.FORWARD).getLight());
    }

    @Test
    void stepShouldTransitionYellowToRed() {
        engine.addVehicle(new Car("vehicle1", new Way(RoadDirection.NORTH, WayType.FORWARD)));
        engine.step();
        engine.step();

        intersection.getLane(RoadDirection.NORTH, WayType.FORWARD).remove();
        engine.addVehicle(new Car("vehicle2", new Way(RoadDirection.EAST, WayType.FORWARD)));

        engine.step();
        assertEquals(LightState.YELLOW, intersection.getLane(RoadDirection.NORTH, WayType.FORWARD).getLight());

        engine.step();
        assertEquals(LightState.RED, intersection.getLane(RoadDirection.NORTH, WayType.FORWARD).getLight());
    }

    @Test
    void addVehicleShouldAddCarToCorrectLane() {
        Vehicle car = new Car("test-car", new Way(RoadDirection.NORTH, WayType.FORWARD));
        engine.addVehicle(car);

        Lane lane = intersection.getLane(RoadDirection.NORTH, WayType.FORWARD);
        assertEquals(1, lane.length());
    }

    @Test
    void stepShouldHandleLeftTurnVehicles() {
        engine.addVehicle(new Car("north-left", new Way(RoadDirection.NORTH, RoadDirection.EAST)));
        engine.step();

        List<String> left = engine.step();
        assertEquals(List.of("north-left"), left);
    }

    @Test
    void stepShouldHandleRightTurnVehicles() {
        engine.addVehicle(new Car("north-right", new Way(RoadDirection.NORTH, RoadDirection.WEST)));
        engine.step();

        List<String> left = engine.step();
        assertEquals(List.of("north-right"), left);
    }

    @Test
    void stepShouldRemoveVehiclesLeavingIntersection() {
        Vehicle vehicle = new Car("leaving-car", new Way(RoadDirection.NORTH, RoadDirection.SOUTH));
        engine.addVehicle(vehicle);

        engine.step();
        List<String> left = engine.step();

        assertEquals(List.of("leaving-car"), left);
    }

    @Test
    void stepShouldHandleMultipleVehiclesInSameLane() {
        for (int i = 0; i < 3; i++) {
            engine.addVehicle(new Car("car-" + i, new Way(RoadDirection.NORTH, WayType.FORWARD)));
        }

        assertEquals(3, intersection.getLane(RoadDirection.NORTH, WayType.FORWARD).length());
        engine.step();
        engine.step();

        List<String> left = engine.step();
        assertEquals(List.of("car-1"), left);
    }
}