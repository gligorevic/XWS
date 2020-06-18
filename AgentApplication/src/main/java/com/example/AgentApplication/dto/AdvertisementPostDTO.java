package com.example.AgentApplication.dto;

import java.util.Date;

public class AdvertisementPostDTO {
    private Long carId;

    private Integer kmRestriction;

    private Integer price;

    private Integer numberChildSeats;

    private Boolean collisionDamage;

    private String cityName;

    private String rentingStreetLocation;

    private String rentingCityLocation;

    private Date freeFrom;

    private Date freeTo;

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

    public String getRentingCityLocation() {
        return rentingCityLocation;
    }

    public void setRentingCityLocation(String rentingCityLocation) {
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
