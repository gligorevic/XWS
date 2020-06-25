package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.service.FuelTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FuelTypeController {

    @Autowired
    private FuelTypeService fuelTypeService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(FuelTypeController.class);

    @PostMapping("fuel-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addFuelType(@RequestBody String fuelTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            FuelType fuelType = fuelTypeService.add(fuelTypeName);
            log.info("Fuel type successfully added by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(fuelType, HttpStatus.CREATED);

        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("fuel-type/{fuelTypeId}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> editFuelType(@PathVariable("fuelTypeId") Long fuelTypeId, @RequestBody String fuelTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            FuelType oldFuelType = fuelTypeService.getFuelTypeById(fuelTypeId);
            FuelType newFuelType = fuelTypeService.editFuelType(fuelTypeId, fuelTypeName);
            log.info("User {} edited fuel type name from {} to {}.", oldFuelType.getFuelTypeName(), newFuelType.getFuelTypeName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(fuelTypeService.editFuelType(fuelTypeId, fuelTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("fuel-type/{fuelTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
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
