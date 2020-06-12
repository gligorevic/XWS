package com.example.SearchService.service;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    public List<Advertisement> getAdvertisementsCart(Long[] adIds) {
        return advertisementRepository.findAllByIdIn(adIds);
    }
}
