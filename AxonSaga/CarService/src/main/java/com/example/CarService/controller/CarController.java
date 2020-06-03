package com.example.CarService.controller;

import com.example.CarService.domain.Car;
import com.example.CarService.dto.CarDTO;
import com.example.CarService.exception.CustomException;
import com.example.CarService.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    @PostMapping
    private ResponseEntity<?> addNewCar(@RequestBody CarDTO carDTO, @RequestHeader (name="Auth") String bearerToken){
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

    @Autowired
    private CarService carService;

    @GetMapping("/{email}")
    private ResponseEntity<List<Car>> getCars(@PathVariable String email){
        try{
            return new ResponseEntity<>(carService.getCars(email), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
