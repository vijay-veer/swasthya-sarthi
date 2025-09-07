package com.swasthyasarthi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patient_profiles")
public class PatientProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "diabetes_diagnosis_date")
    private LocalDate diabetesDiagnosisDate;
    
    @Column(name = "hypertension_diagnosis_date")
    private LocalDate hypertensionDiagnosisDate;
    
    @Column(name = "family_history_diabetes")
    private Boolean familyHistoryDiabetes = false;
    
    @Column(name = "family_history_hypertension")
    private Boolean familyHistoryHypertension = false;
    
    @Column(name = "family_history_heart_disease")
    private Boolean familyHistoryHeartDisease = false;
    
    @Column(name = "known_allergies", length = 1000)
    private String knownAllergies;
    
    @Column(name = "current_medications", length = 2000)
    private String currentMedications;
    
    // Target ranges for vitals
    @Column(name = "target_systolic_bp_min")
    private Integer targetSystolicBpMin = 120;
    
    @Column(name = "target_systolic_bp_max")
    private Integer targetSystolicBpMax = 140;
    
    @Column(name = "target_diastolic_bp_min")
    private Integer targetDiastolicBpMin = 80;
    
    @Column(name = "target_diastolic_bp_max")
    private Integer targetDiastolicBpMax = 90;
    
    @Column(name = "target_fasting_glucose_min")
    private Integer targetFastingGlucoseMin = 80;
    
    @Column(name = "target_fasting_glucose_max")
    private Integer targetFastingGlucoseMax = 130;
    
    @Column(name = "target_post_meal_glucose_min")
    private Integer targetPostMealGlucoseMin = 100;
    
    @Column(name = "target_post_meal_glucose_max")
    private Integer targetPostMealGlucoseMax = 180;
    
    @Column(name = "target_weight_kg")
    private Double targetWeightKg;
    
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;
    
    @Column(name = "emergency_contact_relationship")
    private String emergencyContactRelationship;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "patientProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VitalReading> vitalReadings = new HashSet<>();
    
    @OneToMany(mappedBy = "patientProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CrrsScore> crrsScores = new HashSet<>();
    
    // Constructors
    public PatientProfile() {}
    
    public PatientProfile(User user) {
        this.user = user;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDate getDiabetesDiagnosisDate() { return diabetesDiagnosisDate; }
    public void setDiabetesDiagnosisDate(LocalDate diabetesDiagnosisDate) { this.diabetesDiagnosisDate = diabetesDiagnosisDate; }
    
    public LocalDate getHypertensionDiagnosisDate() { return hypertensionDiagnosisDate; }
    public void setHypertensionDiagnosisDate(LocalDate hypertensionDiagnosisDate) { this.hypertensionDiagnosisDate = hypertensionDiagnosisDate; }
    
    public Boolean getFamilyHistoryDiabetes() { return familyHistoryDiabetes; }
    public void setFamilyHistoryDiabetes(Boolean familyHistoryDiabetes) { this.familyHistoryDiabetes = familyHistoryDiabetes; }
    
    public Boolean getFamilyHistoryHypertension() { return familyHistoryHypertension; }
    public void setFamilyHistoryHypertension(Boolean familyHistoryHypertension) { this.familyHistoryHypertension = familyHistoryHypertension; }
    
    public Boolean getFamilyHistoryHeartDisease() { return familyHistoryHeartDisease; }
    public void setFamilyHistoryHeartDisease(Boolean familyHistoryHeartDisease) { this.familyHistoryHeartDisease = familyHistoryHeartDisease; }
    
    public String getKnownAllergies() { return knownAllergies; }
    public void setKnownAllergies(String knownAllergies) { this.knownAllergies = knownAllergies; }
    
    public String getCurrentMedications() { return currentMedications; }
    public void setCurrentMedications(String currentMedications) { this.currentMedications = currentMedications; }
    
    public Integer getTargetSystolicBpMin() { return targetSystolicBpMin; }
    public void setTargetSystolicBpMin(Integer targetSystolicBpMin) { this.targetSystolicBpMin = targetSystolicBpMin; }
    
    public Integer getTargetSystolicBpMax() { return targetSystolicBpMax; }
    public void setTargetSystolicBpMax(Integer targetSystolicBpMax) { this.targetSystolicBpMax = targetSystolicBpMax; }
    
    public Integer getTargetDiastolicBpMin() { return targetDiastolicBpMin; }
    public void setTargetDiastolicBpMin(Integer targetDiastolicBpMin) { this.targetDiastolicBpMin = targetDiastolicBpMin; }
    
    public Integer getTargetDiastolicBpMax() { return targetDiastolicBpMax; }
    public void setTargetDiastolicBpMax(Integer targetDiastolicBpMax) { this.targetDiastolicBpMax = targetDiastolicBpMax; }
    
    public Integer getTargetFastingGlucoseMin() { return targetFastingGlucoseMin; }
    public void setTargetFastingGlucoseMin(Integer targetFastingGlucoseMin) { this.targetFastingGlucoseMin = targetFastingGlucoseMin; }
    
    public Integer getTargetFastingGlucoseMax() { return targetFastingGlucoseMax; }
    public void setTargetFastingGlucoseMax(Integer targetFastingGlucoseMax) { this.targetFastingGlucoseMax = targetFastingGlucoseMax; }
    
    public Integer getTargetPostMealGlucoseMin() { return targetPostMealGlucoseMin; }
    public void setTargetPostMealGlucoseMin(Integer targetPostMealGlucoseMin) { this.targetPostMealGlucoseMin = targetPostMealGlucoseMin; }
    
    public Integer getTargetPostMealGlucoseMax() { return targetPostMealGlucoseMax; }
    public void setTargetPostMealGlucoseMax(Integer targetPostMealGlucoseMax) { this.targetPostMealGlucoseMax = targetPostMealGlucoseMax; }
    
    public Double getTargetWeightKg() { return targetWeightKg; }
    public void setTargetWeightKg(Double targetWeightKg) { this.targetWeightKg = targetWeightKg; }
    
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    
    public String getEmergencyContactRelationship() { return emergencyContactRelationship; }
    public void setEmergencyContactRelationship(String emergencyContactRelationship) { this.emergencyContactRelationship = emergencyContactRelationship; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Set<VitalReading> getVitalReadings() { return vitalReadings; }
    public void setVitalReadings(Set<VitalReading> vitalReadings) { this.vitalReadings = vitalReadings; }
    
    public Set<CrrsScore> getCrrsScores() { return crrsScores; }
    public void setCrrsScores(Set<CrrsScore> crrsScores) { this.crrsScores = crrsScores; }
    
    // Utility methods
    public boolean hasDiabetes() {
        return diabetesDiagnosisDate != null;
    }
    
    public boolean hasHypertension() {
        return hypertensionDiagnosisDate != null;
    }
    
    public boolean isInTargetRange(Integer systolicBp, Integer diastolicBp) {
        return systolicBp >= targetSystolicBpMin && systolicBp <= targetSystolicBpMax &&
               diastolicBp >= targetDiastolicBpMin && diastolicBp <= targetDiastolicBpMax;
    }
    
    public boolean isGlucoseInTargetRange(Integer glucose, boolean isFasting) {
        if (isFasting) {
            return glucose >= targetFastingGlucoseMin && glucose <= targetFastingGlucoseMax;
        } else {
            return glucose >= targetPostMealGlucoseMin && glucose <= targetPostMealGlucoseMax;
        }
    }
}