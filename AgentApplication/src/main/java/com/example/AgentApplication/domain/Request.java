package com.example.AgentApplication.domain;

import com.example.AgentApplication.enumeration.PaidState;
import com.example.AgentApplication.dto.RequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PaidState paidState;

    @ManyToOne
    private Advertisement advertisement;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date creationDate;

    @ManyToOne
    private User userSentRequest;

    public Request() {
    }

    public Request(RequestDTO requestDTO) {
        this.startDate = requestDTO.getFreeFrom();
        this.endDate = requestDTO.getFreeTo();
        this.paidState = PaidState.PENDING;
    }

    public Request(PaidState paidState) {
        this.paidState = paidState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaidState getPaidState() {
        return paidState;
    }

    public void setPaidState(PaidState paidState) {
        this.paidState = paidState;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @PrePersist
    protected void onCreate(){
        this.creationDate= new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public User getUserSentRequest() {
        return userSentRequest;
    }

    public void setUserSentRequest(User userSentRequest) {
        this.userSentRequest = userSentRequest;
    }
}
