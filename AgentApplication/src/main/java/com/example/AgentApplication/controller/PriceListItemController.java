package com.example.AgentApplication.controller;

import com.example.AgentApplication.dto.PriceListItemDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.PriceListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/price-list/items")
public class PriceListItemController {

    @Autowired
    private PriceListItemService priceListItemService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> addNewPricelistItem(@RequestBody PriceListItemDTO priceListItemDTO){
        try{
            return new ResponseEntity<>(priceListItemService.addNewPriceListItem(priceListItemDTO) , HttpStatus.CREATED);
        }catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPricelistsByUserEmail(@PathVariable Long id){
        try{
            return new ResponseEntity<>(priceListItemService.getPriceListItems(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
