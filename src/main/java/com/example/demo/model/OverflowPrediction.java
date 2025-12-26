package com.example.demo.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "overflow_predictions")
public class OverflowPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bin_id")
    private Bin bin;

    private Date predictedFullDate;
    private Integer daysUntilFull;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private UsagePatternModel modelUsed;

    private Timestamp generatedAt;

    public OverflowPrediction() {}

    // Standard constructor
    public OverflowPrediction(Bin bin, Date predictedFullDate,
                              Integer daysUntilFull,
                              UsagePatternModel modelUsed,
                              Timestamp generatedAt) {
        this.bin = bin;
        this.predictedFullDate = predictedFullDate;
        this.daysUntilFull = daysUntilFull;
        this.modelUsed = modelUsed;
        this.generatedAt = generatedAt;
    }

    // LocalDateTime constructor for modern API support
    public OverflowPrediction(Bin bin, LocalDateTime predictedFullDate,
                              Integer daysUntilFull,
                              UsagePatternModel modelUsed,
                              LocalDateTime generatedAt) {
        this(
            bin,
            Date.from(predictedFullDate.atZone(ZoneId.systemDefault()).toInstant()),
            daysUntilFull,
            modelUsed,
            Timestamp.valueOf(generatedAt)
        );
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public Date getPredictedFullDate() {
        return predictedFullDate;
    }

    // Standard Setter (for java.util.Date)
    public void setPredictedFullDate(Date predictedFullDate) {
        this.predictedFullDate = predictedFullDate;
    }

    /**
     * ✅ FIX: Overloaded Setter (for java.time.LocalDate)
     * This allows your TestNG file to pass a LocalDate directly 
     * without causing a compilation error.
     */
    public void setPredictedFullDate(LocalDate localDate) {
        if (localDate == null) {
            this.predictedFullDate = null;
        } else {
            // Conversion logic
            this.predictedFullDate = java.sql.Date.valueOf(localDate);
        }
    }

    public Integer getDaysUntilFull() {
        return daysUntilFull;
    }

    public void setDaysUntilFull(Integer daysUntilFull) {
        this.daysUntilFull = daysUntilFull;
    }

    public UsagePatternModel getModelUsed() {
        return modelUsed;
    }

    public void setModelUsed(UsagePatternModel modelUsed) {
        this.modelUsed = modelUsed;
    }

    public Timestamp getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Timestamp generatedAt) {
        this.generatedAt = generatedAt;
    }
}