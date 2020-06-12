package com.example.CoreAPI.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Map;

public class AddImagesCommand {

    @TargetAggregateIdentifier
    private String imageAggregateId;

    private Long carId;
    private Map<String, byte[]> images;

    public AddImagesCommand() {
    }

    public AddImagesCommand(String imageAggregateId, Long carId, Map<String, byte[]> images) {
        this.imageAggregateId = imageAggregateId;
        this.carId = carId;
        this.images = images;
    }

    public String getImageAggregateId() {
        return imageAggregateId;
    }

    public void setImageAggregateId(String imageAggregateId) {
        this.imageAggregateId = imageAggregateId;
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
