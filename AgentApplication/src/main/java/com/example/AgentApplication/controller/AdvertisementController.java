package com.example.AgentApplication.controller;

import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.dto.AdvertisementDTO;
import com.example.AgentApplication.dto.AdvertisementPostDTO;
import com.example.AgentApplication.dto.SearchDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("search")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<?> getAllAdvertisements(){
        try{
            return new ResponseEntity<>(advertisementService.getSimpleAdvertisements(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/ad")
    public ResponseEntity<?> getAdvertisement(@RequestBody Long[] addvertismentIds) {
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementsCart(addvertismentIds), HttpStatus.OK);
        }  catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<?> addNewAdvertisement(@RequestBody AdvertisementPostDTO advertisementPostDTO){
        try{
            return new ResponseEntity<>(advertisementService.addAdvertisement(advertisementPostDTO), HttpStatus.OK);
        } catch(CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> getAdvertismentsBySearchParams(@RequestBody SearchDTO searchDTO){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertismentsBySearchParams(searchDTO), HttpStatus.OK);
        }catch(CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
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
