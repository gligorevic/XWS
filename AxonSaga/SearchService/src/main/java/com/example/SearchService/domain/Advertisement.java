package com.example.SearchService.domain;

import com.example.SearchService.dto.AdvertisementDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

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

    private String userEmail;

    @ManyToOne
    private City rentingCityLocation;

    private String rentingStreetLocation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date freeFrom;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date freeTo;

    public Advertisement() {

    }

    public Advertisement(AdvertisementDTO advertisementDTO){
        this.carId = advertisementDTO.getCarId();
        this.kmRestriction = advertisementDTO.getKmRestriction();
        this.priceFrom= advertisementDTO.getPrice();
        this.priceTo = advertisementDTO.getPrice();
        this.brandName = advertisementDTO.getBrandName();
        this.modelName = advertisementDTO.getModelName();
        this.gearShiftName = advertisementDTO.getGearShiftName();
        this.fuelTypeName = advertisementDTO.getFuelTypeName();
        this.bodyName =advertisementDTO.getBodyName();
        this.kmPassed = advertisementDTO.getKmPassed();
        this.numberChildSeats = advertisementDTO.getNumberChildSeats();
        this.collisionDamage = advertisementDTO.getCollisionDamage();
        this.userEmail = advertisementDTO.getUserEmail();
        this.rentingStreetLocation = advertisementDTO.getRentingStreetLocation();
        this.freeFrom = advertisementDTO.getFreeFrom();
        this.freeTo = advertisementDTO.getFreeTo();
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRentingStreetLocation() {
        return rentingStreetLocation;
    }

    public void setRentingStreetLocation(String rentingStreetLocation) {
        this.rentingStreetLocation = rentingStreetLocation;
    }

    public City getRentingCityLocation() {
        return rentingCityLocation;
    }

    public void setRentingCityLocation(City rentingCityLocation) {
        this.rentingCityLocation = rentingCityLocation;
    }

    public Date getFreeFrom() {
        return freeFrom;
    }

    public void setFreeFrom(Date freeFrom) {
        this.freeFrom = freeFrom;
    }

    public Date getFreeTo() {
        return freeTo;
    }

    public void setFreeTo(Date freeTo) {
        this.freeTo = freeTo;
    }
}
