package com.example.RequestService.dto;

import com.example.RequestService.domain.PaidState;

public class RequestDTO {

    private PaidState paidState;

    //------Need to change variable type to Collection<Advertisement> when class Advertisement is made-------
    private Long advertisementIds;

    public RequestDTO() {
    }

    public PaidState getPaidState() {
        return paidState;
    }

    public void setPaidState(PaidState paidState) {
        this.paidState = paidState;
    }
}
