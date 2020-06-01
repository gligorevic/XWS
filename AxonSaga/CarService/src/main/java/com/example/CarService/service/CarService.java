package com.example.CarService.service;

import com.example.CarService.domain.Car;
import com.example.CarService.dto.CarDTO;
import com.example.CarService.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car addNewCar(CarDTO carDTO){
        if(!carDTO.isAgent() && carRepository.findCarsByUserAgentId(carDTO.getUserAgentId()).size() >= 3){
            return null;
        }
        Car car = new Car(carDTO.getBrandName(), carDTO.getModelName(), carDTO.getGearShiftName(), carDTO.getFuelTypeName(), carDTO.getBodyName(), carDTO.getKmPassed(), carDTO.getUserAgentId());
        return carRepository.save(car);
    }
}
