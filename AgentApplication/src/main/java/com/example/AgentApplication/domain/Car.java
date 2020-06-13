package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.CarDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Model model;

    @ManyToOne
    private GearShiftType gearShift;

    @ManyToOne
    private FuelType fuelType;

    @ManyToOne
    private BodyType bodyType;

    private Integer kmPassed;

    private String userEmail;

    private String locationToken;

    private Date creationDate;

    private String mainImageUrl;

    public Car(){
    }

    public Car( Integer kmPassed, String userAgentId, String locationToken) {
        this.kmPassed = kmPassed;
        this.userEmail = userAgentId;
        this.locationToken = locationToken;
    }

    public Car(CarDTO carDTO, String mainImageUrl) {
        this.kmPassed = carDTO.getKmPassed();
        this.userEmail = carDTO.getUserEmail();
        this.mainImageUrl = mainImageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public GearShiftType getGearShift() {
        return gearShift;
    }

    public void setGearShift(GearShiftType gearShift) {
        this.gearShift = gearShift;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getKmPassed() {
        return kmPassed;
    }

    public void setKmPassed(Integer kmPassed) {
        this.kmPassed = kmPassed;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLocationToken() {
        return locationToken;
    }

    public void setLocationToken(String locationToken) {
        this.locationToken = locationToken;
    }

    public Date getCrationDate() {
        return creationDate;
    }

    public void setCrationDate(Date crationDate) {
        this.creationDate = crationDate;
    }

    @PrePersist
    protected void onCreate(){
        this.creationDate= new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }
}
