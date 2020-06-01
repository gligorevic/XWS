package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.service.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BodyTypeController {

    @Autowired
    private BodyTypeService bodyTypeService;

    @PostMapping("body-type/{bodyTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBodyType(@PathVariable String bodyTypeName){
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
}
