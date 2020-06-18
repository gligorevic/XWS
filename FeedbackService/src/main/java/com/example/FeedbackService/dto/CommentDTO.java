package com.example.FeedbackService.dto;

import com.example.FeedbackService.domain.CommentStatus;

public class CommentDTO {

    private Long id;
    private String text;
    private Long requestId;
    private String username; // dao komentar

    public CommentDTO(){}

    public CommentDTO(Long id, String text, Long requestId, String username) {
        this.id = id;
        this.text = text;
        this.requestId = requestId;
        this.username = username;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
