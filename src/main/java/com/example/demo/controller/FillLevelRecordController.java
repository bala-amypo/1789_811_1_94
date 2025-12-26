package com.example.demo.controller;

import com.example.demo.model.FillLevelRecord;
import com.example.demo.service.FillLevelRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fill-records")
public class FillLevelRecordController {

    private final FillLevelRecordService fillLevelRecordService;

    public FillLevelRecordController(FillLevelRecordService fillLevelRecordService) {
        this.fillLevelRecordService = fillLevelRecordService;
    }

    // POST /api/fill-records
    @PostMapping
    public FillLevelRecord createRecord(@RequestBody FillLevelRecord record) {
        return fillLevelRecordService.createRecord(record);
    }

    // GET /api/fill-records/{id}
    @GetMapping("/{id}")
    public FillLevelRecord getRecord(@PathVariable Long id) {
        return fillLevelRecordService.getRecordById(id);
    }

    // GET /api/fill-records/bin/{binId}
    @GetMapping("/bin/{binId}")
    public List<FillLevelRecord> getRecordsForBin(@PathVariable Long binId) {
        return fillLevelRecordService.getRecordsForBin(binId);
    }

    // GET /api/fill-records/bin/{binId}/recent?limit=5
    @GetMapping("/bin/{binId}/recent")
    public List<FillLevelRecord> getRecentRecords(@PathVariable Long binId,
                                                  @RequestParam int limit) {
        return fillLevelRecordService.getRecentRecordsForBin(binId, limit);
    }
}
