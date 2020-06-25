package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.GearShiftType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.service.GearShiftTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class GearShiftTypeController {

    @Autowired
    private GearShiftTypeService gearShiftTypeService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(GearShiftTypeController.class);

    @PostMapping("gear-shift-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addGearShiftType(@RequestBody String gearShiftTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            GearShiftType gearShiftType = gearShiftTypeService.add(gearShiftTypeName);
            log.info("Gearshift type successfully added by user {}", bCryptPasswordEncoder.encode(userEmail));

            return new ResponseEntity<>(gearShiftType, HttpStatus.CREATED);

        } catch(CustomException e) {
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            log.error("{}. Acction initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("gear-shift-type/{gearTypeId}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> editGearShiftType(@PathVariable("gearTypeId") Long gearTypeId, @RequestBody String gearTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{

            GearShiftType oldGearShiftType = gearShiftTypeService.getGearShiftTypeById(gearTypeId);
            GearShiftType newGearShiftType = gearShiftTypeService.editGearShiftType(gearTypeId, gearTypeName);
            log.info("User {} edited gearshift type name from {} to {}.", oldGearShiftType.getGearShiftName(), newGearShiftType.getGearShiftName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(newGearShiftType, HttpStatus.OK);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("gear-shift-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getAllGearShiftTypes(){
        try{
            return new ResponseEntity<>(gearShiftTypeService.getAllGearShiftTypes(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
