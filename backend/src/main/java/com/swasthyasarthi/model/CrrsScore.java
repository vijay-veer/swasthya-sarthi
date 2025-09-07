package com.swasthyasarthi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "crrs_scores")
public class CrrsScore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_profile_id", nullable = false)
    private PatientProfile patientProfile;
    
    @Column(name = "score_date", nullable = false)
    private LocalDate scoreDate;
    
    @Column(name = "crrs_value", nullable = false)
    private Double crrsValue;
    
    @Column(name = "previous_crrs")
    private Double previousCrrs;
    
    @Column(name = "delta_crrs")
    private Double deltaCrrs;
    
    @Column(name = "risk_tier", nullable = false)
    @Enumerated(EnumType.STRING)
    private RiskTier riskTier;
    
    @Column(name = "vitals_contribution")
    private Double vitalsContribution;
    
    @Column(name = "lifestyle_contribution")
    private Double lifestyleContribution;
    
    @Column(name = "adherence_contribution")
    private Double adherenceContribution;
    
    @Column(name = "symptoms_contribution")
    private Double symptomsContribution;
    
    @Column(name = "trend_contribution")
    private Double trendContribution;
    
    @Column(name = "explanation", length = 2000)
    private String explanation;
    
    @Column(name = "calculation_details", length = 5000)
    private String calculationDetails;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructors
    public CrrsScore() {}
    
    public CrrsScore(PatientProfile patientProfile, LocalDate scoreDate, Double crrsValue, RiskTier riskTier) {
        this.patientProfile = patientProfile;
        this.scoreDate = scoreDate;
        this.crrsValue = crrsValue;
        this.riskTier = riskTier;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public PatientProfile getPatientProfile() { return patientProfile; }
    public void setPatientProfile(PatientProfile patientProfile) { this.patientProfile = patientProfile; }
    
    public LocalDate getScoreDate() { return scoreDate; }
    public void setScoreDate(LocalDate scoreDate) { this.scoreDate = scoreDate; }
    
    public Double getCrrsValue() { return crrsValue; }
    public void setCrrsValue(Double crrsValue) { this.crrsValue = crrsValue; }
    
    public Double getPreviousCrrs() { return previousCrrs; }
    public void setPreviousCrrs(Double previousCrrs) { this.previousCrrs = previousCrrs; }
    
    public Double getDeltaCrrs() { return deltaCrrs; }
    public void setDeltaCrrs(Double deltaCrrs) { this.deltaCrrs = deltaCrrs; }
    
    public RiskTier getRiskTier() { return riskTier; }
    public void setRiskTier(RiskTier riskTier) { this.riskTier = riskTier; }
    
    public Double getVitalsContribution() { return vitalsContribution; }
    public void setVitalsContribution(Double vitalsContribution) { this.vitalsContribution = vitalsContribution; }
    
    public Double getLifestyleContribution() { return lifestyleContribution; }
    public void setLifestyleContribution(Double lifestyleContribution) { this.lifestyleContribution = lifestyleContribution; }
    
    public Double getAdherenceContribution() { return adherenceContribution; }
    public void setAdherenceContribution(Double adherenceContribution) { this.adherenceContribution = adherenceContribution; }
    
    public Double getSymptomsContribution() { return symptomsContribution; }
    public void setSymptomsContribution(Double symptomsContribution) { this.symptomsContribution = symptomsContribution; }
    
    public Double getTrendContribution() { return trendContribution; }
    public void setTrendContribution(Double trendContribution) { this.trendContribution = trendContribution; }
    
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    
    public String getCalculationDetails() { return calculationDetails; }
    public void setCalculationDetails(String calculationDetails) { this.calculationDetails = calculationDetails; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Utility methods
    public boolean isImproving() {
        return deltaCrrs != null && deltaCrrs < 0;
    }
    
    public boolean isWorsening() {
        return deltaCrrs != null && deltaCrrs > 0;
    }
    
    public boolean isStable() {
        return deltaCrrs != null && Math.abs(deltaCrrs) < 1.0;
    }
    
    public String getTrendDirection() {
        if (isImproving()) return "Improving";
        if (isWorsening()) return "Worsening";
        if (isStable()) return "Stable";
        return "Unknown";
    }
    
    public String getRiskTierDescription() {
        switch (riskTier) {
            case LOW:
                return "Low Risk - Continue healthy habits";
            case MODERATE:
                return "Moderate Risk - Focus on lifestyle improvements";
            case HIGH:
                return "High Risk - Consult healthcare provider";
            case CRITICAL:
                return "Critical Risk - Immediate medical attention needed";
            default:
                return "Unknown Risk Level";
        }
    }
}