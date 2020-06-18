package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.AdvertisementDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Car car;

    private Integer kmRestriction;

    private Integer priceFrom;

    private Integer priceTo;

    private Integer numberChildSeats;

    private Boolean collisionDamage;

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
        this.kmRestriction = advertisementDTO.getKmRestriction();
        this.priceFrom= advertisementDTO.getPrice();
        this.priceTo = advertisementDTO.getPrice();
        this.numberChildSeats = advertisementDTO.getNumberChildSeats();
        this.collisionDamage = advertisementDTO.getCollisionDamage();
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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
