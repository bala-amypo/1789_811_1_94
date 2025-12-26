package com.example.demo.controller;

import com.example.demo.model.OverflowPrediction;
import com.example.demo.service.OverflowPredictionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
public class OverflowPredictionController {

    private final OverflowPredictionService predictionService;

    public OverflowPredictionController(OverflowPredictionService predictionService) {
        this.predictionService = predictionService;
    }

    // POST /api/predictions/bin/{binId}
    @PostMapping("/bin/{binId}")
    public OverflowPrediction generatePrediction(@PathVariable Long binId) {
        return predictionService.generatePrediction(binId);
    }

    // GET /api/predictions/zone/{zoneId}
    @GetMapping("/zone/{zoneId}")
    public List<OverflowPrediction> getLatestPredictionsForZone(@PathVariable Long zoneId) {
        return predictionService.getLatestPredictionsForZone(zoneId);
    }

    // GET /api/predictions/bin/{binId}/latest
    @GetMapping("/bin/{binId}/latest")
    public OverflowPrediction getLatestPredictionForBin(@PathVariable Long binId) {
        return predictionService.getLatestPredictionForBin(binId);
    }
}
