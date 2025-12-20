package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.OverflowPrediction;
import com.example.demo.model.Zone;

public interface OverflowPredictionRepository
        extends JpaRepository<OverflowPrediction, Long> {

    @Query("""
        SELECT p FROM OverflowPrediction p
        WHERE p.bin.zone = :zone
        AND p.generatedAt = (
            SELECT MAX(p2.generatedAt)
            FROM OverflowPrediction p2
            WHERE p2.bin = p.bin
        )
    """)
    List<OverflowPrediction> findLatestPredictionsForZone(
            @Param("zone") Zone zone
    );
}
