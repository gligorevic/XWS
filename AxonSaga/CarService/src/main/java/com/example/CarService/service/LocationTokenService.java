package com.example.CarService.service;

import com.example.CarService.domain.Car;
import com.example.CarService.exception.CustomException;
import com.example.CarService.repository.CarRepository;
import com.example.CarService.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LocationTokenService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    public String generateLocationToken(String ownerUsername, Long carId) throws CustomException {
        Car car = carRepository.getOne(carId);

        if(car == null)
            throw new CustomException("Bad request", HttpStatus.BAD_REQUEST);
        else if(!car.getUserEmail().equals(ownerUsername))
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);

        String locationToken = jwtTokenHelper.generateLocationToken(car);
        car.setLocationToken(locationToken);
        carRepository.save(car);

        return locationToken;
    }

    public String getLocationToken(String ownerUsername, long carId) throws CustomException {
        Car car = carRepository.getOne(carId);

        if(car == null)
            throw new CustomException("Bad request", HttpStatus.BAD_REQUEST);
        else if(!car.getUserEmail().equals(ownerUsername))
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);

        return car.getLocationToken();
    }
}
