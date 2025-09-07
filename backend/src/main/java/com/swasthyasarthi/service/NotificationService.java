package com.swasthyasarthi.service;

import com.swasthyasarthi.model.*;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    /**
     * Send a regular notification to the user
     */
    public void sendNotification(User user, AgentAction action) {
        // Implementation would send in-app notification
        System.out.println("Sending notification to " + user.getPhoneNumber() + ": " + action.getMessage());
    }
    
    /**
     * Send an alert to the user
     */
    public void sendAlert(User user, AgentAction action) {
        // Implementation would send in-app alert and possibly SMS
        System.out.println("Sending alert to " + user.getPhoneNumber() + ": " + action.getMessage());
    }
    
    /**
     * Send a critical alert to the user and their caregiver
     */
    public void sendCriticalAlert(User user, AgentAction action) {
        // Implementation would send critical alert via multiple channels
        System.out.println("Sending critical alert to " + user.getPhoneNumber() + ": " + action.getMessage());
        
        // Also notify caregiver if available
        if (user.getPatientProfile() != null && 
            user.getPatientProfile().getEmergencyContactPhone() != null) {
            System.out.println("Notifying emergency contact: " + 
                user.getPatientProfile().getEmergencyContactPhone());
        }
    }
}