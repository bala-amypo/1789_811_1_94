package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.OverflowPrediction;
import com.example.demo.service.OverflowPredictionService;

@RestController
@RequestMapping("/api/predictions")
public class OverflowPredictionController {

    private final OverflowPredictionService predictionService;

    public OverflowPredictionController(OverflowPredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/generate/{binId}")
    public OverflowPrediction generate(@PathVariable Long binId) {
        return predictionService.generatePrediction(binId);
    }

    @GetMapping("/{id}")
    public OverflowPrediction getById(@PathVariable Long id) {
        return predictionService.getPredictionById(id);
    }

    @GetMapping("/bin/{binId}")
    public List<OverflowPrediction> getForBin(@PathVariable Long binId) {
        return predictionService.getPredictionsForBin(binId);
    }

    @GetMapping("/zone/{zoneId}/latest")
    public List<OverflowPrediction> getLatestForZone(@PathVariable Long zoneId) {
        return predictionService.getLatestPredictionsForZone(zoneId);
    }
}
