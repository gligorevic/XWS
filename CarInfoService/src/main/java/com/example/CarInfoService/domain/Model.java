package com.example.CarInfoService.domain;

import com.example.CarInfoService.dto.ModelDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelName;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "MODEL_FUEL_TYPES",
            joinColumns = @JoinColumn(
                    name = "MODEL_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "FUEL_TYPE_ID", referencedColumnName = "id"))
    private List<FuelType> fuelTypes = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "MODEL_BODY_TYPES",
            joinColumns = @JoinColumn(
                    name = "MODEL_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "BODY_TYPE_ID", referencedColumnName = "id"))
    private List<BodyType> bodyTypes = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "MODEL_GEAR_SHIFT_TYPES",
            joinColumns = @JoinColumn(
                    name = "MODEL_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "GEAR_SHIFT_TYPE_ID", referencedColumnName = "id"))
    private List<GearShiftType> gearShiftTypes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id", updatable = false, nullable = false)
    @JsonIgnore
    private Brand brand;

    public Model() {
    }

    public Model(String modelName, List<FuelType> fuelTypes, List<BodyType> bodyTypes, List<GearShiftType> gearShiftTypes, Brand brand) {
        this.modelName = modelName;
        this.fuelTypes = fuelTypes;
        this.bodyTypes = bodyTypes;
        this.gearShiftTypes = gearShiftTypes;
        this.brand = brand;
    }

    public Model(String modelName) {
        this.modelName = modelName;

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

    public List<FuelType> getFuelTypes() {
        return fuelTypes;
    }

    public void setFuelTypes(List<FuelType> fuelTypes) {
        this.fuelTypes = fuelTypes;
    }

    public List<BodyType> getBodyTypes() {
        return bodyTypes;
    }

    public void setBodyTypes(List<BodyType> bodyTypes) {
        this.bodyTypes = bodyTypes;
    }

    public List<GearShiftType> getGearShiftTypes() {
        return gearShiftTypes;
    }

    public void setGearShiftTypes(List<GearShiftType> gearShiftTypes) {
        this.gearShiftTypes = gearShiftTypes;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
