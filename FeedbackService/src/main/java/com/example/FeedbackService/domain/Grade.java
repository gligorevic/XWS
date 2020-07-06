package com.example.FeedbackService.domain;

import com.example.FeedbackService.dto.GradeDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int grade;
    private Long requestId;
    private boolean inBundle;
    private String username;
    private String agentUsername;

    public Grade() {
    }

    public Grade(int grade, Long requestId, String username, String agentUsername) {
        this.grade = grade;
        this.requestId = requestId;
        this.username = username;
        this.agentUsername = agentUsername;
    }

    public Grade(GradeDTO gradeDTO) {
        this.grade = gradeDTO.getGrade();
        this.requestId = gradeDTO.getRequestId();
        this.username = gradeDTO.getUsername();
        this.agentUsername = gradeDTO.getAgentUsername();
        this.inBundle = gradeDTO.isInBundle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isInBundle() {
        return inBundle;
    }

    public void setInBundle(boolean inBundle) {
        this.inBundle = inBundle;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }
}
