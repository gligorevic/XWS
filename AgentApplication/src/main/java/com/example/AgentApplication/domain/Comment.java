package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.CommentDTO;
import com.example.AgentApplication.enumeration.CommentStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private CommentStatus commentStatus;

    @ManyToOne
    private Request request;

    @ManyToOne
    private User user;// dao komentar

    private Date creationDate;

    private boolean inBundle;


    public Comment(){
    }

    public Comment(CommentDTO commentDTO){
        this.text = commentDTO.getText();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @PrePersist
    protected void onCreate(){
        this.creationDate= new Date();
    }

    public boolean isInBundle() {
        return inBundle;
    }

    public void setInBundle(boolean inBundle) {
        this.inBundle = inBundle;
    }
}
