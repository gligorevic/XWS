package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.RequestContainerDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RequestContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private String userSentRequest;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Request> boundleList;

    public RequestContainer() {
    }

    public RequestContainer(List<Request> boundleList) {
        this.boundleList = boundleList;
    }

    public RequestContainer(RequestContainerDTO requestContainerDTO) {
        this.userEmail = requestContainerDTO.getUserEmail();
        this.userSentRequest = requestContainerDTO.getUserSentRequest();
        this.boundleList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSentRequest() {
        return userSentRequest;
    }

    public void setUserSentRequest(String userSentRequest) {
        this.userSentRequest = userSentRequest;
    }

    public List<Request> getBoundleList() {
        return boundleList;
    }

    public void setBoundleList(List<Request> boundleList) {
        this.boundleList = boundleList;
    }
}
