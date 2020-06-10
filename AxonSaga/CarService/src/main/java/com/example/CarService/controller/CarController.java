package com.example.CarService.controller;

import com.example.CarService.client.ImageClient;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private ImageClient imageClient;

    @GetMapping("/{carId}")
    @PreAuthorize("hasAuthority('CAR_ADMINISTRATION')")
    public ResponseEntity<?> getCarById(@PathVariable("carId") Long carId){
        try{
            List<String> images = imageClient.getCarImagesUrl(carId);
            CarDTO carDTO = new CarDTO(carService.getCarById(carId), images);
            return new ResponseEntity<>(carDTO, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAR_ADMINISTRATION')")
    public ResponseEntity<?> addNewCar(@RequestPart("car") CarDTO carDTO, @RequestPart("file") MultipartFile[] files, @RequestHeader (name="Auth") String bearerToken){
        try{
            System.out.println(files[0].getOriginalFilename());
            return new ResponseEntity<>(carService.addNewCar(carDTO, files, bearerToken), HttpStatus.OK);
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
}
