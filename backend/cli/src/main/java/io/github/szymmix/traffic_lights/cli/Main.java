package io.github.szymmix.traffic_lights.cli;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.szymmix.traffic_lights.cli.dto.*;
import io.github.szymmix.traffic_lights.logic.CollisionManager;
import io.github.szymmix.traffic_lights.logic.SimulationEngine;
import io.github.szymmix.traffic_lights.logic.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar sim.jar <input.json> <output.json>");
            System.exit(1);
        }

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Intersection intersection = new Intersection();
        CollisionManager collisionManager = new CollisionManager();
        SimulationEngine engine = new SimulationEngine(intersection, collisionManager);
        SimulationInput input = null;

        try {
            input = mapper.readValue(new File(args[0]), SimulationInput.class);

        } catch (IOException e) {
            System.err.println("Simulation Error: " + e.getMessage());
            System.exit(1);
        }

        List<SimulationOutput.StepStatus> stepStatuses = new ArrayList<>();

        for (Command cmd : input.commands()) {
            switch (cmd) {
                case AddVehicleCommand add -> {
                    RoadDirection start = RoadDirection.valueOf(add.startRoad().toUpperCase());
                    RoadDirection end = RoadDirection.valueOf(add.endRoad().toUpperCase());
                    engine.addVehicle(new Car(add.vehicleId(), new Way(start, end)));
                }
                case StepCommand _ -> {
                    List<String> left = engine.step();
                    stepStatuses.add(new SimulationOutput.StepStatus(left));
                }
            }
        }

        SimulationOutput output = new SimulationOutput(stepStatuses);

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[1]), output);
            System.out.println("Success! Output written to " + args[1]);

        } catch (IOException e) {
            System.err.println("Simulation Error: " + e.getMessage());
            System.exit(1);
        }
    }
}