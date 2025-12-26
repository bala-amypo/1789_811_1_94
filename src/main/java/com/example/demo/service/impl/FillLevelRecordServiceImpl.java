package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bin;
import com.example.demo.model.FillLevelRecord;
import com.example.demo.repository.BinRepository;
import com.example.demo.repository.FillLevelRecordRepository;
import com.example.demo.service.FillLevelRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FillLevelRecordServiceImpl implements FillLevelRecordService {

    private final FillLevelRecordRepository recordRepository;
    private final BinRepository binRepository;

    public FillLevelRecordServiceImpl(FillLevelRecordRepository recordRepository, BinRepository binRepository) {
        this.recordRepository = recordRepository;
        this.binRepository = binRepository;
    }

    @Override
    public FillLevelRecord createRecord(FillLevelRecord record) throws BadRequestException {
        Bin bin = binRepository.findById(record.getBin().getId())
                .orElseThrow(() -> new BadRequestException("Bin not found"));
        if (!bin.getActive()) throw new BadRequestException("Cannot add record to inactive bin");
        if (record.getRecordedAt().isAfter(LocalDateTime.now()))
            throw new BadRequestException("Recorded time cannot be in the future");
        record.setBin(bin);
        return recordRepository.save(record);
    }

    @Override
    public FillLevelRecord getRecordById(Long id) throws ResourceNotFoundException {
        return recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
    }

    @Override
    public List<FillLevelRecord> getRecentRecords(Long binId, int limit) throws ResourceNotFoundException {
        Bin bin = binRepository.findById(binId)
                .orElseThrow(() -> new ResourceNotFoundException("Bin not found"));
        List<FillLevelRecord> list = recordRepository.findByBinOrderByRecordedAtDesc(bin);
        return list.subList(0, Math.min(limit, list.size()));
    }

    @Override
    public List<FillLevelRecord> getRecordsWithinRange(Long binId, LocalDateTime start, LocalDateTime end) {
        Bin bin = binRepository.findById(binId).orElseThrow(() -> new RuntimeException("Bin not found"));
        return recordRepository.findByBinAndRecordedAtBetween(bin, start, end);
    }
}
