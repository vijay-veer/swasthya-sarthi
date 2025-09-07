package com.swasthyasarthi.service.ai;

import com.swasthyasarthi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AiAgentService {
    
    @Autowired
    private CrrsComputationService crrsComputationService;
    
    @Autowired
    private AnomalyDetectionService anomalyDetectionService;
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * Main AI Agent OODA Loop implementation
     * OBSERVE -> ORIENT -> DECIDE -> ACT
     */
    public void processPatientData(PatientProfile patientProfile, LocalDate date) {
        // OBSERVE: Monitor the Digital Twin for new data and changes
        DigitalTwinSnapshot snapshot = observeDigitalTwin(patientProfile, date);
        
        // ORIENT: Analyze the data against knowledge base and context
        AnalysisResult analysis = orientData(snapshot, patientProfile);
        
        // DECIDE: Determine appropriate actions based on analysis
        List<AgentAction> actions = decideActions(analysis, patientProfile);
        
        // ACT: Execute the decided actions
        executeActions(actions, patientProfile);
    }
    
    /**
     * OBSERVE: Collect and monitor all relevant data for the patient
     */
    private DigitalTwinSnapshot observeDigitalTwin(PatientProfile patientProfile, LocalDate date) {
        DigitalTwinSnapshot snapshot = new DigitalTwinSnapshot();
        snapshot.setPatientProfile(patientProfile);
        snapshot.setObservationDate(date);
        
        // Collect recent vital readings
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        
        // This would be implemented with actual repository calls
        // For now, we'll create a placeholder
        snapshot.setRecentVitals(List.of()); // Would fetch from VitalReadingRepository
        snapshot.setRecentEncounters(List.of()); // Would fetch from EncounterRepository
        snapshot.setRecentCrrsScores(List.of()); // Would fetch from CrrsScoreRepository
        
        return snapshot;
    }
    
    /**
     * ORIENT: Analyze data against clinical guidelines and patient context
     */
    private AnalysisResult orientData(DigitalTwinSnapshot snapshot, PatientProfile patientProfile) {
        AnalysisResult result = new AnalysisResult();
        result.setPatientProfile(patientProfile);
        result.setAnalysisDate(snapshot.getObservationDate());
        
        // Analyze vital trends
        VitalTrendAnalysis vitalAnalysis = analyzeVitalTrends(snapshot.getRecentVitals(), patientProfile);
        result.setVitalTrendAnalysis(vitalAnalysis);
        
        // Analyze lifestyle patterns
        LifestyleAnalysis lifestyleAnalysis = analyzeLifestylePatterns(snapshot.getRecentEncounters());
        result.setLifestyleAnalysis(lifestyleAnalysis);
        
        // Analyze adherence patterns
        AdherenceAnalysis adherenceAnalysis = analyzeAdherencePatterns(snapshot.getRecentEncounters());
        result.setAdherenceAnalysis(adherenceAnalysis);
        
        // Detect anomalies
        List<Anomaly> anomalies = anomalyDetectionService.detectAnomalies(snapshot, patientProfile);
        result.setAnomalies(anomalies);
        
        return result;
    }
    
    /**
     * DECIDE: Determine appropriate actions based on analysis
     */
    private List<AgentAction> decideActions(AnalysisResult analysis, PatientProfile patientProfile) {
        List<AgentAction> actions = new ArrayList<>();
        
        // Process anomalies
        for (Anomaly anomaly : analysis.getAnomalies()) {
            AgentAction action = decideActionForAnomaly(anomaly, patientProfile);
            if (action != null) {
                actions.add(action);
            }
        }
        
        // Process lifestyle patterns
        if (analysis.getLifestyleAnalysis().needsIntervention()) {
            AgentAction lifestyleAction = createLifestyleInterventionAction(analysis.getLifestyleAnalysis(), patientProfile);
            actions.add(lifestyleAction);
        }
        
        // Process adherence issues
        if (analysis.getAdherenceAnalysis().needsIntervention()) {
            AgentAction adherenceAction = createAdherenceInterventionAction(analysis.getAdherenceAnalysis(), patientProfile);
            actions.add(adherenceAction);
        }
        
        // Generate proactive nudges
        AgentAction nudgeAction = generateProactiveNudge(analysis, patientProfile);
        if (nudgeAction != null) {
            actions.add(nudgeAction);
        }
        
        return actions;
    }
    
    /**
     * ACT: Execute the decided actions
     */
    private void executeActions(List<AgentAction> actions, PatientProfile patientProfile) {
        for (AgentAction action : actions) {
            try {
                switch (action.getActionType()) {
                    case SEND_NOTIFICATION:
                        notificationService.sendNotification(patientProfile.getUser(), action);
                        break;
                    case SEND_ALERT:
                        notificationService.sendAlert(patientProfile.getUser(), action);
                        break;
                    case SEND_CRITICAL_ALERT:
                        notificationService.sendCriticalAlert(patientProfile.getUser(), action);
                        break;
                    case UPDATE_CRRS:
                        crrsComputationService.computeCrrsScore(patientProfile, action.getTargetDate());
                        break;
                    case CREATE_QUEST:
                        createPersonalizedQuest(patientProfile, action);
                        break;
                }
            } catch (Exception e) {
                // Log error but continue with other actions
                System.err.println("Error executing action: " + e.getMessage());
            }
        }
    }
    
    /**
     * Generate CRRS explanation using AI
     */
    public String generateCrrsExplanation(PatientProfile patientProfile, Double crrsValue, Double deltaCrrs,
                                        RiskTier riskTier, Double vitalsContribution, Double lifestyleContribution,
                                        Double adherenceContribution, Double symptomsContribution, Double trendContribution) {
        
        StringBuilder explanation = new StringBuilder();
        
        // Basic explanation
        explanation.append(String.format("Your Cardio-Renal Risk Score today is %.1f (%s). ", 
            crrsValue, riskTier.getDescription()));
        
        if (deltaCrrs > 0) {
            explanation.append(String.format("This is %.1f points higher than yesterday, indicating increased risk. ", deltaCrrs));
        } else if (deltaCrrs < 0) {
            explanation.append(String.format("This is %.1f points lower than yesterday, showing improvement! ", Math.abs(deltaCrrs)));
        } else {
            explanation.append("This is the same as yesterday. ");
        }
        
        // Explain main contributors
        if (Math.abs(vitalsContribution) > 2) {
            if (vitalsContribution > 0) {
                explanation.append("Your recent vital readings are concerning and contributing to higher risk. ");
            } else {
                explanation.append("Your recent vital readings are good and helping reduce risk. ");
            }
        }
        
        if (Math.abs(lifestyleContribution) > 1) {
            if (lifestyleContribution > 0) {
                explanation.append("Your lifestyle choices are increasing your risk. ");
            } else {
                explanation.append("Your healthy lifestyle choices are reducing your risk. ");
            }
        }
        
        if (Math.abs(adherenceContribution) > 1) {
            if (adherenceContribution > 0) {
                explanation.append("Missing medications is increasing your risk. ");
            } else {
                explanation.append("Good medication adherence is helping your health. ");
            }
        }
        
        // Add personalized recommendations
        explanation.append(generatePersonalizedRecommendations(patientProfile, crrsValue, riskTier));
        
        return explanation.toString();
    }
    
    private String generatePersonalizedRecommendations(PatientProfile patientProfile, Double crrsValue, RiskTier riskTier) {
        StringBuilder recommendations = new StringBuilder();
        
        switch (riskTier) {
            case LOW:
                recommendations.append("Keep up the great work! Continue your healthy habits.");
                break;
            case MODERATE:
                recommendations.append("Focus on improving your lifestyle habits. Consider increasing physical activity and monitoring your diet.");
                break;
            case HIGH:
                recommendations.append("Your risk is elevated. Please consult with your healthcare provider and focus on medication adherence and lifestyle changes.");
                break;
            case CRITICAL:
                recommendations.append("Your risk is critical. Please seek immediate medical attention and ensure you're following all medical advice.");
                break;
        }
        
        return recommendations.toString();
    }
    
    // Helper methods for analysis
    private VitalTrendAnalysis analyzeVitalTrends(List<VitalReading> vitals, PatientProfile profile) {
        // Implementation would analyze trends in BP, glucose, weight, etc.
        return new VitalTrendAnalysis();
    }
    
    private LifestyleAnalysis analyzeLifestylePatterns(List<Encounter> encounters) {
        // Implementation would analyze activity, diet, sleep patterns
        return new LifestyleAnalysis();
    }
    
    private AdherenceAnalysis analyzeAdherencePatterns(List<Encounter> encounters) {
        // Implementation would analyze medication adherence patterns
        return new AdherenceAnalysis();
    }
    
    private AgentAction decideActionForAnomaly(Anomaly anomaly, PatientProfile profile) {
        switch (anomaly.getSeverity()) {
            case LOW:
                return new AgentAction(ActionType.SEND_NOTIFICATION, "Low severity anomaly detected", profile.getUser());
            case MEDIUM:
                return new AgentAction(ActionType.SEND_ALERT, "Medium severity anomaly detected", profile.getUser());
            case HIGH:
                return new AgentAction(ActionType.SEND_CRITICAL_ALERT, "High severity anomaly detected", profile.getUser());
            default:
                return null;
        }
    }
    
    private AgentAction createLifestyleInterventionAction(LifestyleAnalysis analysis, PatientProfile profile) {
        return new AgentAction(ActionType.SEND_NOTIFICATION, "Lifestyle intervention needed", profile.getUser());
    }
    
    private AgentAction createAdherenceInterventionAction(AdherenceAnalysis analysis, PatientProfile profile) {
        return new AgentAction(ActionType.SEND_NOTIFICATION, "Medication adherence reminder", profile.getUser());
    }
    
    private AgentAction generateProactiveNudge(AnalysisResult analysis, PatientProfile profile) {
        return new AgentAction(ActionType.SEND_NOTIFICATION, "Proactive health nudge", profile.getUser());
    }
    
    private void createPersonalizedQuest(PatientProfile profile, AgentAction action) {
        // Implementation for creating personalized quests
    }
}