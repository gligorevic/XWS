package com.example.SearchService.controller;

import com.example.SearchService.service.ReservationPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reservationPeriod")
public class ReservationPeriodController {

    @Autowired
    private ReservationPeriodService reservationPeriodService;
}
