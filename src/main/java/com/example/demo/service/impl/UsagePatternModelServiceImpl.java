package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;

@Service
public class UsagePatternModelServiceImpl {

    private final UsagePatternModelRepository repo;
    private final BinRepository binRepo;

    public UsagePatternModelServiceImpl(
            UsagePatternModelRepository repo,
            BinRepository binRepo) {
        this.repo = repo;
        this.binRepo = binRepo;
    }

    public UsagePatternModel createModel(UsagePatternModel model) {
        if (model.getAvgDailyIncreaseWeekday() < 0)
            throw new BadRequestException("Negative increase");

        Bin bin = binRepo.findById(model.getBin().getId()).orElseThrow();
        model.setBin(bin);
        return repo.save(model);
    }

    public UsagePatternModel updateModel(Long id, UsagePatternModel update) {
        UsagePatternModel m = repo.findById(id).orElseThrow();
        if (update.getAvgDailyIncreaseWeekend() != null)
            m.setAvgDailyIncreaseWeekend(update.getAvgDailyIncreaseWeekend());
        return repo.save(m);
    }

    public UsagePatternModel getModelForBin(Long binId) {
        Bin bin = binRepo.findById(binId).orElseThrow();
        return repo.findTop1ByBinOrderByLastUpdatedDesc(bin).orElseThrow();
    }
}
