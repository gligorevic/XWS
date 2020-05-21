package com.example.PriceListService.controller;

import com.example.PriceListService.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/report")
public class ReportController {

    @Autowired
    private ReportService reportService;
}
