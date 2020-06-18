package com.example.FeedbackService.domain;

import com.example.FeedbackService.dto.CommentDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    private CommentStatus commentStatus;
    private Long requestId;
    private String username; // dao komentar



    public Comment(String text, CommentStatus commentStatus) {
        this.text = text;
        this.commentStatus = commentStatus;
    }


    public Comment(CommentDTO commentDTO){
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    public long getId() {
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

    public void setId(long id) {
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
