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
@Table(name = "usage_pattern_models")
public class UsagePatternModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bin_id")
    private Bin bin;

    private Double avgDailyIncreaseWeekday;
    private Double avgDailyIncreaseWeekend;
    private Timestamp lastUpdated;

    public UsagePatternModel() {}

    public UsagePatternModel(Bin bin,
                             Double avgDailyIncreaseWeekday,
                             Double avgDailyIncreaseWeekend,
                             Timestamp lastUpdated) {
        this.bin = bin;
        this.avgDailyIncreaseWeekday = avgDailyIncreaseWeekday;
        this.avgDailyIncreaseWeekend = avgDailyIncreaseWeekend;
        this.lastUpdated = lastUpdated;
    }

    public UsagePatternModel(Bin bin,
                             Double avgDailyIncreaseWeekday,
                             Double avgDailyIncreaseWeekend,
                             LocalDateTime lastUpdated) {
        this(bin,
             avgDailyIncreaseWeekday,
             avgDailyIncreaseWeekend,
             Timestamp.valueOf(lastUpdated));
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

    public Double getAvgDailyIncreaseWeekday() {
        return avgDailyIncreaseWeekday;
    }

    public void setAvgDailyIncreaseWeekday(Double avgDailyIncreaseWeekday) {
        this.avgDailyIncreaseWeekday = avgDailyIncreaseWeekday;
    }

    public Double getAvgDailyIncreaseWeekend() {
        return avgDailyIncreaseWeekend;
    }

    public void setAvgDailyIncreaseWeekend(Double avgDailyIncreaseWeekend) {
        this.avgDailyIncreaseWeekend = avgDailyIncreaseWeekend;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
