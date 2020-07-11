package com.example.SearchService.dto;

import com.baeldung.springsoap.gen.ReservationPeriod;

import java.util.Date;

public class ReservationPeriodDTO {

    private Date startDate;

    private Date endDate;

    private Long advertisementId;

    public ReservationPeriodDTO(){

    }

    public ReservationPeriodDTO(ReservationPeriod reservationPeriod){
        this.advertisementId = reservationPeriod.getAdvertisementId();
        this.startDate = reservationPeriod.getStartDate().toGregorianCalendar().getTime();
        this.endDate = reservationPeriod.getEndDate().toGregorianCalendar().getTime();
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

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }
}
