package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.Comment;

import java.util.Date;

public class CommentDTO {

    private String text;

    private String userEmail;

    private Date creationDate;

    private Long requestId;

    private String commentStatus;

    private boolean inBundle;

    public CommentDTO(){
        
    }

    public CommentDTO(Comment comment){
        this.creationDate = comment.getCreationDate();
        this.text = comment.getText();
        this.userEmail = comment.getUser().getEmail();
        this.requestId = comment.getRequest().getId();
        this.commentStatus = comment.getCommentStatus().toString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public boolean isInBundle() {
        return inBundle;
    }

    public void setInBundle(boolean inBundle) {
        this.inBundle = inBundle;
    }
}
