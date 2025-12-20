package com.example.demo.service;

import java.util.List;

import com.example.demo.model.OverflowPrediction;

public interface OverflowPredictionService {

    OverflowPrediction generatePrediction(Long binId);

    OverflowPrediction getPredictionById(Long id);

    List<OverflowPrediction> getPredictionsForBin(Long binId);

    List<OverflowPrediction> getLatestPredictionsForZone(Long zoneId);
}
