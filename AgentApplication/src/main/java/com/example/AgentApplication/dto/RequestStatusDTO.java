package com.example.AgentApplication.dto;

import com.example.AgentApplication.enumeration.PaidState;

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
