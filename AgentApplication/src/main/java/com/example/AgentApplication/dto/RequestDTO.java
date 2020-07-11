package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.enumeration.PaidState;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RequestDTO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date endDate;

    private String userEmail;
    private String userSentRequest;
    private Integer price;
    private String brandName;
    private String modelName;
    private Date creationDate;
    private boolean inBundle;
    private Long containerId;
    private PaidState paidState;
    private Long adId;

    public RequestDTO() {
    }

    public RequestDTO(Request request) {
        this.id = request.getId();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.userEmail = request.getAdvertisement().getCar().getUserEmail();
        this.userSentRequest = request.getUserSentRequest().getEmail();
        this.creationDate = request.getCreationDate();
        if(request.getRequestContainer() != null) {
            this.containerId = request.getRequestContainer().getId();
        } else {
            this.containerId = -1L;
        }
        this.inBundle = request.isInBundle();
        this.paidState = request.getPaidState();
        this.adId = request.getAdvertisement().getId();
        this.modelName =  request.getAdvertisement().getCar().getModel().getModelName();
        this.brandName = request.getAdvertisement().getCar().getBrand().getBrandName();
        this.adId = request.getAdvertisement().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFreeFrom() {
        return startDate;
    }

    public void setFreeFrom(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFreeTo() {
        return endDate;
    }

    public void setFreeTo(Date endDate) {
        this.endDate = endDate;
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

    public boolean isInBundle() {
        return inBundle;
    }

    public void setInBundle(boolean inBundle) {
        this.inBundle = inBundle;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
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
}
