package com.example.AgentApplication.controller;

import com.example.AgentApplication.dto.PriceListDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price-list")
public class PriceListController {

    @Autowired
    private PriceListService priceListService;

    @PostMapping
    public ResponseEntity<?> addNewPricelist(@RequestBody PriceListDTO priceListDTO) throws CustomException {
        try{
            return new ResponseEntity<>(priceListService.addNewPriceList(priceListDTO), HttpStatus.CREATED);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPricelists(){
        try{
            return new ResponseEntity<>(priceListService.getPricelists(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
