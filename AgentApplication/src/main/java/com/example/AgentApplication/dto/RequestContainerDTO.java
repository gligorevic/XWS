package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.RequestContainer;

import java.util.List;
import java.util.stream.Collectors;

public class RequestContainerDTO {

    private Long id;
    private String userEmail;
    private String userSentRequest;
    private List<RequestDTO> requestDTOS;

    public RequestContainerDTO() {
    }

    public RequestContainerDTO(Long id, String userEmail, String userSentRequest, List<RequestDTO> requestDTOS) {
        this.id = id;
        this.userEmail = userEmail;
        this.userSentRequest = userSentRequest;
        this.requestDTOS = requestDTOS;
    }

    public RequestContainerDTO(RequestContainer requestContainer){
        this.id = requestContainer.getId();
        this.userEmail = requestContainer.getUserEmail();
        this.userSentRequest = requestContainer.getUserSentRequest();
        this.requestDTOS = requestContainer.getBoundleList().stream().map(request -> new RequestDTO(request)).collect(Collectors.toList());;
    }

    public List<RequestDTO> getRequestDTOS() {
        return requestDTOS;
    }

    public void setRequestDTOS(List<RequestDTO> requestDTOS) {
        this.requestDTOS = requestDTOS;
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

}
