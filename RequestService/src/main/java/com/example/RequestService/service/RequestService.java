package com.example.RequestService.service;

import com.example.RequestService.domain.Request;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request add(Request request) {

        return requestRepository.save(request);
    }
}
