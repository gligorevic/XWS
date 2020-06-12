package com.example.SearchService.controller;

import com.example.SearchService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping(path = "/ad")
    public ResponseEntity<?> getAdvertisementsByIds(@RequestBody Long[] addvertismentIds) {
        try{
            return new ResponseEntity<>(cartService.getAdvertisementsCart(addvertismentIds), HttpStatus.OK);
        }  catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
