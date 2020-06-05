package com.example.SearchService.controller;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<?> getAllAdvertisements(){
        try{
            System.out.println("Usao u kontroler");
            return new ResponseEntity<>(advertisementService.getAllAdvertisements(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<Advertisement> addNewAdvertisement(@RequestBody AdvertisementDTO advertisementDTO){
        try{
            return new ResponseEntity<>(advertisementService.addAdvertisement(advertisementDTO), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<List<Advertisement>> getAdvertisementsByUserId(@PathVariable String email){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementsByUserId(email), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> getAdvertismentsBySearchParams(@RequestBody AdvertisementDTO advertisementDTO){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertismentsBySearchParams(advertisementDTO), HttpStatus.OK);
        }catch(CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
