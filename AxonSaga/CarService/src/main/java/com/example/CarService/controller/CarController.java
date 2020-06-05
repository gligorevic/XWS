package com.example.CarService.controller;

import com.example.CarService.domain.Car;
import com.example.CarService.dto.CarDTO;
import com.example.CarService.exception.CustomException;
import com.example.CarService.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/{carId}")
    @PreAuthorize("hasAuthority('CAR_ADMINISTRATION')")
    public ResponseEntity<?> getCarById(@PathVariable("carId") Long carId){
        try{
            return new ResponseEntity<>(new CarDTO(carService.getCarById(carId)), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAR_ADMINISTRATION')")
    public ResponseEntity<?> addNewCar(@RequestBody CarDTO carDTO, @RequestHeader (name="Auth") String bearerToken){
        try{
            return new ResponseEntity<>(carService.addNewCar(carDTO, bearerToken), HttpStatus.OK);
        } catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/owner/{email}")
    @PreAuthorize("hasAuthority('CAR_ADMINISTRATION')")
    public ResponseEntity<?> getCarsByOwnerEmail(@PathVariable String email){
        try{
            return new ResponseEntity<>(carService.getCars(email), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/locationToken")
    @PreAuthorize("hasAuthority('CAR_LOCATION_TOKEN')")
    public ResponseEntity<?> generateLocationToken(@RequestBody Long carId, Authentication authentication){
        try{
            String ownerUsername = (String) authentication.getPrincipal();
            return new ResponseEntity<>(carService.generateLocationToken(ownerUsername , carId), HttpStatus.OK);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/locationToken/{carId}")
    @PreAuthorize("hasAuthority('CAR_LOCATION_TOKEN')")
    public ResponseEntity<?> getLocationToken(@PathVariable String carId, Authentication authentication){
        try{
            String ownerUsername = (String) authentication.getPrincipal();
            return new ResponseEntity<>(carService.getLocationToken(ownerUsername, Long.parseLong(carId)), HttpStatus.OK);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
