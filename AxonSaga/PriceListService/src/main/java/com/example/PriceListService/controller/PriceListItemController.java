package com.example.PriceListService.controller;

import com.example.PriceListService.service.PriceListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/priceListItem")
public class PriceListItemController {

    @Autowired
    private PriceListItemService priceListItemService;
}
