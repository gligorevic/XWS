package com.example.CarService.controller;

import com.example.CarService.exception.CustomException;
import com.example.CarService.service.LocationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("locationToken")
public class LocationTokenController {
    @Autowired
    private LocationTokenService locationTokenService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(LocationTokenController.class);

    @PostMapping
    @PreAuthorize("hasAuthority('CAR_LOCATION_TOKEN')")
    public ResponseEntity<?> generateLocationToken(@RequestBody Long carId, Authentication authentication){
        String ownerUsername = (String) authentication.getPrincipal();
        try{
            String locationToken = locationTokenService.generateLocationToken(ownerUsername , carId);
            log.info("Successfully generated token for car {} by user {}", bCryptPasswordEncoder.encode(carId.toString()), bCryptPasswordEncoder.encode(ownerUsername));
            return new ResponseEntity<>(locationToken, HttpStatus.OK);
        }catch (CustomException e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(ownerUsername));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(ownerUsername));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{carId}")
    @PreAuthorize("hasAuthority('CAR_LOCATION_TOKEN')")
    public ResponseEntity<?> getLocationToken(@PathVariable String carId, Authentication authentication){
        String ownerUsername = (String) authentication.getPrincipal();
        try{
            String locationToken = locationTokenService.getLocationToken(ownerUsername, Long.parseLong(carId));
            log.info("Location token for car {} shown to user {}.", bCryptPasswordEncoder.encode(carId), bCryptPasswordEncoder.encode(ownerUsername));
            return new ResponseEntity<>(locationToken, HttpStatus.OK);
        }catch (CustomException e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(ownerUsername));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(ownerUsername));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
