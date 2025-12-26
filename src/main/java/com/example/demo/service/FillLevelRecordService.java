package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.FillLevelRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface FillLevelRecordService {

    FillLevelRecord createRecord(FillLevelRecord record) throws BadRequestException;

    FillLevelRecord getRecordById(Long id) throws ResourceNotFoundException;

    List<FillLevelRecord> getRecentRecords(Long binId, int limit) throws ResourceNotFoundException;

    List<FillLevelRecord> getRecordsWithinRange(Long binId, LocalDateTime start, LocalDateTime end);
}
