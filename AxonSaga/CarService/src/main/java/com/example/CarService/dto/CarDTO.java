package com.example.CarService.dto;

public class CarDTO {
    private String brandName;

    private String modelName;

    private String gearShiftName;

    private String fuelTypeName;

    private String bodyName;

    private Integer kmPassed;

    private Long userAgentId;

    private boolean isAgent;

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

    public Long getUserAgentId() {
        return userAgentId;
    }

    public void setUserAgentId(Long userAgentId) {
        this.userAgentId = userAgentId;
    }

    public boolean isAgent() {
        return isAgent;
    }

    public void setAgent(boolean agent) {
        isAgent = agent;
    }
}
