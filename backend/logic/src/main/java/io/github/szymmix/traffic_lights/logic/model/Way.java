package io.github.szymmix.traffic_lights.logic.model;

public record Way (RoadDirection startRoad, RoadDirection endRoad, WayType type) {

    public Way(RoadDirection startRoad, RoadDirection endRoad) {
        this(startRoad, endRoad, calculateType(startRoad, endRoad));
    }

    private static WayType calculateType(RoadDirection startRoad, RoadDirection endRoad) {
        if (endRoad == startRoad.opposite()) return WayType.FORWARD;
        if (endRoad == startRoad.next())     return WayType.LEFT_TURN;
        if (endRoad == startRoad.previous()) return WayType.RIGHT_TURN;

        throw new IllegalArgumentException("Illegal move from " + startRoad + " to " + endRoad);
    }
}
