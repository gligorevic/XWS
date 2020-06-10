package com.example.CoreAPI.events;

import java.util.Map;

public class CarCreatedEvent {
    private Long carId;

    private Map<String, byte[]> images;

    public CarCreatedEvent() {
    }

    public CarCreatedEvent(Long carId, Map<String, byte[]> images) {
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
