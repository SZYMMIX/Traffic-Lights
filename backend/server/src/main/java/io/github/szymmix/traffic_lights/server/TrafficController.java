package io.github.szymmix.traffic_lights.server;

import io.github.szymmix.traffic_lights.logic.CollisionManager;
import io.github.szymmix.traffic_lights.logic.SimulationEngine;
import io.github.szymmix.traffic_lights.logic.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "http://localhost:5173")
public class TrafficController {

    private SimulationEngine engine;
    private Intersection intersection;

    public TrafficController() {
        this.intersection = new Intersection();
        this.engine = new SimulationEngine(intersection, new CollisionManager());
    }

    @GetMapping("/state")
    public IntersectionSnapshot getState() {
        return intersection.getSnapshot();
    }

    @PostMapping("/add-vehicle")
    public IntersectionSnapshot addVehicle(@RequestBody VehicleRequest request) {
        engine.addVehicle(new Car(request.id(), new Way(request.start(), request.end())));
        return intersection.getSnapshot();
    }

    @PostMapping("/step")
    public StepResponse doStep() {
        List<String> left = engine.step();
        return new StepResponse(left, intersection.getSnapshot());
    }

    @PostMapping("/reset")
    public IntersectionSnapshot reset() {
        this.intersection = new Intersection();
        this.engine = new SimulationEngine(intersection, new CollisionManager());
        return intersection.getSnapshot();
    }

    public record VehicleRequest(String id, RoadDirection start, RoadDirection end) {}
    public record StepResponse(List<String> leftVehicles, IntersectionSnapshot state) {}
}