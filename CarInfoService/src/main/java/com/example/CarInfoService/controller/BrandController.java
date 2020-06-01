package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.Brand;
import com.example.CarInfoService.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("brand/{brandName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBrand(@PathVariable("brandName") String brandName){
        try{
            Brand brand = brandService.add(brandName);

            if(brand == null){
                return new ResponseEntity<>("Brand already exists", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(brand, HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
