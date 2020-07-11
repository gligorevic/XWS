package com.example.PriceListService.dto;

import com.baeldung.springsoap.gen.Pricelist;
import com.example.PriceListService.domain.PriceList;

import java.util.Date;

public class PriceListDTO {

    private Date validFrom;

    private Date validTo;

    public PriceListDTO(){

    }

    public PriceListDTO(Pricelist pricelist){
        this.validFrom = pricelist.getValidFrom().toGregorianCalendar().getTime();
        this.validTo = pricelist.getValidTo().toGregorianCalendar().getTime();
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
}
