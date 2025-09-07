package com.swasthyasarthi.model;

public enum RiskTier {
    LOW(0, 25, "Low Risk"),
    MODERATE(25, 50, "Moderate Risk"),
    HIGH(50, 75, "High Risk"),
    CRITICAL(75, 100, "Critical Risk");
    
    private final double minScore;
    private final double maxScore;
    private final String description;
    
    RiskTier(double minScore, double maxScore, String description) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.description = description;
    }
    
    public double getMinScore() { return minScore; }
    public double getMaxScore() { return maxScore; }
    public String getDescription() { return description; }
    
    public static RiskTier fromScore(double score) {
        for (RiskTier tier : values()) {
            if (score >= tier.minScore && score < tier.maxScore) {
                return tier;
            }
        }
        return CRITICAL; // Default to critical if score is 100 or above
    }
    
    public boolean isInRange(double score) {
        return score >= minScore && score < maxScore;
    }
}