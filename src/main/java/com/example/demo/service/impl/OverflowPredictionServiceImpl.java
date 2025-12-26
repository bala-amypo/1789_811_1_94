package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.OverflowPredictionService;

import java.time.LocalDate;
import java.util.List;

@Service
public class OverflowPredictionServiceImpl implements OverflowPredictionService {

    private final BinRepository binRepo;
    private final FillLevelRecordRepository recordRepo;
    private final UsagePatternModelRepository modelRepo;
    private final OverflowPredictionRepository predRepo;
    private final ZoneRepository zoneRepo;

    public OverflowPredictionServiceImpl(
            BinRepository binRepo,
            FillLevelRecordRepository recordRepo,
            UsagePatternModelRepository modelRepo,
            OverflowPredictionRepository predRepo,
            ZoneRepository zoneRepo) {
        this.binRepo = binRepo;
        this.recordRepo = recordRepo;
        this.modelRepo = modelRepo;
        this.predRepo = predRepo;
        this.zoneRepo = zoneRepo;
    }

    @Override
    public OverflowPrediction generatePrediction(Long binId) {
        Bin bin = binRepo.findById(binId)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));

        FillLevelRecord record =
                recordRepo.findTop1ByBinOrderByRecordedAtDesc(bin)
                        .orElseThrow(() -> new ResourceNotFoundException("Fill record not found"));

        UsagePatternModel model =
                modelRepo.findTop1ByBinOrderByLastUpdatedDesc(bin)
                        .orElseThrow(() -> new ResourceNotFoundException("Usage pattern model not found"));

        OverflowPrediction p = new OverflowPrediction();
        p.setBin(bin);
        p.setModelUsed(model);
        p.setDaysUntilFull(3);  // example calculation
        p.setPredictedFullDate(LocalDate.now().plusDays(3));

        return predRepo.save(p);
    }

    @Override
    public OverflowPrediction getPredictionById(Long id) {
        return predRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prediction not found"));
    }

    @Override
    public List<OverflowPrediction> getPredictionsForBin(Long binId) {
        Bin bin = binRepo.findById(binId)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));
        return predRepo.findByBin(bin);
    }

    @Override
    public List<OverflowPrediction> getLatestPredictionsForZone(Long zoneId) {
        Zone zone = zoneRepo.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
        return predRepo.findLatestPredictionsForZone(zone);
    }
}
