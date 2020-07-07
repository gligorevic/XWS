package com.example.SearchService.dto;

import com.example.SearchService.domain.Advertisement;

import java.util.Date;
import java.util.List;

public class AdvertisementStatisticDTO {
    private Long id;

    private Long carId;

    private String brandName;

    private String modelName;

    private Integer kmPassed;

    private Date freeFrom;

    public AdvertisementStatisticDTO(Advertisement advertisement){
        this.id = advertisement.getId();
        this.carId = advertisement.getCarId();
        this.brandName = advertisement.getBrandName();
        this.modelName = advertisement.getModelName();
        this.kmPassed = advertisement.getKmPassed();
        this.freeFrom = advertisement.getFreeFrom();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public Integer getKmPassed() {
        return kmPassed;
    }

    public void setKmPassed(Integer kmPassed) {
        this.kmPassed = kmPassed;
    }

    public Date getFreeFrom() {
        return freeFrom;
    }

    public void setFreeFrom(Date freeFrom) {
        this.freeFrom = freeFrom;
    }
}
