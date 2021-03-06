package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.Car;

import java.util.Date;
import java.util.List;

public class CarDTO {
    private Long id;

    private String brandName;

    private String modelName;

    private String gearShiftName;

    private String fuelTypeName;

    private String bodyName;

    private Integer kmPassed;

    private String userEmail;

    private Date creationDate;

    private List<String> images;

    public CarDTO() {
    }

    public CarDTO(Car car, List<String> images) {
        this.id = car.getId();
        this.brandName = car.getBrand().getBrandName();
        this.modelName = car.getModel().getModelName();
        this.gearShiftName = car.getGearShift().getGearShiftName();
        this.fuelTypeName = car.getFuelType().getFuelTypeName();
        this.bodyName = car.getBodyType().getBodyTypeName();
        this.kmPassed = car.getKmPassed();
        this.userEmail = car.getUserEmail();
        this.creationDate = car.getCrationDate();
        this.images = images;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
