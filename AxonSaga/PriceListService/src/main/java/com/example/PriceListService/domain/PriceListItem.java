package com.example.PriceListService.domain;

import com.example.PriceListService.dto.PriceListItemDTO;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pricelist_id")
    private PriceList priceList;

    @OneToOne
    private Discount discount;

    public PriceListItem(){

    }

    public PriceListItem(PriceListItemDTO priceListItemDTO){
        this.advertisementId = priceListItemDTO.getAdvertisementId();
        this.modelName = priceListItemDTO.getModelName();
        this.brandName = priceListItemDTO.getBrandName();
        this.priceCollisionDamage = priceListItemDTO.getPriceCollisionDamage();
        this.pricePerDay = priceListItemDTO.getPricePerDay();
        this.pricePerKm = priceListItemDTO.getPricePerKm();
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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
