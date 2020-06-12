package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.Car;

import java.util.Date;

public class SimpleCarDTO {
    private Long id;
    private String brandName;
    private String modelName;
    private Boolean tokenGenerated;
    private Date creationDate;

    public SimpleCarDTO() {
    }

    public SimpleCarDTO(Car car) {
        this.id = car.getId();
        this.brandName = car.getBrand().getBrandName();
        this.modelName = car.getModel().getModelName();
        this.tokenGenerated = car.getLocationToken() != null && car.getLocationToken().length() > 0;
        this.creationDate = car.getCrationDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public Boolean getTokenGenerated() {
        return tokenGenerated;
    }

    public void setTokenGenerated(Boolean tokenGenerated) {
        this.tokenGenerated = tokenGenerated;
    }
}
