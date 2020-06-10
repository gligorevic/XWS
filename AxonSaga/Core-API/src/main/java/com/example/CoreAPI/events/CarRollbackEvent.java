package com.example.CoreAPI.events;

public class CarRollbackEvent {
    private Long carId;

    public CarRollbackEvent() {
    }

    public CarRollbackEvent(Long carId) {
        this.carId = carId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
