package com.example.SearchService.controller;

import com.example.SearchService.domain.ReservationPeriod;
import com.example.SearchService.dto.ReservationPeriodDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.service.ReservationPeriodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reservationPeriod")
public class ReservationPeriodController {

    @Autowired
    private ReservationPeriodService reservationPeriodService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(ReservationPeriodController.class);


    @PostMapping
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    private ResponseEntity<?> addNewReservationPeriod(@RequestBody ReservationPeriodDTO reservationPeriodDTO, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            ReservationPeriod reservationPeriod = reservationPeriodService.addNewReservationPeriod(reservationPeriodDTO);
            log.info("Reservation period added for advertisement {} by user {}",  bCryptPasswordEncoder.encode(reservationPeriod.getAdvertisement().getId().toString()), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(reservationPeriod, HttpStatus.OK);
        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getReservationPeriodsByAdvertisement(@PathVariable Long id){
        try{
            return new ResponseEntity<>(reservationPeriodService.getReservationPeriodsByAdvertisementId(id), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
