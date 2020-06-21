package com.example.AgentApplication.dto;

public class MessageDTO {

    private String text;

    private Long sentBy;

    private Long requestId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getSentBy() {
        return sentBy;
    }

    public void setSentBy(Long sentBy) {
        this.sentBy = sentBy;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
}
