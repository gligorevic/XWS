package com.example.SearchService.service;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public List<AdvertisementDTO> getAdvertisementsByIds(List<Long> adIds) {
        List<Advertisement> advertisements = advertisementRepository.findAllById(adIds);
        if(!advertisements.isEmpty()) {
            return advertisements.stream().map(advertisement -> new AdvertisementDTO(advertisement)).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
