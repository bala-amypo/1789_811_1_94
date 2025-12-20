package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;
import com.example.demo.model.FillLevelRecord;
import com.example.demo.model.OverflowPrediction;
import com.example.demo.model.UsagePatternModel;
import com.example.demo.model.Zone;
import com.example.demo.repository.BinRepository;
import com.example.demo.repository.FillLevelRecordRepository;
import com.example.demo.repository.OverflowPredictionRepository;
import com.example.demo.repository.UsagePatternModelRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.OverflowPredictionService;

public class OverflowPredictionServiceImpl implements OverflowPredictionService {

    private final BinRepository binRepository;
    private final FillLevelRecordRepository recordRepository;
    private final UsagePatternModelRepository modelRepository;
    private final OverflowPredictionRepository predictionRepository;
    private final ZoneRepository zoneRepository;

    public OverflowPredictionServiceImpl(
            BinRepository binRepository,
            FillLevelRecordRepository recordRepository,
            UsagePatternModelRepository modelRepository,
            OverflowPredictionRepository predictionRepository,
            ZoneRepository zoneRepository) {

        this.binRepository = binRepository;
        this.recordRepository = recordRepository;
        this.modelRepository = modelRepository;
        this.predictionRepository = predictionRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public OverflowPrediction generatePrediction(Long binId) {

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bin not found"));

        if (!Boolean.TRUE.equals(bin.getActive())) {
            throw new BadRequestException("Bin is inactive");
        }

        FillLevelRecord latestRecord =
                recordRepository.findTop1ByBinOrderByRecordedAtDesc(bin)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("FillLevelRecord not found"));

        UsagePatternModel model =
                modelRepository.findTop1ByBinOrderByLastUpdatedDesc(bin)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("UsagePatternModel not found"));

        double dailyIncrease = Boolean.TRUE.equals(latestRecord.getIsWeekend())
                ? model.getAvgDailyIncreaseWeekend()
                : model.getAvgDailyIncreaseWeekday();

        double remaining = 100.0 - latestRecord.getFillPercentage();

        int daysUntilFull;
        if (dailyIncrease <= 0) {
            daysUntilFull = 0;
        } else {
            daysUntilFull = (int) Math.ceil(remaining / dailyIncrease);
        }

        if (daysUntilFull < 0) {
            throw new BadRequestException("daysUntilFull must be non-negative");
        }

        LocalDate predictedDate =
                LocalDate.now().plusDays(daysUntilFull);

        Date predictedFullDate =
                Date.from(predictedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        OverflowPrediction prediction = new OverflowPrediction();
        prediction.setBin(bin);
        prediction.setModelUsed(model);
        prediction.setDaysUntilFull(daysUntilFull);
        prediction.setPredictedFullDate(predictedFullDate);
        prediction.setGeneratedAt(Timestamp.from(Instant.now()));

        return predictionRepository.save(prediction);
    }

    @Override
    public OverflowPrediction getPredictionById(Long id) {
        return predictionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("OverflowPrediction not found"));
    }

    @Override
    public List<OverflowPrediction> getPredictionsForBin(Long binId) {

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bin not found"));

        return predictionRepository.findAll()
                .stream()
                .filter(p -> p.getBin().getId().equals(bin.getId()))
                .toList();
    }

    @Override
    public List<OverflowPrediction> getLatestPredictionsForZone(Long zoneId) {

        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found"));

        return predictionRepository.findLatestPredictionsForZone(zone);
    }
}
