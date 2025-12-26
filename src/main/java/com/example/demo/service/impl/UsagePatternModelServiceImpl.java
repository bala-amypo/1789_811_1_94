package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;
import com.example.demo.model.UsagePatternModel;
import com.example.demo.repository.BinRepository;
import com.example.demo.repository.UsagePatternModelRepository;
import com.example.demo.service.UsagePatternModelService;

@Service
public class UsagePatternModelServiceImpl implements UsagePatternModelService {

    private final UsagePatternModelRepository modelRepository;
    private final BinRepository binRepository;

    public UsagePatternModelServiceImpl(UsagePatternModelRepository modelRepository,
                                        BinRepository binRepository) {
        this.modelRepository = modelRepository;
        this.binRepository = binRepository;
    }

    @Override
    public UsagePatternModel createModel(UsagePatternModel model) {

        if (model.getAvgDailyIncreaseWeekday() < 0 ||
                model.getAvgDailyIncreaseWeekend() < 0) {
            throw new BadRequestException("Daily increase values must be non-negative");
        }

        Bin bin = binRepository.findById(model.getBin().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bin not found"));

        model.setBin(bin);
        model.setLastUpdated(Timestamp.from(Instant.now()));

        return modelRepository.save(model);
    }

    @Override
    public UsagePatternModel updateModel(Long id, UsagePatternModel model) {

        UsagePatternModel existing = modelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("UsagePatternModel not found"));

        if (model.getAvgDailyIncreaseWeekday() < 0 ||
                model.getAvgDailyIncreaseWeekend() < 0) {
            throw new BadRequestException("Daily increase values must be non-negative");
        }

        existing.setAvgDailyIncreaseWeekday(model.getAvgDailyIncreaseWeekday());
        existing.setAvgDailyIncreaseWeekend(model.getAvgDailyIncreaseWeekend());
        existing.setLastUpdated(Timestamp.from(Instant.now()));

        return modelRepository.save(existing);
    }

    @Override
    public UsagePatternModel getModelForBin(Long binId) {

        Bin bin = binRepository.findById(binId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bin not found"));

        return modelRepository.findTop1ByBinOrderByLastUpdatedDesc(bin)
                .orElseThrow(() ->
                        new ResourceNotFoundException("UsagePatternModel not found"));
    }

    @Override
    public List<UsagePatternModel> getAllModels() {
        return modelRepository.findAll();
    }
}
