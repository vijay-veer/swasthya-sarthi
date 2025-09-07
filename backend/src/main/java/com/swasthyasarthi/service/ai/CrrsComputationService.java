package com.swasthyasarthi.service.ai;

import com.swasthyasarthi.model.*;
import com.swasthyasarthi.repository.CrrsScoreRepository;
import com.swasthyasarthi.repository.VitalReadingRepository;
import com.swasthyasarthi.repository.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CrrsComputationService {
    
    @Autowired
    private CrrsScoreRepository crrsScoreRepository;
    
    @Autowired
    private VitalReadingRepository vitalReadingRepository;
    
    @Autowired
    private EncounterRepository encounterRepository;
    
    @Autowired
    private AiAgentService aiAgentService;
    
    /**
     * Computes the Cardio-Renal Risk Score (CRRS) for a patient profile
     * Formula: CRRS_today = CRRS_yesterday + ΔV + ΔL + ΔS + ΔT
     * Where:
     * - ΔV = Delta Vitals (impact of today's vital readings)
     * - ΔL = Delta Lifestyle (impact of lifestyle choices)
     * - ΔS = Delta Symptoms (impact of reported symptoms)
     * - ΔT = Delta Trend (impact of recent trends)
     */
    public CrrsScore computeCrrsScore(PatientProfile patientProfile, LocalDate scoreDate) {
        // Get previous CRRS score
        Double previousCrrs = getPreviousCrrsScore(patientProfile, scoreDate);
        
        // Calculate individual contributions
        Double vitalsContribution = calculateVitalsContribution(patientProfile, scoreDate);
        Double lifestyleContribution = calculateLifestyleContribution(patientProfile, scoreDate);
        Double adherenceContribution = calculateAdherenceContribution(patientProfile, scoreDate);
        Double symptomsContribution = calculateSymptomsContribution(patientProfile, scoreDate);
        Double trendContribution = calculateTrendContribution(patientProfile, scoreDate);
        
        // Calculate new CRRS
        Double newCrrs = previousCrrs + vitalsContribution + lifestyleContribution + 
                        adherenceContribution + symptomsContribution + trendContribution;
        
        // Ensure CRRS stays within bounds (0-100)
        newCrrs = Math.max(0.0, Math.min(100.0, newCrrs));
        
        // Determine risk tier
        RiskTier riskTier = RiskTier.fromScore(newCrrs);
        
        // Calculate delta
        Double deltaCrrs = newCrrs - previousCrrs;
        
        // Generate explanation using AI agent
        String explanation = aiAgentService.generateCrrsExplanation(
            patientProfile, newCrrs, deltaCrrs, riskTier,
            vitalsContribution, lifestyleContribution, adherenceContribution,
            symptomsContribution, trendContribution
        );
        
        // Create and save CRRS score
        CrrsScore crrsScore = new CrrsScore(patientProfile, scoreDate, newCrrs, riskTier);
        crrsScore.setPreviousCrrs(previousCrrs);
        crrsScore.setDeltaCrrs(deltaCrrs);
        crrsScore.setVitalsContribution(vitalsContribution);
        crrsScore.setLifestyleContribution(lifestyleContribution);
        crrsScore.setAdherenceContribution(adherenceContribution);
        crrsScore.setSymptomsContribution(symptomsContribution);
        crrsScore.setTrendContribution(trendContribution);
        crrsScore.setExplanation(explanation);
        
        // Generate detailed calculation breakdown
        String calculationDetails = generateCalculationDetails(
            vitalsContribution, lifestyleContribution, adherenceContribution,
            symptomsContribution, trendContribution
        );
        crrsScore.setCalculationDetails(calculationDetails);
        
        return crrsScoreRepository.save(crrsScore);
    }
    
    private Double getPreviousCrrsScore(PatientProfile patientProfile, LocalDate scoreDate) {
        Optional<CrrsScore> previousScore = crrsScoreRepository
            .findTopByPatientProfileAndScoreDateBeforeOrderByScoreDateDesc(
                patientProfile, scoreDate);
        return previousScore.map(CrrsScore::getCrrsValue).orElse(50.0); // Default starting score
    }
    
    private Double calculateVitalsContribution(PatientProfile patientProfile, LocalDate scoreDate) {
        LocalDateTime startOfDay = scoreDate.atStartOfDay();
        LocalDateTime endOfDay = scoreDate.atTime(23, 59, 59);
        
        List<VitalReading> todaysReadings = vitalReadingRepository
            .findByPatientProfileAndReadingTimestampBetween(patientProfile, startOfDay, endOfDay);
        
        if (todaysReadings.isEmpty()) {
            return 2.0; // Penalty for missing vitals
        }
        
        double contribution = 0.0;
        
        for (VitalReading reading : todaysReadings) {
            switch (reading.getVitalType()) {
                case BLOOD_PRESSURE:
                    contribution += calculateBpContribution(reading, patientProfile);
                    break;
                case GLUCOSE:
                    contribution += calculateGlucoseContribution(reading, patientProfile);
                    break;
                case WEIGHT:
                    contribution += calculateWeightContribution(reading, patientProfile);
                    break;
                case HEART_RATE:
                    contribution += calculateHeartRateContribution(reading);
                    break;
            }
        }
        
        return contribution;
    }
    
    private Double calculateBpContribution(VitalReading reading, PatientProfile profile) {
        if (reading.getSystolicBp() == null || reading.getDiastolicBp() == null) {
            return 0.0;
        }
        
        Integer sbp = reading.getSystolicBp();
        Integer dbp = reading.getDiastolicBp();
        
        // Check if within target range
        if (profile.isInTargetRange(sbp, dbp)) {
            return -1.0; // Bonus for good BP
        }
        
        // Calculate deviation from target
        double sbpDeviation = Math.max(0, sbp - profile.getTargetSystolicBpMax());
        double dbpDeviation = Math.max(0, dbp - profile.getTargetDiastolicBpMax());
        
        // Critical thresholds
        if (sbp >= 180 || dbp >= 110) {
            return 15.0; // High penalty for critical BP
        }
        
        // Moderate to high BP
        if (sbp >= 160 || dbp >= 100) {
            return 8.0;
        }
        
        // Mild elevation
        return Math.min(5.0, (sbpDeviation + dbpDeviation) / 10.0);
    }
    
    private Double calculateGlucoseContribution(VitalReading reading, PatientProfile profile) {
        if (reading.getGlucoseValue() == null) {
            return 0.0;
        }
        
        Integer glucose = reading.getGlucoseValue();
        boolean isFasting = reading.getIsFasting() != null && reading.getIsFasting();
        
        // Check if within target range
        if (profile.isGlucoseInTargetRange(glucose, isFasting)) {
            return -1.0; // Bonus for good glucose
        }
        
        // Critical thresholds
        if (glucose < 70) {
            return 10.0; // High penalty for hypoglycemia
        }
        if (glucose > 300) {
            return 12.0; // High penalty for severe hyperglycemia
        }
        
        // Calculate deviation from target
        double deviation;
        if (isFasting) {
            deviation = Math.max(0, glucose - profile.getTargetFastingGlucoseMax());
        } else {
            deviation = Math.max(0, glucose - profile.getTargetPostMealGlucoseMax());
        }
        
        return Math.min(6.0, deviation / 20.0);
    }
    
    private Double calculateWeightContribution(VitalReading reading, PatientProfile profile) {
        if (reading.getWeightKg() == null || profile.getTargetWeightKg() == null) {
            return 0.0;
        }
        
        double currentWeight = reading.getWeightKg();
        double targetWeight = profile.getTargetWeightKg();
        double deviation = Math.abs(currentWeight - targetWeight);
        
        // If within 2kg of target, give bonus
        if (deviation <= 2.0) {
            return -0.5;
        }
        
        // Penalty for significant deviation
        return Math.min(3.0, deviation / 5.0);
    }
    
    private Double calculateHeartRateContribution(VitalReading reading) {
        if (reading.getHeartRate() == null) {
            return 0.0;
        }
        
        Integer hr = reading.getHeartRate();
        
        // Normal range: 60-100 bpm
        if (hr >= 60 && hr <= 100) {
            return -0.5; // Bonus for normal HR
        }
        
        // Abnormal ranges
        if (hr < 50 || hr > 120) {
            return 3.0; // Penalty for abnormal HR
        }
        
        return 1.0; // Minor penalty for slightly abnormal HR
    }
    
    private Double calculateLifestyleContribution(PatientProfile patientProfile, LocalDate scoreDate) {
        LocalDateTime startOfDay = scoreDate.atStartOfDay();
        LocalDateTime endOfDay = scoreDate.atTime(23, 59, 59);
        
        List<Encounter> todaysEncounters = encounterRepository
            .findByUserAndEncounterTimestampBetween(patientProfile.getUser(), startOfDay, endOfDay);
        
        double contribution = 0.0;
        
        for (Encounter encounter : todaysEncounters) {
            // Activity contribution
            if (encounter.getActivityMinutes() != null) {
                if (encounter.getActivityMinutes() >= 30) {
                    contribution -= 2.0; // Bonus for good activity
                } else if (encounter.getActivityMinutes() < 10) {
                    contribution += 1.0; // Penalty for low activity
                }
            }
            
            // Diet contribution
            if (encounter.getDietTags() != null && !encounter.getDietTags().isEmpty()) {
                if (encounter.getDietTags().contains("fried") || encounter.getDietTags().contains("sweets")) {
                    contribution += 1.5; // Penalty for unhealthy food
                }
            }
            
            // Sleep contribution
            if (encounter.getSleepHours() != null) {
                if (encounter.getSleepHours() < 6 || encounter.getSleepHours() > 9) {
                    contribution += 1.0; // Penalty for poor sleep
                } else if (encounter.getSleepHours() >= 7 && encounter.getSleepHours() <= 8) {
                    contribution -= 0.5; // Bonus for good sleep
                }
            }
            
            // Stress contribution
            if (encounter.getStressLevel() != null) {
                if (encounter.getStressLevel() >= 4) {
                    contribution += 1.0; // Penalty for high stress
                } else if (encounter.getStressLevel() <= 2) {
                    contribution -= 0.5; // Bonus for low stress
                }
            }
        }
        
        return contribution;
    }
    
    private Double calculateAdherenceContribution(PatientProfile patientProfile, LocalDate scoreDate) {
        LocalDateTime startOfDay = scoreDate.atStartOfDay();
        LocalDateTime endOfDay = scoreDate.atTime(23, 59, 59);
        
        List<Encounter> todaysEncounters = encounterRepository
            .findByUserAndEncounterTimestampBetween(patientProfile.getUser(), startOfDay, endOfDay);
        
        double contribution = 0.0;
        boolean hasMedicationLog = false;
        
        for (Encounter encounter : todaysEncounters) {
            if (encounter.getMedicationTaken() != null || encounter.getMedicationMissed() != null) {
                hasMedicationLog = true;
                
                if (encounter.getMedicationMissed() != null && encounter.getMedicationMissed()) {
                    contribution += 3.0; // High penalty for missed medication
                } else if (encounter.getMedicationTaken() != null && encounter.getMedicationTaken()) {
                    contribution -= 1.0; // Bonus for taking medication
                }
            }
        }
        
        // Penalty for not logging medication at all
        if (!hasMedicationLog) {
            contribution += 1.0;
        }
        
        return contribution;
    }
    
    private Double calculateSymptomsContribution(PatientProfile patientProfile, LocalDate scoreDate) {
        LocalDateTime startOfDay = scoreDate.atStartOfDay();
        LocalDateTime endOfDay = scoreDate.atTime(23, 59, 59);
        
        List<Encounter> todaysEncounters = encounterRepository
            .findByUserAndEncounterTimestampBetween(patientProfile.getUser(), startOfDay, endOfDay);
        
        double contribution = 0.0;
        
        for (Encounter encounter : todaysEncounters) {
            if (encounter.getSymptoms() != null && !encounter.getSymptoms().trim().isEmpty()) {
                String symptoms = encounter.getSymptoms().toLowerCase();
                
                // High-risk symptoms
                if (symptoms.contains("chest pain") || symptoms.contains("shortness of breath") || 
                    symptoms.contains("severe headache")) {
                    contribution += 8.0;
                }
                // Moderate-risk symptoms
                else if (symptoms.contains("dizziness") || symptoms.contains("swelling") || 
                         symptoms.contains("blurred vision")) {
                    contribution += 4.0;
                }
                // Low-risk symptoms
                else if (symptoms.contains("fatigue") || symptoms.contains("thirst") || 
                         symptoms.contains("tingling")) {
                    contribution += 2.0;
                }
            }
        }
        
        return contribution;
    }
    
    private Double calculateTrendContribution(PatientProfile patientProfile, LocalDate scoreDate) {
        // Get readings from the past 7 days
        LocalDateTime sevenDaysAgo = scoreDate.minusDays(7).atStartOfDay();
        LocalDateTime endOfDay = scoreDate.atTime(23, 59, 59);
        
        List<VitalReading> recentReadings = vitalReadingRepository
            .findByPatientProfileAndReadingTimestampBetween(patientProfile, sevenDaysAgo, endOfDay);
        
        if (recentReadings.size() < 3) {
            return 1.0; // Penalty for insufficient data
        }
        
        double contribution = 0.0;
        
        // Analyze BP trends
        List<VitalReading> bpReadings = recentReadings.stream()
            .filter(r -> r.getVitalType() == VitalType.BLOOD_PRESSURE && r.getSystolicBp() != null)
            .sorted((a, b) -> a.getReadingTimestamp().compareTo(b.getReadingTimestamp()))
            .toList();
        
        if (bpReadings.size() >= 3) {
            double trend = calculateTrend(bpReadings.stream().mapToInt(VitalReading::getSystolicBp).toArray());
            if (trend > 5) { // Rising trend
                contribution += 3.0;
            } else if (trend < -5) { // Improving trend
                contribution -= 1.0;
            }
        }
        
        // Analyze glucose trends
        List<VitalReading> glucoseReadings = recentReadings.stream()
            .filter(r -> r.getVitalType() == VitalType.GLUCOSE && r.getGlucoseValue() != null)
            .sorted((a, b) -> a.getReadingTimestamp().compareTo(b.getReadingTimestamp()))
            .toList();
        
        if (glucoseReadings.size() >= 3) {
            double trend = calculateTrend(glucoseReadings.stream().mapToInt(VitalReading::getGlucoseValue).toArray());
            if (trend > 10) { // Rising trend
                contribution += 2.0;
            } else if (trend < -10) { // Improving trend
                contribution -= 1.0;
            }
        }
        
        return contribution;
    }
    
    private double calculateTrend(int[] values) {
        if (values.length < 2) return 0.0;
        
        double sum = 0.0;
        for (int i = 1; i < values.length; i++) {
            sum += values[i] - values[i-1];
        }
        return sum / (values.length - 1);
    }
    
    private String generateCalculationDetails(Double vitalsContribution, Double lifestyleContribution,
                                            Double adherenceContribution, Double symptomsContribution,
                                            Double trendContribution) {
        return String.format(
            "CRRS Calculation Breakdown:\n" +
            "Vitals Contribution: %.2f\n" +
            "Lifestyle Contribution: %.2f\n" +
            "Adherence Contribution: %.2f\n" +
            "Symptoms Contribution: %.2f\n" +
            "Trend Contribution: %.2f\n" +
            "Total Change: %.2f",
            vitalsContribution, lifestyleContribution, adherenceContribution,
            symptomsContribution, trendContribution,
            vitalsContribution + lifestyleContribution + adherenceContribution + 
            symptomsContribution + trendContribution
        );
    }
}