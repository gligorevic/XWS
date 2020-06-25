package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.service.BodyTypeService;
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
public class BodyTypeController {

    @Autowired
    private BodyTypeService bodyTypeService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(BodyTypeController.class);

    @PostMapping("body-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBodyType(@RequestBody String bodyTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            BodyType bodyType = bodyTypeService.addBodyType(bodyTypeName);
            log.info("Body type successfully added by user {}", bCryptPasswordEncoder.encode(userEmail));

            return new ResponseEntity<>(bodyType, HttpStatus.CREATED);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("body-type/{bodyTypeId}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> editGearShiftType(@PathVariable("bodyTypeId") Long bodyTypeId, @RequestBody String bodyTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{

            BodyType oldBodyType = bodyTypeService.getBodyTypeById(bodyTypeId);
            BodyType newBodyType = bodyTypeService.editBodyType(bodyTypeId, bodyTypeName);
            log.info("User {} edited body type name from {} to {}.", oldBodyType.getBodyTypeName(), newBodyType.getBodyTypeName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(newBodyType, HttpStatus.OK);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("body-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getAllBodyTypes(){
        try{

            List<BodyType> bodyTypes = bodyTypeService.getAllBodyTypes();

            if(bodyTypes == null || bodyTypes.isEmpty())
                bodyTypes = new ArrayList<>();

            return new ResponseEntity<>(bodyTypes, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("body-type/{bodyTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getBodyTypeByName(@PathVariable("bodyTypeName") String bodyTypeName){
        try{

            return new ResponseEntity<>(bodyTypeService.getBodyTypeByName(bodyTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
