package com.example.SearchService.controller;

import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/search")
    public ResponseEntity<?> getAdvertismentsBySearchParams(@RequestBody AdvertisementDTO advertisementDTO){
        try{
            return new ResponseEntity<>(searchService.getAdvertismentsBySearchParams(advertisementDTO), HttpStatus.OK);
        }catch(CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
