package io.github.szymmix.traffic_lights.logic.model;

import java.util.Map;

public record IntersectionSnapshot(Map<RoadDirection, Map<WayType, LaneSnapshot>> lanes) {
}
