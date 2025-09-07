package com.swasthyasarthi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vital_readings")
public class VitalReading {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_profile_id", nullable = false)
    private PatientProfile patientProfile;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VitalType vitalType;
    
    @Column(name = "systolic_bp")
    private Integer systolicBp;
    
    @Column(name = "diastolic_bp")
    private Integer diastolicBp;
    
    @Column(name = "glucose_value")
    private Integer glucoseValue;
    
    @Column(name = "is_fasting")
    private Boolean isFasting;
    
    @Column(name = "heart_rate")
    private Integer heartRate;
    
    @Column(name = "weight_kg")
    private Double weightKg;
    
    @Column(name = "temperature_celsius")
    private Double temperatureCelsius;
    
    @Column(name = "oxygen_saturation")
    private Integer oxygenSaturation;
    
    @Column(name = "reading_timestamp", nullable = false)
    private LocalDateTime readingTimestamp;
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @Column(name = "is_backdated")
    private Boolean isBackdated = false;
    
    @Column(name = "original_timestamp")
    private LocalDateTime originalTimestamp;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructors
    public VitalReading() {}
    
    public VitalReading(PatientProfile patientProfile, User user, VitalType vitalType, LocalDateTime readingTimestamp) {
        this.patientProfile = patientProfile;
        this.user = user;
        this.vitalType = vitalType;
        this.readingTimestamp = readingTimestamp;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public PatientProfile getPatientProfile() { return patientProfile; }
    public void setPatientProfile(PatientProfile patientProfile) { this.patientProfile = patientProfile; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public VitalType getVitalType() { return vitalType; }
    public void setVitalType(VitalType vitalType) { this.vitalType = vitalType; }
    
    public Integer getSystolicBp() { return systolicBp; }
    public void setSystolicBp(Integer systolicBp) { this.systolicBp = systolicBp; }
    
    public Integer getDiastolicBp() { return diastolicBp; }
    public void setDiastolicBp(Integer diastolicBp) { this.diastolicBp = diastolicBp; }
    
    public Integer getGlucoseValue() { return glucoseValue; }
    public void setGlucoseValue(Integer glucoseValue) { this.glucoseValue = glucoseValue; }
    
    public Boolean getIsFasting() { return isFasting; }
    public void setIsFasting(Boolean isFasting) { this.isFasting = isFasting; }
    
    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }
    
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    
    public Double getTemperatureCelsius() { return temperatureCelsius; }
    public void setTemperatureCelsius(Double temperatureCelsius) { this.temperatureCelsius = temperatureCelsius; }
    
    public Integer getOxygenSaturation() { return oxygenSaturation; }
    public void setOxygenSaturation(Integer oxygenSaturation) { this.oxygenSaturation = oxygenSaturation; }
    
    public LocalDateTime getReadingTimestamp() { return readingTimestamp; }
    public void setReadingTimestamp(LocalDateTime readingTimestamp) { this.readingTimestamp = readingTimestamp; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public Boolean getIsBackdated() { return isBackdated; }
    public void setIsBackdated(Boolean isBackdated) { this.isBackdated = isBackdated; }
    
    public LocalDateTime getOriginalTimestamp() { return originalTimestamp; }
    public void setOriginalTimestamp(LocalDateTime originalTimestamp) { this.originalTimestamp = originalTimestamp; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Utility methods
    public boolean isBloodPressureReading() {
        return vitalType == VitalType.BLOOD_PRESSURE && systolicBp != null && diastolicBp != null;
    }
    
    public boolean isGlucoseReading() {
        return vitalType == VitalType.GLUCOSE && glucoseValue != null;
    }
    
    public boolean isWeightReading() {
        return vitalType == VitalType.WEIGHT && weightKg != null;
    }
    
    public boolean isHeartRateReading() {
        return vitalType == VitalType.HEART_RATE && heartRate != null;
    }
    
    public String getFormattedValue() {
        switch (vitalType) {
            case BLOOD_PRESSURE:
                return systolicBp + "/" + diastolicBp + " mmHg";
            case GLUCOSE:
                return glucoseValue + " mg/dL" + (isFasting ? " (Fasting)" : " (Post-meal)");
            case WEIGHT:
                return weightKg + " kg";
            case HEART_RATE:
                return heartRate + " bpm";
            case TEMPERATURE:
                return temperatureCelsius + "°C";
            case OXYGEN_SATURATION:
                return oxygenSaturation + "%";
            default:
                return "N/A";
        }
    }
}