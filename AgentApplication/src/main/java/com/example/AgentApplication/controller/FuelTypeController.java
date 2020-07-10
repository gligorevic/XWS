package com.example.AgentApplication.controller;

import com.example.AgentApplication.domain.FuelType;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fuel-type")
public class FuelTypeController {

    @Autowired
    private FuelTypeService fuelTypeService;


    @GetMapping
    public ResponseEntity<?> getAllFuelTypes(){
        try{

            List<FuelType> fuelTypes = fuelTypeService.getAllFuelTypes();

            if(fuelTypes == null || fuelTypes.isEmpty())
                fuelTypes = new ArrayList<>();

            return new ResponseEntity<>(fuelTypes, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{fuelTypeName}")
    public ResponseEntity<?> getFuelTypeByName(@PathVariable("fuelTypeName") String fuelTypeName){
        try{

            return new ResponseEntity<>(fuelTypeService.getFuelTypeByName(fuelTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
