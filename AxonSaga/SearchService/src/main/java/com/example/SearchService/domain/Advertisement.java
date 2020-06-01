package com.example.SearchService.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long carId;

    private Integer kmRestriction;

    private Integer priceFrom;

    private Integer priceTo;

    private String brandName;

    private String modelName;

    private String gearShiftName;

    private String fuelTypeName;

    private String bodyName;

    private Integer kmPassed;

    private Integer numberChildSeats;

    private Boolean collisionDamage;

    private String userAgentId;

    public Advertisement() {

    }

    public Advertisement(Long carId,Integer kmRestriction, Integer priceFrom, Integer priceTo, String brandName, String modelName, String gearShiftName, String fuelTypeName, String bodyName, Integer kmPassed,Integer numberChildSeats, Boolean collisionDamage, String userAgentId){
        this.carId = carId;
        this.kmRestriction = kmRestriction;
        this.priceFrom= priceFrom;
        this.priceTo = priceTo;
        this.brandName = brandName;
        this.modelName = modelName;
        this.gearShiftName = gearShiftName;
        this.fuelTypeName = fuelTypeName;
        this.bodyName = bodyName;
        this.kmPassed = kmPassed;
        this.numberChildSeats = numberChildSeats;
        this.collisionDamage = collisionDamage;
        this.userAgentId = userAgentId;
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

    public Integer getKmRestriction() {
        return kmRestriction;
    }

    public void setKmRestriction(Integer kmRestriction) {
        this.kmRestriction = kmRestriction;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
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

    public String getGearShiftName() {
        return gearShiftName;
    }

    public void setGearShiftName(String gearShiftName) {
        this.gearShiftName = gearShiftName;
    }

    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }

    public Integer getKmPassed() {
        return kmPassed;
    }

    public void setKmPassed(Integer kmPassed) {
        this.kmPassed = kmPassed;
    }

    public Integer getNumberChildSeats() {
        return numberChildSeats;
    }

    public void setNumberChildSeats(Integer numberChildSeats) {
        this.numberChildSeats = numberChildSeats;
    }

    public Boolean getCollisionDamage() {
        return collisionDamage;
    }

    public void setCollisionDamage(Boolean collisionDamage) {
        this.collisionDamage = collisionDamage;
    }

    public String getUserAgentId() {
        return userAgentId;
    }

    public void setUserAgentId(String userAgentId) {
        this.userAgentId = userAgentId;
    }


}
