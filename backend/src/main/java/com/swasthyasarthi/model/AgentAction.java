package com.swasthyasarthi.model;

import java.time.LocalDate;

public class AgentAction {
    private ActionType actionType;
    private String message;
    private User targetUser;
    private LocalDate targetDate;
    private String priority;
    
    // Constructors
    public AgentAction() {}
    
    public AgentAction(ActionType actionType, String message, User targetUser) {
        this.actionType = actionType;
        this.message = message;
        this.targetUser = targetUser;
        this.targetDate = LocalDate.now();
    }
    
    // Getters and Setters
    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public User getTargetUser() { return targetUser; }
    public void setTargetUser(User targetUser) { this.targetUser = targetUser; }
    
    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
    
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
}