package com.example.SearchService.service;

import com.example.SearchService.repository.ReservationPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationPeriodService {

    @Autowired
    private ReservationPeriodRepository reservationPeriodRepository;
}
