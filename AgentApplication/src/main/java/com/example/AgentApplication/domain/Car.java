package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.CarDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brandName;

    private String modelName;

    private String gearShiftName;

    private String fuelTypeName;

    private String bodyName;

    private Integer kmPassed;

    private String userEmail;

    private String locationToken;

    private Date crationDate;

    public Car(){
    }

    public Car(String brandName, String modelName, String gearShiftName, String fuelTypeName, String bodyName, Integer kmPassed, String userAgentId, String locationToken) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.gearShiftName = gearShiftName;
        this.fuelTypeName = fuelTypeName;
        this.bodyName = bodyName;
        this.kmPassed = kmPassed;
        this.userEmail = userAgentId;
        this.locationToken = locationToken;
    }

    public Car(CarDTO carDTO) {
        this.brandName = carDTO.getBrandName();
        this.modelName = carDTO.getModelName();
        this.gearShiftName = carDTO.getGearShiftName();
        this.fuelTypeName = carDTO.getFuelTypeName();
        this.bodyName = carDTO.getBodyName();
        this.kmPassed = carDTO.getKmPassed();
        this.userEmail = carDTO.getUserEmail();
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
        return crationDate;
    }

    public void setCrationDate(Date crationDate) {
        this.crationDate = crationDate;
    }

    @PrePersist
    protected void onCreate(){
        this.crationDate= new Date();
    }
}
