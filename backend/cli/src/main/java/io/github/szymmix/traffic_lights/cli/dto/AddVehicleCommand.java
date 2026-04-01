package io.github.szymmix.traffic_lights.cli.dto;

public record AddVehicleCommand(
        String vehicleId,
        String startRoad,
        String endRoad
) implements Command {
}
