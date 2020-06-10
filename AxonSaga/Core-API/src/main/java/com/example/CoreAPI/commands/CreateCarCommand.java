package com.example.CoreAPI.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Map;

public class CreateCarCommand {
    @TargetAggregateIdentifier
    private Long carId;

    private Map<String, byte[]> images;

    public CreateCarCommand() {
    }

    public CreateCarCommand(Long carId, Map<String, byte[]> images) {
        this.carId = carId;
        this.images = images;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Map<String, byte[]> getImages() {
        return images;
    }

    public void setImages(Map<String, byte[]> images) {
        this.images = images;
    }
}
