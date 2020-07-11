package com.example.AgentApplication.controller;

import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.GearShiftTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gear-shift-type")
public class GearShiftTypeController {

    @Autowired
    private GearShiftTypeService gearShiftTypeService;


    @GetMapping
    public ResponseEntity<?> getAllGearShiftTypes(){
        try{
            return new ResponseEntity<>(gearShiftTypeService.getAllGearShiftTypes(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{gearShiftTypeName}")
    public ResponseEntity<?> getGearShiftTypeByName(@PathVariable("gearShiftTypeName") String gearShiftTypeName){
        try{
            return new ResponseEntity<>(gearShiftTypeService.getGearShiftTypeByName(gearShiftTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
