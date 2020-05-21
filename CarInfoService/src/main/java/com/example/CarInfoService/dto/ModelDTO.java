package com.example.CarInfoService.dto;

public class ModelDTO {

    private String modelName;
    private String brandName;
    private String fuelTypeName;
    private String gearShiftName;
    private String bodyTypeName;

    public ModelDTO() {
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }

    public String getGearShiftName() {
        return gearShiftName;
    }

    public void setGearShiftName(String gearShiftName) {
        this.gearShiftName = gearShiftName;
    }

    public String getBodyTypeName() {
        return bodyTypeName;
    }

    public void setBodyTypeName(String bodyTypeName) {
        this.bodyTypeName = bodyTypeName;
    }

    public ModelDTO(String modelName, String brandName, String fuelTypeName, String gearShiftName, String bodyTypeName) {
        this.modelName = modelName;
        this.brandName = brandName;
        this.fuelTypeName = fuelTypeName;
        this.gearShiftName = gearShiftName;
        this.bodyTypeName = bodyTypeName;
    }
}
