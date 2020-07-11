package com.example.PriceListService.controller;

import com.example.PriceListService.dto.PriceListDTO;
import com.example.PriceListService.dto.PriceListItemDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.service.PriceListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/items")
public class PriceListItemController {

    @Autowired
    private PriceListItemService priceListItemService;

    @PostMapping
    public ResponseEntity<?> addNewPricelistItem(@RequestBody PriceListItemDTO priceListItemDTO, @RequestHeader("Auth") String auth){
        try{
            return new ResponseEntity<>(priceListItemService.addNewPriceListItem(priceListItemDTO, auth) , HttpStatus.CREATED);
        }catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPricelistsByUserEmail(@PathVariable Long id){
        try{
            return new ResponseEntity<>(priceListItemService.getPriceListItems(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
