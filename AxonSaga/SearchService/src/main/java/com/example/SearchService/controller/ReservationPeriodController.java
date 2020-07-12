package com.example.SearchService.controller;

import com.baeldung.springsoap.gen.GetAdvertisementRequest;
import com.baeldung.springsoap.gen.GetAdvertisementResponse;
import com.baeldung.springsoap.gen.GetReservationPeriodRequest;
import com.baeldung.springsoap.gen.GetReservationPeriodResponse;
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
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
@RestController
@RequestMapping(value = "/reservationPeriod")
public class ReservationPeriodController {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";

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
            System.out.println("\n\n\n\nLoger zeza\n\n\n\n");
            log.info("Reservation period added for advertisement {} by user {}",  bCryptPasswordEncoder.encode(reservationPeriod.getAdvertisement().getId().toString()), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(reservationPeriod, HttpStatus.OK);
        } catch(CustomException e) {
            e.printStackTrace();
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch(Exception e){
            e.printStackTrace();
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getReservationPeriodsByAdvertisement(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(reservationPeriodService.getReservationPeriodsByAdvertisementId(id), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationPeriodRequest")
    @ResponsePayload
    public GetReservationPeriodResponse addResPeriodAgent(@RequestPayload GetReservationPeriodRequest request) {
        try{
            GetReservationPeriodResponse response = new GetReservationPeriodResponse();
            response.setId(reservationPeriodService.saveReservationPeriodAgent(request.getReservationPeriod()));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
