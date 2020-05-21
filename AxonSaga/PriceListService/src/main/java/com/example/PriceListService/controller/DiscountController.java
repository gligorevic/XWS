package com.example.PriceListService.controller;

import com.example.PriceListService.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;
}
