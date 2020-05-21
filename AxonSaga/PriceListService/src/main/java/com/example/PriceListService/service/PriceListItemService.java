package com.example.PriceListService.service;

import com.example.PriceListService.repository.PriceListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceListItemService {

    @Autowired
    private PriceListItemRepository priceListItemRepository;
}
