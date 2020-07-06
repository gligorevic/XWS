package com.example.RequestService.service;

import com.example.RequestService.domain.Request;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserRequestService {

    @Autowired
    private RequestRepository requestRepository;


    public List<RequestDTO> getAllCreatedRequests(String email) {
        List<Request> requests = requestRepository.findAllByUserSentRequest(email);

        List<RequestDTO> requestDTOs = new ArrayList<>();
        for(Request request : requests) {
            requestDTOs.add(new RequestDTO(request));
        }

        return requestDTOs;
    }


    public List<Request> getAllPaid(String email) {
        return requestRepository.findAllPaid(email);
    }
}
