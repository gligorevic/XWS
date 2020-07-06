package com.example.RequestService.dto;

import com.example.RequestService.domain.Request;

import java.util.Date;

public class RequestBundleDTO {

    private String brandName;
    private String modelName;
    private Date startDate;
    private Date endDate;

    public RequestBundleDTO() {
    }

    public RequestBundleDTO(Request request) {
        this.brandName = request.getBrandName();
        this.modelName = request.getModelName();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
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
}
