package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FillLevelRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double fillPercentage;
    private LocalDateTime recordedAt;

    @ManyToOne
    private Bin bin;

    public FillLevelRecord() {}

    public FillLevelRecord(Long id, Double fillPercentage, LocalDateTime recordedAt, Bin bin) {
        this.id = id;
        this.fillPercentage = fillPercentage;
        this.recordedAt = recordedAt;
        this.bin = bin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getFillPercentage() { return fillPercentage; }
    public void setFillPercentage(Double fillPercentage) { this.fillPercentage = fillPercentage; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }

    public Bin getBin() { return bin; }
    public void setBin(Bin bin) { this.bin = bin; }
}
