package com.example.FeedbackService.domain;

import com.example.FeedbackService.dto.CommentDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private CommentStatus commentStatus;
    private Long requestId;
    private String username; // dao komentar
    private String agentUsername;
    private boolean inBundle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date creationDate;


    public Comment() {
    }

    public Comment(String text, CommentStatus commentStatus) {
        this.text = text;
        this.commentStatus = commentStatus;
    }


    public Comment(CommentDTO commentDTO){
        this.text = commentDTO.getText();
        this.commentStatus = CommentStatus.PENDING;
        this.requestId = commentDTO.getRequestId();
        this.username = commentDTO.getUsername();
        this.agentUsername = commentDTO.getAgentUsername();
        this.inBundle = commentDTO.isInBundle();
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }


    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
