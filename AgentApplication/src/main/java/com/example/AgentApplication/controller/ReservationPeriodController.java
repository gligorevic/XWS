package com.example.AgentApplication.controller;

import com.example.AgentApplication.domain.ReservationPeriod;
import com.example.AgentApplication.dto.ReservationPeriodDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.ReservationPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reservationPeriod")
public class ReservationPeriodController {

    @Autowired
    private ReservationPeriodService reservationPeriodService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> addNewReservationPeriod(@RequestBody ReservationPeriodDTO reservationPeriodDTO){
        try{
            ReservationPeriod reservationPeriod = reservationPeriodService.addNewReservationPeriod(reservationPeriodDTO);
            return new ResponseEntity<>(reservationPeriod, HttpStatus.OK);
        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationPeriodsByAdvertisement(@PathVariable Long id){
        try{
            return new ResponseEntity<>(reservationPeriodService.getReservationPeriodsByAdvertisementId(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
