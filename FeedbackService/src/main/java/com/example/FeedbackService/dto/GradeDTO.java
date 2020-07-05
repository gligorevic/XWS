package com.example.FeedbackService.dto;

public class GradeDTO {

    private int grade;
    private Long requestId;
    private String username;
    private String agentUsername;

    public GradeDTO() {
    }

    public GradeDTO(int grade, Long requestId, String username, String agentUsername) {
        this.grade = grade;
        this.requestId = requestId;
        this.username = username;
        this.agentUsername = agentUsername;
    }

    public int getGrade() {
        return grade;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
