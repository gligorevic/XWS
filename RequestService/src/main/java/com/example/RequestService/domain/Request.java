package com.example.RequestService.domain;

import com.example.RequestService.dto.RequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PaidState paidState;

    private Long adId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Belgrade")
    private Date endDate;

    public Request() {
    }

    public Request(RequestDTO requestDTO) {
        this.adId = requestDTO.getId();
        this.startDate = requestDTO.getFreeFrom();
        this.endDate = requestDTO.getFreeTo();
        this.paidState = PaidState.PENDING;
    }

    public Request(PaidState paidState) {
        this.paidState = paidState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaidState getPaidState() {
        return paidState;
    }

    public void setPaidState(PaidState paidState) {
        this.paidState = paidState;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

}
