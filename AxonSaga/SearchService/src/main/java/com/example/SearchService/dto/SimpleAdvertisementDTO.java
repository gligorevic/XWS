package com.example.SearchService.dto;

import com.example.SearchService.domain.Advertisement;

public class SimpleAdvertisementDTO {
    private Integer price;

    private String brandName;

    private String modelName;

    private String cityName;

    public SimpleAdvertisementDTO() {
    }

    public SimpleAdvertisementDTO(Advertisement advertisement) {
        this.price = advertisement.getPriceFrom();
        this.brandName = advertisement.getBrandName();
        this.cityName = advertisement.getRentingCityLocation().getName();
        this.modelName = advertisement.getModelName();
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
}
