package com.example.FeedbackService.dto;

import com.example.FeedbackService.domain.CommentStatus;

import java.util.Date;

public class CommentDTO {

    private String text;
    private Long requestId;
    private String username;
    private String agentUsername;
    private boolean inBundle;

    public CommentDTO(){}

    public CommentDTO(String text, Long requestId, String username) {
        this.text = text;
        this.requestId = requestId;
        this.username = username;
    }

    public String getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String agentUsername) {
        this.agentUsername = agentUsername;
    }

    public boolean isInBundle() {
        return inBundle;
    }

    public void setInBundle(boolean inBundle) {
        this.inBundle = inBundle;
    }

    public String getText() {
        return text;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getUsername() {
        return username;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
