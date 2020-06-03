package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.service.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FuelTypeController {

    @Autowired
    private FuelTypeService fuelTypeService;

    @PostMapping("fuel-type/{fuelTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addFuelType(@PathVariable("fuelTypeName") String fuelTypeName){
        try{
            FuelType fuelType = fuelTypeService.add(fuelTypeName);

            if(fuelType == null){
                return new ResponseEntity<>("Fuel type already exists", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(fuelType, HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("fuel-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getAllFuelTypes(){
        try{

            List<FuelType> fuelTypes = fuelTypeService.getAllFuelTypes();

            if(fuelTypes == null || fuelTypes.isEmpty())
                fuelTypes = new ArrayList<>();

            return new ResponseEntity<>(fuelTypes, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("fuel-type/{fuelTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getFuelTypeByName(@PathVariable("fuelTypeName") String fuelTypeName){
        try{

            FuelType fuelType = fuelTypeService.getFuelTypeByName(fuelTypeName);

            if(fuelType == null)
                return new ResponseEntity<>("No fuel type with that name.", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(fuelType, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
