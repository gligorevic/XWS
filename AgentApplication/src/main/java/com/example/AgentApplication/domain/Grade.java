package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.GradeDTO;

import javax.persistence.*;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer grade;

    @OneToOne
    private Request request;

    @ManyToOne
    private User user; // dao ocenu

    private boolean inBundle;

    private Long remoteId;

    public Grade() {
    }

    public Grade(GradeDTO gradeDTO) {
        this.grade = gradeDTO.getGrade();
        this.inBundle = gradeDTO.isInBundle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isInBundle() {
        return inBundle;
    }

    public void setInBundle(boolean inBundle) {
        this.inBundle = inBundle;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }
}
