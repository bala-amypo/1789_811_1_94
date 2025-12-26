package com.example.demo.controller;

import com.example.demo.model.OverflowPrediction;
import com.example.demo.service.impl.OverflowPredictionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
public class OverflowPredictionController {

    private final OverflowPredictionServiceImpl predictionService;

    public OverflowPredictionController(OverflowPredictionServiceImpl predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/bin/{binId}")
    public OverflowPrediction generatePrediction(@PathVariable Long binId) {
        return predictionService.generatePrediction(binId);
    }

    @GetMapping("/zone/{zoneId}")
    public List<OverflowPrediction> getPredictionsForZone(@PathVariable Long zoneId) {
        return predictionService.getLatestPredictionsForZone(zoneId);
    }
}
