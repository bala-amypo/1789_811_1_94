package com.example.demo.controller;

import com.example.demo.model.UsagePatternModel;
import com.example.demo.service.impl.UsagePatternModelServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/models")
public class UsagePatternModelController {

    private final UsagePatternModelServiceImpl modelService;

    public UsagePatternModelController(UsagePatternModelServiceImpl modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    public UsagePatternModel createModel(@RequestBody UsagePatternModel model) {
        return modelService.createModel(model);
    }

    @PutMapping("/{id}")
    public UsagePatternModel updateModel(
            @PathVariable Long id,
            @RequestBody UsagePatternModel model) {
        return modelService.updateModel(id, model);
    }

    @GetMapping("/bin/{binId}")
    public UsagePatternModel getModelForBin(@PathVariable Long binId) {
        return modelService.getModelForBin(binId);
    }
}
