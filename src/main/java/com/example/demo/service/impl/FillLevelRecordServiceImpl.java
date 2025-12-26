package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.exception.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FillLevelRecordServiceImpl {

    private final FillLevelRecordRepository repo;
    private final BinRepository binRepo;

    public FillLevelRecordServiceImpl(FillLevelRecordRepository repo, BinRepository binRepo) {
        this.repo = repo;
        this.binRepo = binRepo;
    }

    public FillLevelRecord createRecord(FillLevelRecord record) {
        Bin bin = binRepo.findById(record.getBin().getId())
                .orElseThrow(() -> new BadRequestException("Bin not found"));

        if (!bin.getActive())
            throw new BadRequestException("Bin inactive");

        if (record.getRecordedAt().isAfter(LocalDateTime.now()))
            throw new BadRequestException("Future date");

        return repo.save(record);
    }

    public FillLevelRecord getRecordById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
    }

    public List<FillLevelRecord> getRecentRecords(Long binId, int limit) {
        Bin bin = binRepo.findById(binId).orElseThrow();
        List<FillLevelRecord> list = repo.findByBinOrderByRecordedAtDesc(bin);
        return list.subList(0, Math.min(limit, list.size()));
    }
}
