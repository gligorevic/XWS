package com.example.CarService.controller;

import com.example.CarService.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    private CarService carService;
}
