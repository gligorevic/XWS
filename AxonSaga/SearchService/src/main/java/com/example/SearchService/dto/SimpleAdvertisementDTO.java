package com.example.SearchService.dto;

import com.example.SearchService.domain.Advertisement;

public class SimpleAdvertisementDTO {
    private Long id;
    private Integer price;
    private String brandName;
    private String modelName;
    private String cityName;
    private String mainImagePath;
    private Integer kmPassed;
    private Double avgRate = 0.0;
    private String agentUsername;

    public SimpleAdvertisementDTO() {
    }

    public SimpleAdvertisementDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.price = advertisement.getPriceFrom();
        this.brandName = advertisement.getBrandName();
        this.cityName = advertisement.getRentingCityLocation().getName();
        this.modelName = advertisement.getModelName();
        this.mainImagePath = advertisement.getMainImagePath();
        this.kmPassed = advertisement.getKmPassed();
        this.agentUsername = advertisement.getUserEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKmPassed() {
        return kmPassed;
    }

    public void setKmPassed(Integer kmPassed) {
        this.kmPassed = kmPassed;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMainImagePath() {
        return mainImagePath;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }
}
