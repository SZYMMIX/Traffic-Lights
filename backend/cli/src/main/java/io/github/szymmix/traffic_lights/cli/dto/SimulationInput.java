package io.github.szymmix.traffic_lights.cli.dto;

import java.util.List;

public record SimulationInput(List<Command> commands) {
}