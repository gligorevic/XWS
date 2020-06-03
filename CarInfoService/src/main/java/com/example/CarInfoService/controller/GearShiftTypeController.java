package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.GearShiftType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.service.GearShiftTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class GearShiftTypeController {

    @Autowired
    private GearShiftTypeService gearShiftTypeService;

    @PostMapping("gear-shift-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addGearShiftType(@RequestBody String gearShiftTypeName){
        try{
            return new ResponseEntity<>(gearShiftTypeService.add(gearShiftTypeName), HttpStatus.CREATED);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("gear-shift-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getAllGearShiftTypes(){
        try{
            return new ResponseEntity<>(gearShiftTypeService.getAllGearShiftTypes(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("gear-shift-type/{gearShiftTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getGearShiftTypeByName(@PathVariable("gearShiftTypeName") String gearShiftTypeName){
        try{
            return new ResponseEntity<>(gearShiftTypeService.getGearShiftTypeByName(gearShiftTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

}
