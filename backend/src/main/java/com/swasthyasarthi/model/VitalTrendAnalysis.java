package com.swasthyasarthi.model;

public class VitalTrendAnalysis {
    private boolean bpTrendRising;
    private boolean glucoseTrendRising;
    private boolean weightTrendConcerning;
    private String trendSummary;
    
    // Constructors
    public VitalTrendAnalysis() {}
    
    // Getters and Setters
    public boolean isBpTrendRising() { return bpTrendRising; }
    public void setBpTrendRising(boolean bpTrendRising) { this.bpTrendRising = bpTrendRising; }
    
    public boolean isGlucoseTrendRising() { return glucoseTrendRising; }
    public void setGlucoseTrendRising(boolean glucoseTrendRising) { this.glucoseTrendRising = glucoseTrendRising; }
    
    public boolean isWeightTrendConcerning() { return weightTrendConcerning; }
    public void setWeightTrendConcerning(boolean weightTrendConcerning) { this.weightTrendConcerning = weightTrendConcerning; }
    
    public String getTrendSummary() { return trendSummary; }
    public void setTrendSummary(String trendSummary) { this.trendSummary = trendSummary; }
}