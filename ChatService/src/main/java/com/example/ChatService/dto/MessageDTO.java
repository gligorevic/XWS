package com.example.ChatService.dto;

import java.util.Date;

public class MessageDTO {

    private String content;
    private Date timeSent;

    private Long userSentToId;
    private Long userSentFromId;

    private Long requestId;

    public MessageDTO() {
    }

    public MessageDTO(String content, Date timeSent, Long userSentToId, Long userSentFromId, Long requestId) {
        this.content = content;
        this.timeSent = timeSent;
        this.userSentToId = userSentToId;
        this.userSentFromId = userSentFromId;
        this.requestId = requestId;
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
