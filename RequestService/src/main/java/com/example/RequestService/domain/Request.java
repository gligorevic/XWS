package com.example.RequestService.domain;

import com.example.RequestService.dto.RequestDTO;

import javax.persistence.*;
import java.util.*;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PaidState paidState;

    private Long userSendingRequest;

    private List<Long> adIds;
    private String userEmail;

    public Request(RequestDTO requestDTO) {
        this.paidState = PaidState.PENDING;
    }

    public Request(PaidState paidState) {
        this.paidState = paidState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaidState getPaidState() {
        return paidState;
    }

    public void setPaidState(PaidState paidState) {
        this.paidState = paidState;
    }
}
