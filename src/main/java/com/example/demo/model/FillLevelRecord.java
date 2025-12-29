package com.example.demo.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fill_level_records")
public class FillLevelRecord {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bin_id")
    private Bin bin;

    private Double fillPercentage;
    private Timestamp recordedAt;
    private Boolean isWeekend;

    public FillLevelRecord() {}

    public FillLevelRecord(Bin bin, Double fillPercentage,
                           Timestamp recordedAt, Boolean isWeekend) {
        this.bin = bin;
        this.fillPercentage = fillPercentage;
        this.recordedAt = recordedAt;
        this.isWeekend = isWeekend;
    }

    public FillLevelRecord(Bin bin, Double fillPercentage,
                           LocalDateTime recordedAt, Boolean isWeekend) {
        this(bin, fillPercentage,
             Timestamp.valueOf(recordedAt),
             isWeekend);
    }

    public Timestamp getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Timestamp recordedAt) { this.recordedAt = recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = Timestamp.valueOf(recordedAt);
    }
   
    public Long getId() {
        return id;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public Double getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(Double fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    public Boolean getIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(Boolean isWeekend) {
        this.isWeekend = isWeekend;
    }
}
