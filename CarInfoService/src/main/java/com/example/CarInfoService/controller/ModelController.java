package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.Model;
import com.example.CarInfoService.dto.ModelDTO;
import com.example.CarInfoService.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class ModelController {

    @Autowired
    private ModelService modelService;


    @PostMapping("model")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addNewModel(@RequestBody ModelDTO modelDTO){

        try{
            Model model = modelService.addNewModel(modelDTO);
            if(model == null)
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(model, HttpStatus.CREATED);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
