package com.example.FeedbackService.dto;

import java.util.Date;

public class RequestDTO {

    private Long id;
    private Date freeFrom;
    private Date freeTo;
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

    public RequestDTO(Long id, Date freeFrom, Date freeTo, boolean inBundle) {
        this.id = id;
        this.freeFrom = freeFrom;
        this.freeTo = freeTo;
        this.inBundle = inBundle;
    }

    public RequestDTO(Long id, Date freeFrom, Date freeTo, String userEmail, String userSentRequest) {
        this.id = id;
        this.freeFrom = freeFrom;
        this.freeTo = freeTo;
        this.userEmail = userEmail;
        this.userSentRequest = userSentRequest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFreeFrom() {
        return freeFrom;
    }

    public void setFreeFrom(Date freeFrom) {
        this.freeFrom = freeFrom;
    }

    public Date getFreeTo() {
        return freeTo;
    }

    public void setFreeTo(Date freeTo) {
        this.freeTo = freeTo;
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
