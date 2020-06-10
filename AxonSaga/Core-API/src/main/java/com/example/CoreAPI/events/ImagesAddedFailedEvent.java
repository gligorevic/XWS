package com.example.CoreAPI.events;

public class ImagesAddedFailedEvent {
    private String imageAggregateId;
    private Long carId;
    private String reason;

    public ImagesAddedFailedEvent() {
    }

    public ImagesAddedFailedEvent(String imageAggregateId, Long carId, String reason) {
        this.imageAggregateId = imageAggregateId;
        this.carId = carId;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
