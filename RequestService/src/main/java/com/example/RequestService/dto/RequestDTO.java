package com.example.RequestService.dto;

import com.example.RequestService.domain.PaidState;

import java.util.Date;
import java.util.List;

public class RequestDTO {

    private PaidState paidState;

    private Long userSendingRequest;

    private List<Long> adIds;
    private String userEmail;

    public RequestDTO() {
    }

    public RequestDTO(Long userSendingRequest, String userEmail, List<Long> adIds) {
        this.userSendingRequest = userSendingRequest;
        this.userEmail = userEmail;
        this.adIds = adIds;

        this.paidState = PaidState.PENDING;
    }

    public PaidState getPaidState() {
        return paidState;
    }

    public void setPaidState(PaidState paidState) {
        this.paidState = paidState;
    }


}
