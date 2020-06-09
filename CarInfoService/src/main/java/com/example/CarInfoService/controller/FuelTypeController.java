package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.service.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FuelTypeController {

    @Autowired
    private FuelTypeService fuelTypeService;

    @PostMapping("fuel-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addFuelType(@RequestBody String fuelTypeName){
        try{
            return new ResponseEntity<>(fuelTypeService.add(fuelTypeName), HttpStatus.CREATED);

        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("fuel-type/{fuelTypeId}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> editFuelType(@PathVariable("fuelTypeId") Long fuelTypeId, @RequestBody String fuelTypeName){

        try{
            return new ResponseEntity<>(fuelTypeService.editFuelType(fuelTypeId, fuelTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
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

            return new ResponseEntity<>(fuelTypeService.getFuelTypeByName(fuelTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
