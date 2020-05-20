package com.example.PriceListService.controller;

import com.example.PriceListService.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/priceList")
public class PriceListController {

    @Autowired
    private PriceListService priceListService;
}
