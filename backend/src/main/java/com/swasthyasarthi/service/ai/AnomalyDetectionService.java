package com.swasthyasarthi.service.ai;

import com.swasthyasarthi.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnomalyDetectionService {
    
    /**
     * Detect anomalies in patient data
     */
    public List<Anomaly> detectAnomalies(DigitalTwinSnapshot snapshot, PatientProfile patientProfile) {
        List<Anomaly> anomalies = new ArrayList<>();
        
        // Check vital readings for anomalies
        for (VitalReading reading : snapshot.getRecentVitals()) {
            anomalies.addAll(detectVitalAnomalies(reading, patientProfile));
        }
        
        // Check encounters for anomalies
        for (Encounter encounter : snapshot.getRecentEncounters()) {
            anomalies.addAll(detectEncounterAnomalies(encounter));
        }
        
        // Check trends for anomalies
        anomalies.addAll(detectTrendAnomalies(snapshot, patientProfile));
        
        return anomalies;
    }
    
    private List<Anomaly> detectVitalAnomalies(VitalReading reading, PatientProfile profile) {
        List<Anomaly> anomalies = new ArrayList<>();
        
        switch (reading.getVitalType()) {
            case BLOOD_PRESSURE:
                anomalies.addAll(detectBloodPressureAnomalies(reading, profile));
                break;
            case GLUCOSE:
                anomalies.addAll(detectGlucoseAnomalies(reading, profile));
                break;
            case HEART_RATE:
                anomalies.addAll(detectHeartRateAnomalies(reading));
                break;
            case WEIGHT:
                anomalies.addAll(detectWeightAnomalies(reading, profile));
                break;
        }
        
        return anomalies;
    }
    
    private List<Anomaly> detectBloodPressureAnomalies(VitalReading reading, PatientProfile profile) {
        List<Anomaly> anomalies = new ArrayList<>();
        
        if (reading.getSystolicBp() == null || reading.getDiastolicBp() == null) {
            return anomalies;
        }
        
        Integer sbp = reading.getSystolicBp();
        Integer dbp = reading.getDiastolicBp();
        
        // Critical thresholds
        if (sbp >= 180 || dbp >= 110) {
            anomalies.add(new Anomaly(
                AnomalyType.BLOOD_PRESSURE_HIGH,
                AnomalySeverity.CRITICAL,
                String.format("Critical blood pressure: %d/%d mmHg", sbp, dbp)
            ));
        }
        // High thresholds
        else if (sbp >= 160 || dbp >= 100) {
            anomalies.add(new Anomaly(
                AnomalyType.BLOOD_PRESSURE_HIGH,
                AnomalySeverity.HIGH,
                String.format("High blood pressure: %d/%d mmHg", sbp, dbp)
            ));
        }
        // Moderate elevation
        else if (sbp >= 140 || dbp >= 90) {
            anomalies.add(new Anomaly(
                AnomalyType.BLOOD_PRESSURE_HIGH,
                AnomalySeverity.MEDIUM,
                String.format("Elevated blood pressure: %d/%d mmHg", sbp, dbp)
            ));
        }
        // Low blood pressure
        else if (sbp < 90 || dbp < 60) {
            anomalies.add(new Anomaly(
                AnomalyType.BLOOD_PRESSURE_LOW,
                AnomalySeverity.MEDIUM,
                String.format("Low blood pressure: %d/%d mmHg", sbp, dbp)
            ));
        }
        
        return anomalies;
    }
    
    private List<Anomaly> detectGlucoseAnomalies(VitalReading reading, PatientProfile profile) {
        List<Anomaly> anomalies = new ArrayList<>();
        
        if (reading.getGlucoseValue() == null) {
            return anomalies;
        }
        
        Integer glucose = reading.getGlucoseValue();
        boolean isFasting = reading.getIsFasting() != null && reading.getIsFasting();
        
        // Critical thresholds
        if (glucose < 70) {
            anomalies.add(new Anomaly(
                AnomalyType.GLUCOSE_LOW,
                AnomalySeverity.CRITICAL,
                String.format("Critical low glucose: %d mg/dL", glucose)
            ));
        } else if (glucose > 300) {
            anomalies.add(new Anomaly(
                AnomalyType.GLUCOSE_HIGH,
                AnomalySeverity.CRITICAL,
                String.format("Critical high glucose: %d mg/dL", glucose)
            ));
        }
        // High thresholds
        else if (glucose > 250) {
            anomalies.add(new Anomaly(
                AnomalyType.GLUCOSE_HIGH,
                AnomalySeverity.HIGH,
                String.format("High glucose: %d mg/dL", glucose)
            ));
        }
        // Moderate elevation
        else if ((isFasting && glucose > profile.getTargetFastingGlucoseMax()) ||
                 (!isFasting && glucose > profile.getTargetPostMealGlucoseMax())) {
            anomalies.add(new Anomaly(
                AnomalyType.GLUCOSE_HIGH,
                AnomalySeverity.MEDIUM,
                String.format("Elevated glucose: %d mg/dL (%s)", glucose, isFasting ? "Fasting" : "Post-meal")
            ));
        }
        
        return anomalies;
    }
    
    private List<Anomaly> detectHeartRateAnomalies(VitalReading reading) {
        List<Anomaly> anomalies = new ArrayList<>();
        
        if (reading.getHeartRate() == null) {
            return anomalies;
        }
        
        Integer hr = reading.getHeartRate();
        
        if (hr < 50 || hr > 120) {
            anomalies.add(new Anomaly(
                AnomalyType.HEART_RATE_ABNORMAL,
                AnomalySeverity.MEDIUM,
                String.format("Abnormal heart rate: %d bpm", hr)
            ));
        }
        
        return anomalies;
    }
    
    private List<Anomaly> detectWeightAnomalies(VitalReading reading, PatientProfile profile) {
        List<Anomaly> anomalies = new ArrayList<>();
        
        if (reading.getWeightKg() == null || profile.getTargetWeightKg() == null) {
            return anomalies;
        }
        
        double currentWeight = reading.getWeightKg();
        double targetWeight = profile.getTargetWeightKg();
        double change = Math.abs(currentWeight - targetWeight);
        
        if (change > 5.0) { // More than 5kg deviation
            anomalies.add(new Anomaly(
                AnomalyType.WEIGHT_CHANGE,
                AnomalySeverity.MEDIUM,
                String.format("Significant weight deviation: %.1f kg (target: %.1f kg)", currentWeight, targetWeight)
            ));
        }
        
        return anomalies;
    }
    
    private List<Anomaly> detectEncounterAnomalies(Encounter encounter) {
        List<Anomaly> anomalies = new ArrayList<>();
        
        // Check for missed medications
        if (encounter.getMedicationMissed() != null && encounter.getMedicationMissed()) {
            anomalies.add(new Anomaly(
                AnomalyType.MISSING_MEDICATION,
                AnomalySeverity.HIGH,
                "Medication dose missed"
            ));
        }
        
        // Check for concerning symptoms
        if (encounter.getSymptoms() != null && !encounter.getSymptoms().trim().isEmpty()) {
            String symptoms = encounter.getSymptoms().toLowerCase();
            
            if (symptoms.contains("chest pain") || symptoms.contains("shortness of breath") || 
                symptoms.contains("severe headache")) {
                anomalies.add(new Anomaly(
                    AnomalyType.SYMPTOM_REPORTED,
                    AnomalySeverity.CRITICAL,
                    "Critical symptoms reported: " + encounter.getSymptoms()
                ));
            } else if (symptoms.contains("dizziness") || symptoms.contains("swelling") || 
                       symptoms.contains("blurred vision")) {
                anomalies.add(new Anomaly(
                    AnomalyType.SYMPTOM_REPORTED,
                    AnomalySeverity.HIGH,
                    "Concerning symptoms reported: " + encounter.getSymptoms()
                ));
            }
        }
        
        return anomalies;
    }
    
    private List<Anomaly> detectTrendAnomalies(DigitalTwinSnapshot snapshot, PatientProfile profile) {
        List<Anomaly> anomalies = new ArrayList<>();
        
        // This would implement trend analysis
        // For now, return empty list
        return anomalies;
    }
}