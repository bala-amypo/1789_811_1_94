package com.example.demo.controller;

import com.example.demo.model.FillLevelRecord;
import com.example.demo.service.impl.FillLevelRecordServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class FillLevelRecordController {

    private final FillLevelRecordServiceImpl recordService;

    public FillLevelRecordController(FillLevelRecordServiceImpl recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public FillLevelRecord createRecord(@RequestBody FillLevelRecord record) {
        return recordService.createRecord(record);
    }

    @GetMapping("/{id}")
    public FillLevelRecord getRecord(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }

    @GetMapping("/bin/{binId}")
    public List<FillLevelRecord> getRecentRecords(
            @PathVariable Long binId,
            @RequestParam(defaultValue = "5") int limit) {
        return recordService.getRecentRecords(binId, limit);
    }
}
