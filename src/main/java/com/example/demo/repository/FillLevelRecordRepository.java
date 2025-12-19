package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Bin;
import com.example.demo.model.FillLevelRecord;
@Repository
public interface FillLevelRecordRepository extends JpaRepository<FillLevelRecord, Long> {
    List<FillLevelRecord> findByBinOrderByRecordedAtDesc(Bin bin);
    FillLevelRecord findTop1ByBinOrderByRecordedAtDesc(Bin bin);
    List<FillLevelRecord> findByBinAndRecordedAtBetween(
        Bin bin, LocalDateTime start, LocalDateTime end
    );
}
