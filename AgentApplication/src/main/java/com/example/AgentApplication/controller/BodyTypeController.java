package com.example.AgentApplication.controller;

import com.example.AgentApplication.domain.BodyType;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("body-type")
public class BodyTypeController {

    @Autowired
    private BodyTypeService bodyTypeService;


    @GetMapping
    //@PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
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

    @GetMapping("/{bodyTypeName}")
    //@PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getBodyTypeByName(@PathVariable("bodyTypeName") String bodyTypeName){
        try{

            return new ResponseEntity<>(bodyTypeService.getBodyTypeByName(bodyTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

}
