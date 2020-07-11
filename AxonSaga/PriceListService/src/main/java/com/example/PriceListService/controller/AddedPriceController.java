package com.example.PriceListService.controller;

import com.example.PriceListService.service.AddedPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/added-price")
public class AddedPriceController {

    @Autowired
    private AddedPriceService addedPriceService;

    @GetMapping
    public ResponseEntity<?> getAddedPrices(Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            return new ResponseEntity<>(addedPriceService.getAddedPrices(userEmail), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> payAddedPrice(@PathVariable Long id){
        try{
            return new ResponseEntity<>(addedPriceService.payAddedPrice(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkIfNotPaid(Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            return new ResponseEntity<>(addedPriceService.checkIfNotPaid(userEmail), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
