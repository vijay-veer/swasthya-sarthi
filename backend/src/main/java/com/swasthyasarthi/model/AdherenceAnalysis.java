package com.swasthyasarthi.model;

public class AdherenceAnalysis {
    private double medicationAdherenceRate;
    private boolean missingCriticalDoses;
    private boolean loggingInconsistency;
    private String adherenceSummary;
    
    // Constructors
    public AdherenceAnalysis() {}
    
    // Getters and Setters
    public double getMedicationAdherenceRate() { return medicationAdherenceRate; }
    public void setMedicationAdherenceRate(double medicationAdherenceRate) { this.medicationAdherenceRate = medicationAdherenceRate; }
    
    public boolean isMissingCriticalDoses() { return missingCriticalDoses; }
    public void setMissingCriticalDoses(boolean missingCriticalDoses) { this.missingCriticalDoses = missingCriticalDoses; }
    
    public boolean isLoggingInconsistency() { return loggingInconsistency; }
    public void setLoggingInconsistency(boolean loggingInconsistency) { this.loggingInconsistency = loggingInconsistency; }
    
    public String getAdherenceSummary() { return adherenceSummary; }
    public void setAdherenceSummary(String adherenceSummary) { this.adherenceSummary = adherenceSummary; }
    
    // Utility methods
    public boolean needsIntervention() {
        return medicationAdherenceRate < 0.8 || missingCriticalDoses || loggingInconsistency;
    }
}