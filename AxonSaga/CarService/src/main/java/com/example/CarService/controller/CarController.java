package com.example.CarService.controller;

import com.example.CarService.domain.Car;
import com.example.CarService.dto.CarDTO;
import com.example.CarService.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    private ResponseEntity<Car> addNewCar(@RequestBody CarDTO carDTO){
        try{
            return new ResponseEntity<>(carService.addNewCar(carDTO), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
