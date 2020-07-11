package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.PriceListDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date validFrom;

    private Date validTo;

    private Boolean excludeYear;

    private Long remoteId;

    public PriceList(){

    }

    public PriceList(PriceListDTO priceListDTO){
        this.validFrom = priceListDTO.getValidFrom();
        this.validTo = priceListDTO.getValidTo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Boolean getExcludeYear() {
        return excludeYear;
    }

    public void setExcludeYear(Boolean excludeYear) {
        this.excludeYear = excludeYear;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }
}
