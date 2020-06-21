package com.example.RequestService.service;

import com.example.RequestService.client.AdvertisementClient;
import com.example.RequestService.domain.Request;
import com.example.RequestService.dto.AdvertisementDTO;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private AdvertisementClient advertisementClient;

    public List<RequestDTO> getAllCreatedRequests(String email, String auth) {
        List<Request> requests = requestRepository.findAllByUserSentRequest(email);
        if(requests.isEmpty()) {
            return new ArrayList<>();
        }

        List<RequestDTO> requestDTOs = new ArrayList<>();
        for(Request request : requests) {
            requestDTOs.add(new RequestDTO(request));
        }

        List<AdvertisementDTO> advertisementsFromRequests = advertisementClient.getAdvertisementsByIds(requests.stream().map(request -> request.getAdId()).collect(Collectors.toList()), auth).getBody();

        for(AdvertisementDTO a : advertisementsFromRequests) {
            for(RequestDTO request : requestDTOs) {
                if(request.getAdId() == a.getId()) {
                    request.setBrandName(a.getBrandName());
                    request.setModelName(a.getModelName());
                    request.setPrice(a.getPrice());
                }
            }
        }

        return requestDTOs;
    }

    public List<Request> getAllPaid(String email) {

        return requestRepository.findAllPaid(email);
    }
}
