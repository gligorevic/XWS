package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.PriceListItem;

public class PriceListItemDTO {

    private String brandName;

    private String modelName;

    private Long advertisementId;

    private Float pricePerDay;

    private Float pricePerKm;

    private Float priceCollisionDamage;

    private Long priceListId;

    private Integer minNumberDays;

    private Integer percentage;

    public PriceListItemDTO(){

    }

    public PriceListItemDTO(PriceListItem priceListItem){
        this.advertisementId = priceListItem.getAdvertisementId();
        this.brandName = priceListItem.getBrandName();
        this.modelName = priceListItem.getModelName();
        this.minNumberDays = priceListItem.getMinNumberDays();
        this.percentage = priceListItem.getPercentage();
        this.priceListId = priceListItem.getPriceList().getId();
        this.pricePerDay = priceListItem.getPricePerDay();
        this.pricePerKm = priceListItem.getPricePerKm();
        this.priceCollisionDamage = priceListItem.getPriceCollisionDamage();
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

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    public Float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(Float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public Float getPriceCollisionDamage() {
        return priceCollisionDamage;
    }

    public void setPriceCollisionDamage(Float priceCollisionDamage) {
        this.priceCollisionDamage = priceCollisionDamage;
    }

    public Long getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(Long priceListId) {
        this.priceListId = priceListId;
    }

    public Integer getMinNumberDays() {
        return minNumberDays;
    }

    public void setMinNumberDays(Integer minNumberDays) {
        this.minNumberDays = minNumberDays;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
