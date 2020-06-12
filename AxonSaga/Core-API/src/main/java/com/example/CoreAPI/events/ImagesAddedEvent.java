package com.example.CoreAPI.events;

public class ImagesAddedEvent {
    private String imageAggregateId;

    public ImagesAddedEvent() {
    }

    public ImagesAddedEvent(String imageAggregateId) {
        this.imageAggregateId = imageAggregateId;
    }

    public String getImageAggregateId() {
        return imageAggregateId;
    }

    public void setImageAggregateId(String imageAggregateId) {
        this.imageAggregateId = imageAggregateId;
    }
}
