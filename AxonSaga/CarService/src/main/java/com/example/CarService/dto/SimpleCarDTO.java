package com.example.CarService.dto;

import com.example.CarService.domain.Car;

import java.util.Date;

public class SimpleCarDTO {
    private Long id;
    private String brandName;
    private String modelName;
    private Boolean tokenGenerated;
    private Date creationDate;
    private String mainImageUrl;

    public SimpleCarDTO() {
    }

    public SimpleCarDTO(Car car) {
        this.id = car.getId();
        this.brandName = car.getBrandName();
        this.modelName = car.getModelName();
        this.tokenGenerated = car.getLocationToken() != null && car.getLocationToken().length() > 0;
        this.creationDate = car.getCrationDate();
        this.mainImageUrl = car.getMainImageUrl();
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

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }
}
