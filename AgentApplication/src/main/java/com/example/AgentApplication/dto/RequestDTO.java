package com.example.AgentApplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RequestDTO {

    private Long adId;

    private Date freeFrom;

    private Date freeTo;

    public RequestDTO() {
    }

    public RequestDTO(Long adId, Date freeFrom, Date freeTo) {
        this.adId = adId;
        this.freeFrom = freeFrom;
        this.freeTo = freeTo;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Date getFreeFrom() {
        return freeFrom;
    }

    public void setFreeFrom(Date freeFrom) {
        this.freeFrom = freeFrom;
    }

    public Date getFreeTo() {
        return freeTo;
    }

    public void setFreeTo(Date freeTo) {
        this.freeTo = freeTo;
    }
}
