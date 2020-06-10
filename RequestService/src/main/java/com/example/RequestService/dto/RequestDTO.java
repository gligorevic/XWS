package com.example.RequestService.dto;

import com.example.RequestService.domain.PaidState;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RequestDTO {

    private Long id;

    private Date freeFrom;

    private Date freeTo;

    public RequestDTO() {
    }

    public RequestDTO(Long id, Date freeFrom, Date freeTo) {
        this.id = id;
        this.freeFrom = freeFrom;
        this.freeTo = freeTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
