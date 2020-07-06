package com.example.RequestService.domain;

import com.example.RequestService.dto.RequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PaidState paidState;

    private Long adId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date crationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private RequestContainer requestContainer;

    private boolean inBundle;

    private String userEmail;
    private String userSentRequest;

    private String brandName;
    private String modelName;

    public Request() {
    }

    public Request(RequestDTO requestDTO) {
        this.adId = requestDTO.getId();
        this.startDate = requestDTO.getFreeFrom();
        this.endDate = requestDTO.getFreeTo();
        this.paidState = PaidState.PENDING;
        this.userEmail = requestDTO.getUserEmail();
        this.userSentRequest = requestDTO.getUserSentRequest();
        this.inBundle = requestDTO.isInBundle();
        this.brandName = requestDTO.getBrandName();
        this.modelName = requestDTO.getModelName();
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

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSentRequest() {
        return userSentRequest;
    }

    public void setUserSentRequest(String userSentRequest) {
        this.userSentRequest = userSentRequest;
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

    public boolean isInBundle() {
        return inBundle;
    }

    public void setInBundle(boolean inBundle) {
        this.inBundle = inBundle;
    }

    public Date getCrationDate() {
        return crationDate;
    }

    public void setCrationDate(Date crationDate) {
        this.crationDate = crationDate;
    }

    public RequestContainer getRequestContainer() {
        return requestContainer;
    }

    public void setRequestContainer(RequestContainer requestContainer) {
        this.requestContainer = requestContainer;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @PrePersist
    protected void onCreate(){
        this.crationDate = new Date();
    }
}
