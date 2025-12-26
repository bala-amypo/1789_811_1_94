package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class OverflowPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer daysUntilFull;
    private LocalDate predictedFullDate;

    @ManyToOne
    private Bin bin;

    @ManyToOne
    private UsagePatternModel modelUsed;

    public OverflowPrediction() {}

    public OverflowPrediction(Long id, Integer daysUntilFull, LocalDate predictedFullDate,
                              Bin bin, UsagePatternModel modelUsed) {
        this.id = id;
        this.daysUntilFull = daysUntilFull;
        this.predictedFullDate = predictedFullDate;
        this.bin = bin;
        this.modelUsed = modelUsed;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getDaysUntilFull() { return daysUntilFull; }
    public void setDaysUntilFull(Integer daysUntilFull) { this.daysUntilFull = daysUntilFull; }

    public LocalDate getPredictedFullDate() { return predictedFullDate; }
    public void setPredictedFullDate(LocalDate predictedFullDate) { this.predictedFullDate = predictedFullDate; }

    public Bin getBin() { return bin; }
    public void setBin(Bin bin) { this.bin = bin; }

    public UsagePatternModel getModelUsed() { return modelUsed; }
    public void setModelUsed(UsagePatternModel modelUsed) { this.modelUsed = modelUsed; }
}
