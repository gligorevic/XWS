package com.example.PriceListService.domain;


import com.example.PriceListService.dto.ReportDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Integer km;

    private Long adId;

    private Long requestId;

    private String userEmail;

    private Date rentedFrom;

    private Date rentedTo;

    private String userEmailRented;

    public Report(){

    }

    public Report(ReportDTO reportDTO){
        this.adId = reportDTO.getAdId();
        this.text = reportDTO.getText();
        this.km = reportDTO.getKm();
        this.requestId = reportDTO.getRequestId();
        this.rentedFrom = reportDTO.getRentedFrom();
        this.rentedTo = reportDTO.getRentedTo();
        this.userEmailRented = reportDTO.getUserEmailRented();
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

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Date getRentedFrom() {
        return rentedFrom;
    }

    public void setRentedFrom(Date rentedFrom) {
        this.rentedFrom = rentedFrom;
    }

    public Date getRentedTo() {
        return rentedTo;
    }

    public void setRentedTo(Date rentedTo) {
        this.rentedTo = rentedTo;
    }

    public String getUserEmailRented() {
        return userEmailRented;
    }

    public void setUserEmailRented(String userEmailRented) {
        this.userEmailRented = userEmailRented;
    }
}
