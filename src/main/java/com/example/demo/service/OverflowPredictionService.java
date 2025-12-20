package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.OverflowPrediction;

@Service
public interface OverflowPredictionService {

    OverflowPrediction generatePrediction(Long binId);

    OverflowPrediction getPredictionById(Long id);

    List<OverflowPrediction> getPredictionsForBin(Long binId);

    List<OverflowPrediction> getLatestPredictionsForZone(Long zoneId);
}
