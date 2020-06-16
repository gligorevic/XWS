package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.Comment;

import java.util.Date;

public class CommentDTO {

    private String text;

    private String userEmail;

    private Date creationDate;

    public CommentDTO(Comment comment){
        this.creationDate = comment.getCrationDate();
        this.text = comment.getText();
        this.userEmail = comment.getUser().getEmail();
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
}
