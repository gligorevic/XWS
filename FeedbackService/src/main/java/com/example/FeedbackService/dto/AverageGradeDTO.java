package com.example.FeedbackService.dto;

public class AverageGradeDTO {
    private Double averageGrade;
    private String agentUsername;

    public AverageGradeDTO() {
    }

    public AverageGradeDTO(Double averageGrade, String agentUsername) {
        this.averageGrade = averageGrade;
        this.agentUsername = agentUsername;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }
}
