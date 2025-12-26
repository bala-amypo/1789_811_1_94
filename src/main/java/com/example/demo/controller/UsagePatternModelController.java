package com.example.demo.controller;

import com.example.demo.model.UsagePatternModel;
import com.example.demo.service.UsagePatternModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class UsagePatternModelController {

    private final UsagePatternModelService usagePatternModelService;

    public UsagePatternModelController(UsagePatternModelService usagePatternModelService) {
        this.usagePatternModelService = usagePatternModelService;
    }

    // POST /api/models
    @PostMapping
    public UsagePatternModel createModel(@RequestBody UsagePatternModel model) {
        return usagePatternModelService.createModel(model);
    }

    // PUT /api/models/{id}
    @PutMapping("/{id}")
    public UsagePatternModel updateModel(@PathVariable Long id,
                                         @RequestBody UsagePatternModel model) {
        return usagePatternModelService.updateModel(id, model);
    }

    // GET /api/models/bin/{binId}
    @GetMapping("/bin/{binId}")
    public UsagePatternModel getModelForBin(@PathVariable Long binId) {
        return usagePatternModelService.getModelForBin(binId);
    }

    // GET /api/models
    @GetMapping
    public List<UsagePatternModel> getAllModels() {
        return usagePatternModelService.getAllModels();
    }
}
