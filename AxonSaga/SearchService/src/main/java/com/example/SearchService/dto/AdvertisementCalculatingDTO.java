package com.example.SearchService.dto;

import com.example.SearchService.domain.Advertisement;

public class AdvertisementCalculatingDTO {

    private Long id;
    private String brandName;
    private String modelName;
    private String cityName;
    private Integer kmPassed;
    private Integer kmRestriction;
    private Double avgRate = 0.0;
    private String agentUsername;

    public AdvertisementCalculatingDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.brandName = advertisement.getBrandName();
        this.cityName = advertisement.getRentingCityLocation().getName();
        this.modelName = advertisement.getModelName();
        this.kmRestriction = advertisement.getKmRestriction();
        this.kmPassed = advertisement.getKmPassed();
        this.agentUsername = advertisement.getUserEmail();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getKmPassed() {
        return kmPassed;
    }

    public void setKmPassed(Integer kmPassed) {
        this.kmPassed = kmPassed;
    }

    public Integer getKmRestriction() {
        return kmRestriction;
    }

    public void setKmRestriction(Integer kmRestriction) {
        this.kmRestriction = kmRestriction;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }
}
