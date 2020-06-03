package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.domain.Brand;
import com.example.CarInfoService.domain.Model;
import com.example.CarInfoService.service.BrandService;
import com.example.CarInfoService.service.ModelService;
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
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

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

    @GetMapping("brand")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getAllBrands(){
        try{

            List<Brand> brands = brandService.getAllBrands();

            if(brands == null || brands.isEmpty())
                brands = new ArrayList<>();

            return new ResponseEntity<>(brands, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("brand/{brandName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getBrandByName(@PathVariable("brandName") String brandName){
        try{

            Brand brand = brandService.getBrandByName(brandName);

            if(brand == null)
                return new ResponseEntity<>("No brand with that name.", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(brand, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("brand/{brandName}/model")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getModelsFromBrand(@PathVariable("brandName") String brandName){
        try{

            Brand brand = brandService.getBrandByName(brandName);

            if(brand == null)
                return new ResponseEntity<>("No brand with that name.", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(modelService.getModelsByBrandName(brandName), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
