package com.example.CarInfoService.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelName;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "model", orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Collection<FuelType> fuelTypes = new HashSet<>();

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "model", orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Collection<BodyType> bodyTypes = new HashSet<>();

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "model", orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Collection<GearShiftType> gearShiftTypes = new HashSet<>();

    private Brand brand;

    public Model() {
    }

    public Model(String modelName, Collection<FuelType> fuelTypes, Collection<BodyType> bodyTypes, Collection<GearShiftType> gearShiftTypes, Brand brand) {
        this.modelName = modelName;
        this.fuelTypes = fuelTypes;
        this.bodyTypes = bodyTypes;
        this.gearShiftTypes = gearShiftTypes;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Collection<FuelType> getFuelTypes() {
        return fuelTypes;
    }

    public void setFuelTypes(Collection<FuelType> fuelTypes) {
        this.fuelTypes = fuelTypes;
    }

    public Collection<BodyType> getBodyTypes() {
        return bodyTypes;
    }

    public void setBodyTypes(Collection<BodyType> bodyTypes) {
        this.bodyTypes = bodyTypes;
    }

    public Collection<GearShiftType> getGearShiftTypes() {
        return gearShiftTypes;
    }

    public void setGearShiftTypes(Collection<GearShiftType> gearShiftTypes) {
        this.gearShiftTypes = gearShiftTypes;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
