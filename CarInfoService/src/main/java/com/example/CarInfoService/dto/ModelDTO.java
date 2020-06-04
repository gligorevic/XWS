package com.example.CarInfoService.dto;

import java.util.HashSet;

public class ModelDTO {

    private String modelName;
    private String brandName;
    private HashSet<String> fuelTypeNames;
    private HashSet<String> gearShiftNames;
    private HashSet<String> bodyTypeNames;

    public ModelDTO() {
    }

    public ModelDTO(String modelName, String brandName, HashSet<String> fuelTypeNames, HashSet<String> gearShiftNames, HashSet<String> bodyTypeNames) {
        this.modelName = modelName;
        this.brandName = brandName;
        this.fuelTypeNames = fuelTypeNames;
        this.gearShiftNames = gearShiftNames;
        this.bodyTypeNames = bodyTypeNames;
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

    public HashSet<String> getFuelTypeNames() {
        return fuelTypeNames;
    }

    public void setFuelTypeNames(HashSet<String> fuelTypeName) {
        this.fuelTypeNames = fuelTypeName;
    }

    public HashSet<String> getGearShiftNames() {
        return gearShiftNames;
    }

    public void setGearShiftNames(HashSet<String> gearShiftName) {
        this.gearShiftNames = gearShiftName;
    }

    public HashSet<String> getBodyTypeNames() {
        return bodyTypeNames;
    }

    public void setBodyTypeNames(HashSet<String> bodyTypeName) {
        this.bodyTypeNames = bodyTypeName;
    }
}
