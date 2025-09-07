package com.swasthyasarthi.model;

public class LifestyleAnalysis {
    private boolean lowActivity;
    private boolean poorDiet;
    private boolean inadequateSleep;
    private boolean highStress;
    private String lifestyleSummary;
    
    // Constructors
    public LifestyleAnalysis() {}
    
    // Getters and Setters
    public boolean isLowActivity() { return lowActivity; }
    public void setLowActivity(boolean lowActivity) { this.lowActivity = lowActivity; }
    
    public boolean isPoorDiet() { return poorDiet; }
    public void setPoorDiet(boolean poorDiet) { this.poorDiet = poorDiet; }
    
    public boolean isInadequateSleep() { return inadequateSleep; }
    public void setInadequateSleep(boolean inadequateSleep) { this.inadequateSleep = inadequateSleep; }
    
    public boolean isHighStress() { return highStress; }
    public void setHighStress(boolean highStress) { this.highStress = highStress; }
    
    public String getLifestyleSummary() { return lifestyleSummary; }
    public void setLifestyleSummary(String lifestyleSummary) { this.lifestyleSummary = lifestyleSummary; }
    
    // Utility methods
    public boolean needsIntervention() {
        return lowActivity || poorDiet || inadequateSleep || highStress;
    }
}