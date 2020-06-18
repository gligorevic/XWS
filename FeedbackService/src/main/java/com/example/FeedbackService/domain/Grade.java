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
    private String username; // dao ocenu


    public Grade(int grade, Long requestId, String username ) {
        this.grade = grade;
        this.requestId = requestId;
        this.username = username;
    }

    public Grade(GradeDTO gradeDTO) {
        this.grade = gradeDTO.getGrade();
        this.requestId = gradeDTO.getRequestId();
        this.username = gradeDTO.getUsername();
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
}
