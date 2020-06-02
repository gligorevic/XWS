package com.example.SearchService.controller;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping
    private ResponseEntity<Advertisement> addNewAdvertisement(@RequestBody AdvertisementDTO advertisementDTO){
        try{
            return new ResponseEntity<>(advertisementService.addAdvertisement(advertisementDTO), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email}")
    private ResponseEntity<List<Advertisement>> getAdvertisementsByUserId(@PathVariable String email){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementsByUserId(email), HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
