package com.example.SearchService.service;

import com.example.SearchService.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

}
