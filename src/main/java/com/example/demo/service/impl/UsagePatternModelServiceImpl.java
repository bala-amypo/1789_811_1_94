package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Bin;
import com.example.demo.model.UsagePatternModel;
import com.example.demo.repository.UsagePatternModelRepository;
import com.example.demo.service.UsagePatternModelService;
import com.example.demo.util.WeekendUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UsagePatternModelServiceImpl implements UsagePatternModelService {

    private final UsagePatternModelRepository modelRepository;

    public UsagePatternModelServiceImpl(UsagePatternModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public UsagePatternModel createModel(UsagePatternModel model) {
        if (model.getAvgDailyIncreaseWeekday() < 0 || model.getAvgDailyIncreaseWeekend() < 0) {
            throw new BadRequestException("Average daily increase cannot be negative");
        }
        return modelRepository.save(model);
    }

    @Override
    public UsagePatternModel updateModel(Long id, UsagePatternModel model) {
        UsagePatternModel existing = modelRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Model not found"));
        if (model.getAvgDailyIncreaseWeekday() != null) {
            existing.setAvgDailyIncreaseWeekday(model.getAvgDailyIncreaseWeekday());
        }
        if (model.getAvgDailyIncreaseWeekend() != null) {
            existing.setAvgDailyIncreaseWeekend(model.getAvgDailyIncreaseWeekend());
        }
        return modelRepository.save(existing);
    }

    // Example method to compute daily increase for a given date
    public double getIncreaseForDate(Long binId, LocalDate date) {
        UsagePatternModel model = modelRepository.findTop1ByBinOrderByLastUpdatedDesc(
                new Bin(binId) // assuming constructor sets ID only
        ).orElseThrow(() -> new BadRequestException("No usage model found for bin"));

        return WeekendUtil.isWeekend(date) ? model.getAvgDailyIncreaseWeekend()
                                           : model.getAvgDailyIncreaseWeekday();
    }

    @Override
    public UsagePatternModel getModelForBin(Long binId) {
        return modelRepository.findTop1ByBinOrderByLastUpdatedDesc(new Bin(binId))
                .orElseThrow(() -> new BadRequestException("No usage model found for bin"));
    }
}
