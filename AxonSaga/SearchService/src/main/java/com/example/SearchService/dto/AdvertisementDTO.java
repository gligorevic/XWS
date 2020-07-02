package com.example.SearchService.dto;

import com.example.SearchService.domain.Advertisement;

import java.util.Date;
import java.util.List;

public class AdvertisementDTO {
    private Long id;

    private Long carId;

    private Integer kmRestriction;

    private Integer price;

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

    private String cityName;

    private String rentingStreetLocation;

    private Date freeFrom;

    private Date freeTo;

    private String mainImagePath;

    private List<String> images;

    public AdvertisementDTO() {
    }

    public AdvertisementDTO(Advertisement advertisement, List<String> images) {
        this.id = advertisement.getId();
        this.carId = advertisement.getCarId();
        this.kmRestriction = advertisement.getKmRestriction();
        this.price = advertisement.getPriceFrom();
        this.priceTo = advertisement.getPriceTo();
        this.brandName = advertisement.getBrandName();
        this.modelName = advertisement.getModelName();
        this.gearShiftName = advertisement.getGearShiftName();
        this.fuelTypeName = advertisement.getFuelTypeName();
        this.bodyName = advertisement.getBodyName();
        this.kmPassed = advertisement.getKmPassed();
        this.numberChildSeats = advertisement.getNumberChildSeats();
        this.collisionDamage = advertisement.getCollisionDamage();
        this.userEmail = advertisement.getUserEmail();
        this.cityName = advertisement.getRentingCityLocation().getName();
        this.rentingStreetLocation = advertisement.getRentingStreetLocation();
        this.freeFrom = advertisement.getFreeFrom();
        this.freeTo = advertisement.getFreeTo();
        this.mainImagePath = advertisement.getMainImagePath();
        this.images = images;
    }

    public AdvertisementDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.carId = advertisement.getCarId();
        this.kmRestriction = advertisement.getKmRestriction();
        this.price = advertisement.getPriceFrom();
        this.priceTo = advertisement.getPriceTo();
        this.brandName = advertisement.getBrandName();
        this.modelName = advertisement.getModelName();
        this.gearShiftName = advertisement.getGearShiftName();
        this.fuelTypeName = advertisement.getFuelTypeName();
        this.bodyName = advertisement.getBodyName();
        this.kmPassed = advertisement.getKmPassed();
        this.numberChildSeats = advertisement.getNumberChildSeats();
        this.collisionDamage = advertisement.getCollisionDamage();
        this.userEmail = advertisement.getUserEmail();
        this.cityName = advertisement.getRentingCityLocation().getName();
        this.rentingStreetLocation = advertisement.getRentingStreetLocation();
        this.freeFrom = advertisement.getFreeFrom();
        this.freeTo = advertisement.getFreeTo();
        this.mainImagePath = advertisement.getMainImagePath();
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRentingStreetLocation() {
        return rentingStreetLocation;
    }

    public void setRentingStreetLocation(String rentingStreetLocation) {
        this.rentingStreetLocation = rentingStreetLocation;
    }

    public String getMainImagePath() {
        return mainImagePath;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }
}
