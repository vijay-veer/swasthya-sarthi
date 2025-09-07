package com.swasthyasarthi.model;

import java.time.LocalDate;
import java.util.List;

public class DigitalTwinSnapshot {
    private PatientProfile patientProfile;
    private LocalDate observationDate;
    private List<VitalReading> recentVitals;
    private List<Encounter> recentEncounters;
    private List<CrrsScore> recentCrrsScores;
    
    // Constructors
    public DigitalTwinSnapshot() {}
    
    // Getters and Setters
    public PatientProfile getPatientProfile() { return patientProfile; }
    public void setPatientProfile(PatientProfile patientProfile) { this.patientProfile = patientProfile; }
    
    public LocalDate getObservationDate() { return observationDate; }
    public void setObservationDate(LocalDate observationDate) { this.observationDate = observationDate; }
    
    public List<VitalReading> getRecentVitals() { return recentVitals; }
    public void setRecentVitals(List<VitalReading> recentVitals) { this.recentVitals = recentVitals; }
    
    public List<Encounter> getRecentEncounters() { return recentEncounters; }
    public void setRecentEncounters(List<Encounter> recentEncounters) { this.recentEncounters = recentEncounters; }
    
    public List<CrrsScore> getRecentCrrsScores() { return recentCrrsScores; }
    public void setRecentCrrsScores(List<CrrsScore> recentCrrsScores) { this.recentCrrsScores = recentCrrsScores; }
}