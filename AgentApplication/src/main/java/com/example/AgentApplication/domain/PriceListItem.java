package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.PriceListItemDTO;

import javax.persistence.*;

@Entity
public class PriceListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brandName;

    private String modelName;

    private Long advertisementId;

    private Float pricePerDay;

    private Float pricePerKm;

    private Float priceCollisionDamage;

    private Long remoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pricelist_id")
    private PriceList priceList;

    private Integer minNumberDays;

    private Integer percentage;

    public PriceListItem(){

    }

    public PriceListItem(PriceListItemDTO priceListItemDTO){
        this.advertisementId = priceListItemDTO.getAdvertisementId();
        this.modelName = priceListItemDTO.getModelName();
        this.brandName = priceListItemDTO.getBrandName();
        this.priceCollisionDamage = priceListItemDTO.getPriceCollisionDamage();
        this.pricePerDay = priceListItemDTO.getPricePerDay();
        this.pricePerKm = priceListItemDTO.getPricePerKm();
        this.minNumberDays = priceListItemDTO.getMinNumberDays();
        this.percentage = priceListItemDTO.getPercentage();
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


    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
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

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }
}
