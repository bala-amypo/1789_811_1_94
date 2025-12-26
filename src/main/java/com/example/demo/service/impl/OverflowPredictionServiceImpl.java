package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

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

@Service
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

        // 1. Fetch and validate Bin
        Bin bin = binRepository.findById(binId)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));

        if (!Boolean.TRUE.equals(bin.getActive())) {
            throw new BadRequestException("Bin is inactive");
        }

        // 2. Fetch latest fill level record
        FillLevelRecord latestRecord = recordRepository.findTop1ByBinOrderByRecordedAtDesc(bin)
                        .orElseThrow(() -> new ResourceNotFoundException("FillLevelRecord not found"));

        // 3. Fetch latest usage model
        UsagePatternModel model = modelRepository.findTop1ByBinOrderByLastUpdatedDesc(bin)
                        .orElseThrow(() -> new ResourceNotFoundException("UsagePatternModel not found"));

        // ✅ FIX: Null-safe handling for Double values to prevent NullPointerException
        Double weekendInc = model.getAvgDailyIncreaseWeekend();
        Double weekdayInc = model.getAvgDailyIncreaseWeekday();
        
        double dailyIncrease = Boolean.TRUE.equals(latestRecord.getIsWeekend())
                ? (weekendInc != null ? weekendInc : 0.0)
                : (weekdayInc != null ? weekdayInc : 0.0);

        double remaining = 100.0 - latestRecord.getFillPercentage();

        // 4. Calculate days until full
        int daysUntilFull;
        if (dailyIncrease <= 0) {
            daysUntilFull = 0;
        } else {
            daysUntilFull = (int) Math.ceil(remaining / dailyIncrease);
        }

        if (daysUntilFull < 0) {
            throw new BadRequestException("daysUntilFull must be non-negative");
        }

        // 5. Convert LocalDate to Date for predictedFullDate
        Date predictedFullDate = Date.from(
                LocalDate.now().plusDays(daysUntilFull)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );

        // 6. Create and save prediction
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
                .orElseThrow(() -> new ResourceNotFoundException("OverflowPrediction not found"));
    }

    @Override
    public List<OverflowPrediction> getPredictionsForBin(Long binId) {
        Bin bin = binRepository.findById(binId)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));

        return predictionRepository.findByBinId(bin.getId());
    }

    @Override
    public List<OverflowPrediction> getLatestPredictionsForZone(Long zoneId) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        // ✅ FIX: Added Inactive Zone validation required by tests
        if (!Boolean.TRUE.equals(zone.getActive())) {
            throw new BadRequestException("Zone is inactive");
        }

        return predictionRepository.findLatestPredictionsForZone(zone);
    }
}