package com.swasthyasarthi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "encounters")
public class Encounter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "encounter_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EncounterType encounterType;
    
    @Column(name = "encounter_timestamp", nullable = false)
    private LocalDateTime encounterTimestamp;
    
    @Column(name = "symptoms", length = 2000)
    private String symptoms;
    
    @Column(name = "medication_taken")
    private Boolean medicationTaken;
    
    @Column(name = "medication_missed")
    private Boolean medicationMissed;
    
    @Column(name = "activity_minutes")
    private Integer activityMinutes;
    
    @Column(name = "diet_tags", length = 1000)
    private String dietTags; // JSON string of diet-related tags
    
    @Column(name = "mood_rating")
    private Integer moodRating; // 1-5 scale
    
    @Column(name = "sleep_hours")
    private Double sleepHours;
    
    @Column(name = "stress_level")
    private Integer stressLevel; // 1-5 scale
    
    @Column(name = "notes", length = 2000)
    private String notes;
    
    @Column(name = "is_backdated")
    private Boolean isBackdated = false;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructors
    public Encounter() {}
    
    public Encounter(User user, EncounterType encounterType, LocalDateTime encounterTimestamp) {
        this.user = user;
        this.encounterType = encounterType;
        this.encounterTimestamp = encounterTimestamp;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public EncounterType getEncounterType() { return encounterType; }
    public void setEncounterType(EncounterType encounterType) { this.encounterType = encounterType; }
    
    public LocalDateTime getEncounterTimestamp() { return encounterTimestamp; }
    public void setEncounterTimestamp(LocalDateTime encounterTimestamp) { this.encounterTimestamp = encounterTimestamp; }
    
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    
    public Boolean getMedicationTaken() { return medicationTaken; }
    public void setMedicationTaken(Boolean medicationTaken) { this.medicationTaken = medicationTaken; }
    
    public Boolean getMedicationMissed() { return medicationMissed; }
    public void setMedicationMissed(Boolean medicationMissed) { this.medicationMissed = medicationMissed; }
    
    public Integer getActivityMinutes() { return activityMinutes; }
    public void setActivityMinutes(Integer activityMinutes) { this.activityMinutes = activityMinutes; }
    
    public String getDietTags() { return dietTags; }
    public void setDietTags(String dietTags) { this.dietTags = dietTags; }
    
    public Integer getMoodRating() { return moodRating; }
    public void setMoodRating(Integer moodRating) { this.moodRating = moodRating; }
    
    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }
    
    public Integer getStressLevel() { return stressLevel; }
    public void setStressLevel(Integer stressLevel) { this.stressLevel = stressLevel; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public Boolean getIsBackdated() { return isBackdated; }
    public void setIsBackdated(Boolean isBackdated) { this.isBackdated = isBackdated; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Utility methods
    public boolean hasSymptoms() {
        return symptoms != null && !symptoms.trim().isEmpty();
    }
    
    public boolean hasActivity() {
        return activityMinutes != null && activityMinutes > 0;
    }
    
    public boolean hasDietTags() {
        return dietTags != null && !dietTags.trim().isEmpty();
    }
    
    public boolean isMedicationAdherent() {
        return medicationTaken != null && medicationTaken && 
               (medicationMissed == null || !medicationMissed);
    }
}