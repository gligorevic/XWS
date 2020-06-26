package com.example.SearchService.controller;

import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/reqAdList")
    public ResponseEntity<?> getAdvertisementsByIds(@RequestBody List<Long> adIds) {
        try {
            return new ResponseEntity<>(requestService.getAdvertisementsByIds(adIds), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
