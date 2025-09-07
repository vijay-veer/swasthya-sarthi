package com.swasthyasarthi.model;

import java.time.LocalDateTime;

public class Anomaly {
    private AnomalyType type;
    private AnomalySeverity severity;
    private String description;
    private LocalDateTime detectedAt;
    private String context;
    private Double value;
    private Double threshold;
    
    // Constructors
    public Anomaly() {}
    
    public Anomaly(AnomalyType type, AnomalySeverity severity, String description) {
        this.type = type;
        this.severity = severity;
        this.description = description;
        this.detectedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public AnomalyType getType() { return type; }
    public void setType(AnomalyType type) { this.type = type; }
    
    public AnomalySeverity getSeverity() { return severity; }
    public void setSeverity(AnomalySeverity severity) { this.severity = severity; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }
    
    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
    
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    
    public Double getThreshold() { return threshold; }
    public void setThreshold(Double threshold) { this.threshold = threshold; }
}