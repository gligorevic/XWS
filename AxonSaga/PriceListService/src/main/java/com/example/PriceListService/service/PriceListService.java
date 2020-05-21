package com.example.PriceListService.service;

import com.example.PriceListService.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;
}
