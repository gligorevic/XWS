package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.domain.GearShiftType;
import com.example.CarInfoService.service.GearShiftTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GearShiftTypeController {

    @Autowired
    private GearShiftTypeService gearShiftTypeService;

    @PostMapping("gear-shift-type/{gearShiftTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBrand(@PathVariable String gearShiftTypeName){
        try{
            GearShiftType gearShiftType = gearShiftTypeService.add(gearShiftTypeName);

            if(gearShiftType == null){
                return new ResponseEntity<>("Gear shift type already exists", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(gearShiftType, HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
