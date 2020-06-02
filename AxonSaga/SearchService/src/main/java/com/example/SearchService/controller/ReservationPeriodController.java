package com.example.SearchService.controller;

import com.example.SearchService.domain.ReservationPeriod;
import com.example.SearchService.dto.ReservationPeriodDTO;
import com.example.SearchService.service.ReservationPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reservationPeriod")
public class ReservationPeriodController {

    @Autowired
    private ReservationPeriodService reservationPeriodService;

    @PostMapping
    private ResponseEntity<ReservationPeriod> addNewReservationPeriod(@RequestBody ReservationPeriodDTO reservationPeriodDTO){
        try{
            ReservationPeriod reservationPeriod = reservationPeriodService.addNewReservationPeriod(reservationPeriodDTO);
            if(reservationPeriod == null){
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(reservationPeriod, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
