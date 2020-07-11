package com.example.PriceListService.dto;

import java.util.Date;

public class DataForPriceCalculationDTO {

    private Date dateFrom;
    private Date dateTo;
    private Long adId;

    public DataForPriceCalculationDTO() {
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }
}
