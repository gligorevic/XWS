package com.example.ChatService.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date timeSent;

    private Long userSentToId;
    private Long userSentFromId;

    private Long requestId;

    public Message() {
    }

    public Message(String content, Date timeSent, Long userSentToId, Long userSentFromId, Long requestId) {
        this.content = content;
        this.timeSent = timeSent;
        this.userSentToId = userSentToId;
        this.userSentFromId = userSentFromId;
        this.requestId = requestId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public Long getUserSentToId() {
        return userSentToId;
    }

    public void setUserSentToId(Long userSentToId) {
        this.userSentToId = userSentToId;
    }

    public Long getUserSentFromId() {
        return userSentFromId;
    }

    public void setUserSentFromId(Long userSentFromId) {
        this.userSentFromId = userSentFromId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
}
