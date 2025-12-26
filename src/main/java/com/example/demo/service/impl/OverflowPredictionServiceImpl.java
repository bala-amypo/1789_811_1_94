package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class OverflowPredictionServiceImpl {

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

    public OverflowPrediction generatePrediction(Long binId) {
        Bin bin = binRepo.findById(binId).orElseThrow();
        FillLevelRecord record =
                recordRepo.findTop1ByBinOrderByRecordedAtDesc(bin).orElseThrow();
        UsagePatternModel model =
                modelRepo.findTop1ByBinOrderByLastUpdatedDesc(bin).orElseThrow();

        OverflowPrediction p = new OverflowPrediction();
        p.setBin(bin);
        p.setModelUsed(model);
        p.setDaysUntilFull(3);
        p.setPredictedFullDate(LocalDate.now().plusDays(3));

        return predRepo.save(p);
    }

    public List<OverflowPrediction> getLatestPredictionsForZone(Long zoneId) {
        Zone zone = zoneRepo.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
        return predRepo.findLatestPredictionsForZone(zone);
    }
}
