package com.example.CarService.controller;

import com.example.CarService.exception.CustomException;
import com.example.CarService.service.LocationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("locationToken")
public class LocationTokenController {
    @Autowired
    private LocationTokenService locationTokenService;

    @PostMapping
    @PreAuthorize("hasAuthority('CAR_LOCATION_TOKEN')")
    public ResponseEntity<?> generateLocationToken(@RequestBody Long carId, Authentication authentication){
        try{
            String ownerUsername = (String) authentication.getPrincipal();
            return new ResponseEntity<>(locationTokenService.generateLocationToken(ownerUsername , carId), HttpStatus.OK);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{carId}")
    @PreAuthorize("hasAuthority('CAR_LOCATION_TOKEN')")
    public ResponseEntity<?> getLocationToken(@PathVariable String carId, Authentication authentication){
        try{
            String ownerUsername = (String) authentication.getPrincipal();
            return new ResponseEntity<>(locationTokenService.getLocationToken(ownerUsername, Long.parseLong(carId)), HttpStatus.OK);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
