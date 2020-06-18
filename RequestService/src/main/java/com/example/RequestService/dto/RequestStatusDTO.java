package com.example.RequestService.dto;

import com.example.RequestService.domain.PaidState;

public class RequestStatusDTO {
    private String userEmail;
    private PaidState paidState;

    public RequestStatusDTO() {
    }

    public RequestStatusDTO(String userEmail, PaidState paidState) {
        this.userEmail = userEmail;
        this.paidState = paidState;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public PaidState getPaidState() {
        return paidState;
    }

    public void setPaidState(PaidState paidState) {
        this.paidState = paidState;
    }
}
