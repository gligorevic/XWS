package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.Brand;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.service.BrandService;
import com.example.CarInfoService.service.ModelService;
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
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(BrandController.class);

    @PostMapping("brand")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBrand(@RequestBody String brandName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Brand brand = brandService.add(brandName);
            log.info("Brand {} successfully added by user {}", brand.getBrandName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(brand, HttpStatus.CREATED);
        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("brand/{brandId}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> editBrand(@PathVariable("brandId") Long brandId, @RequestBody String brandName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Brand oldBrand = brandService.getBrandById(brandId);
            Brand changedBrand = brandService.editBrand(brandId, brandName);
            log.info("User {} edited brand name from {} to {}.", oldBrand.getBrandName(), changedBrand.getBrandName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(changedBrand, HttpStatus.OK);
        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("brand")
    public ResponseEntity<?> getAllBrands(){
        try{

            List<Brand> brands = brandService.getAllBrands();

            if(brands == null || brands.isEmpty())
                brands = new ArrayList<>();

            return new ResponseEntity<>(brands, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("brand/{brandName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getBrandByName(@PathVariable("brandName") String brandName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            return new ResponseEntity<>(brandService.getBrandByName(brandName), HttpStatus.OK);
        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("brand/{brandName}/model")
    public ResponseEntity<?> getModelsFromBrand(@PathVariable("brandName") String brandName){
        try{
            return new ResponseEntity<>(modelService.getModelsByBrandName(brandName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
