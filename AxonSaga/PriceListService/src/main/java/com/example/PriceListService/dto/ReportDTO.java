package com.example.PriceListService.dto;

import com.baeldung.springsoap.gen.Report;

import java.util.Date;

public class ReportDTO {

    private String text;

    private Integer km;

    private Long requestId;

    private Long adId;

    private Date rentedFrom;

    private Date rentedTo;

    private String userEmailRented; //ko je iznajmio

    public ReportDTO(){

    }

    public ReportDTO(Report report){
        this.km = report.getKm();
        this.rentedFrom = report.getRentedFrom().toGregorianCalendar().getTime();
        this.rentedTo = report.getRentedTo().toGregorianCalendar().getTime();
        this.text = report.getText();
        this.requestId = report.getRequestId();
        this.adId = report.getAdId();
        this.userEmailRented = report.getUserEmailRented();
    }

    public String getUserEmailRented() {
        return userEmailRented;
    }

    public void setUserEmailRented(String userEmailRented) {
        this.userEmailRented = userEmailRented;
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
}
