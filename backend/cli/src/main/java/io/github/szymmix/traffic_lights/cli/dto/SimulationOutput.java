package io.github.szymmix.traffic_lights.cli.dto;

import java.util.List;

public record SimulationOutput(List<StepStatus> stepStatuses) {
    public record StepStatus(List<String> leftVehicles) {}
}