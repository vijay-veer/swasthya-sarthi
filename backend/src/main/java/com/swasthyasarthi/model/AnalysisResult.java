package com.swasthyasarthi.model;

import java.time.LocalDate;
import java.util.List;

public class AnalysisResult {
    private PatientProfile patientProfile;
    private LocalDate analysisDate;
    private VitalTrendAnalysis vitalTrendAnalysis;
    private LifestyleAnalysis lifestyleAnalysis;
    private AdherenceAnalysis adherenceAnalysis;
    private List<Anomaly> anomalies;
    
    // Constructors
    public AnalysisResult() {}
    
    // Getters and Setters
    public PatientProfile getPatientProfile() { return patientProfile; }
    public void setPatientProfile(PatientProfile patientProfile) { this.patientProfile = patientProfile; }
    
    public LocalDate getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(LocalDate analysisDate) { this.analysisDate = analysisDate; }
    
    public VitalTrendAnalysis getVitalTrendAnalysis() { return vitalTrendAnalysis; }
    public void setVitalTrendAnalysis(VitalTrendAnalysis vitalTrendAnalysis) { this.vitalTrendAnalysis = vitalTrendAnalysis; }
    
    public LifestyleAnalysis getLifestyleAnalysis() { return lifestyleAnalysis; }
    public void setLifestyleAnalysis(LifestyleAnalysis lifestyleAnalysis) { this.lifestyleAnalysis = lifestyleAnalysis; }
    
    public AdherenceAnalysis getAdherenceAnalysis() { return adherenceAnalysis; }
    public void setAdherenceAnalysis(AdherenceAnalysis adherenceAnalysis) { this.adherenceAnalysis = adherenceAnalysis; }
    
    public List<Anomaly> getAnomalies() { return anomalies; }
    public void setAnomalies(List<Anomaly> anomalies) { this.anomalies = anomalies; }
}