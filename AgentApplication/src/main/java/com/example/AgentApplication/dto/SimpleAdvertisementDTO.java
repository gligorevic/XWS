package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.Advertisement;

public class SimpleAdvertisementDTO {
    private Long id;
    private Integer price;
    private String brandName;
    private String modelName;
    private String cityName;
    private String mainImagePath;

    public SimpleAdvertisementDTO() {
    }

    public SimpleAdvertisementDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.price = advertisement.getPriceFrom();
        this.brandName = advertisement.getBrandName();
        this.cityName = advertisement.getRentingCityLocation().getName();
        this.modelName = advertisement.getModelName();
        this.mainImagePath = advertisement.getMainImagePath();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
