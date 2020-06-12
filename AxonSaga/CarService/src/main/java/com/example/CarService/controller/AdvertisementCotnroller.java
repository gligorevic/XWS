package com.example.CarService.controller;

import com.example.CarService.dto.AdvertisementDTO;
import com.example.CarService.exception.CustomException;
import com.example.CarService.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("advert")
public class AdvertisementCotnroller {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping("/activate")
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<?> activateAdvertisement(@RequestBody AdvertisementDTO advertisementDTO, @RequestHeader(name="Auth") String bearerToken) {
        try{
            return advertisementService.activateAdvertisement(advertisementDTO, bearerToken);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
