package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.service.BodyTypeService;
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
public class BodyTypeController {

    @Autowired
    private BodyTypeService bodyTypeService;

    @PostMapping("body-type/{bodyTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBodyType(@PathVariable("bodyTypeName") String bodyTypeName){
        try{
            BodyType bodyType = bodyTypeService.addBodyType(bodyTypeName);

            if(bodyType == null){
                return new ResponseEntity<>("Body type already exists", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(bodyType, HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("body-type/{bodyTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getBodyTypeByName(@PathVariable("bodyTypeName") String bodyTypeName){
        try{

            BodyType bodyType = bodyTypeService.getBodyTypeByName(bodyTypeName);

            if(bodyType == null)
                return new ResponseEntity<>("No body type with that name.", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(bodyType, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

}
