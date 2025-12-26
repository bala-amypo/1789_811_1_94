package com.example.demo.service;

import java.util.List;

import com.example.demo.model.UsagePatternModel;

public interface UsagePatternModelService {

    UsagePatternModel createModel(UsagePatternModel model);

    UsagePatternModel updateModel(Long id, UsagePatternModel model);

    UsagePatternModel getModelForBin(Long binId);

    List<UsagePatternModel> getAllModels();
}
