package com.example.RequestService.dto;

import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

public class RequestStatisticDTO {

    private Long id;

    private Long adId;

    private Date startDate;

    private Date endDate;

    private Date crationDate;

    private String userEmail;

    public RequestStatisticDTO(Request request){
        this.adId = request.getAdId();
        this.id = request.getId();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.userEmail = request.getUserEmail();
        this.crationDate = request.getCrationDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCrationDate() {
        return crationDate;
    }

    public void setCrationDate(Date crationDate) {
        this.crationDate = crationDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
