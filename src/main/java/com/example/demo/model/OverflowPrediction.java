package com.example.demo.model;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "overflow_predictions")
public class OverflowPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne
    // @JoinColumn(name = "bin_id", nullable = false)
    private Bin bin;

    private Date predictedFullDate;

    private Integer daysUntilFull;

    // @ManyToOne
    // @JoinColumn(name = "model_id")
    private UsagePatternModel modelUsed;

    private Timestamp generatedAt;

    @PrePersist
    protected void onCreate() {
        generatedAt = new Timestamp(System.currentTimeMillis());
    }
    

    public OverflowPrediction() {
    }


    public OverflowPrediction(Long id, Bin bin, Date predictedFullDate, Integer daysUntilFull,
            UsagePatternModel modelUsed, Timestamp generatedAt) {
        this.id = id;
        this.bin = bin;
        this.predictedFullDate = predictedFullDate;
        this.daysUntilFull = daysUntilFull;
        this.modelUsed = modelUsed;
        this.generatedAt = generatedAt;
    }


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

    public void setPredictedFullDate(Date predictedFullDate) {
        this.predictedFullDate = predictedFullDate;
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

