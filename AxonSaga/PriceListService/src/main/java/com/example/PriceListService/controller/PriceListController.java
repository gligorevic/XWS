package com.example.PriceListService.controller;

import com.example.PriceListService.dto.DataForPriceCalculationDTO;
import com.example.PriceListService.dto.PriceListDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class PriceListController {

    @Autowired
    private PriceListService priceListService;

    @PostMapping
    public ResponseEntity<?> addNewPricelist(@RequestBody PriceListDTO priceListDTO, Authentication authentication) throws CustomException {
        try{
            String userEmail = (String) authentication.getPrincipal();
            return new ResponseEntity<>(priceListService.addNewPriceList(priceListDTO, userEmail), HttpStatus.CREATED);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/price")
    public ResponseEntity<?> getPriceForAdInPeriod(@RequestBody DataForPriceCalculationDTO dataForPriceCalculationDTO) {
        try{
            return new ResponseEntity<>(priceListService.calculatePriceForRequest(dataForPriceCalculationDTO), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPricelistsByUserEmail(Authentication authentication){
        try{
            String userEmail = (String) authentication.getPrincipal();
            return new ResponseEntity<>(priceListService.getPricelistsByUserEmail(userEmail), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
