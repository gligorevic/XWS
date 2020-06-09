package com.example.SearchService.controller;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.service.AdvertisementService;
import com.example.SearchService.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getAllAdvertisements(){
        try{
            return new ResponseEntity<>(advertisementService.getAllAdvertisements(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<?> addNewAdvertisement(@RequestPart("advertisement") AdvertisementDTO advertisementDTO, @RequestPart("file") MultipartFile[] files){
        try{
            imageService.saveImages(files, advertisementDTO.getCarId());
            return new ResponseEntity<>(advertisementService.addAdvertisement(advertisementDTO), HttpStatus.OK);
        } catch(CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<List<Advertisement>> getAdvertisementsByUserId(@PathVariable String email){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementsByUserId(email), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementById(id), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
