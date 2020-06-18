package com.example.FeedbackService.dto;

public class GradeDTO {

    private Long id;
    private int grade;
    private Long requestId;
    private String username; // dao ocenu

    public GradeDTO() {
    }

    public GradeDTO(Long id, int grade, Long requestId, String username) {
        this.id = id;
        this.grade = grade;
        this.requestId = requestId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public Long getRequestId() {
        return requestId;
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
