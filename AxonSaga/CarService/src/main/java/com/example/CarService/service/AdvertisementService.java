package com.example.CarService.service;

import com.example.CarService.client.AdvertisementClient;
import com.example.CarService.domain.Car;
import com.example.CarService.dto.AdvertisementDTO;
import com.example.CarService.exception.CustomException;
import com.example.CarService.repository.CarRepository;
import com.example.CarService.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private AdvertisementClient advertisementClient;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    public ResponseEntity<?> activateAdvertisement(AdvertisementDTO advertisementDTO, String bearerToken) throws Exception {
        String jwt = jwtTokenHelper.getJWTFromBearerToken(bearerToken);
        String email = jwtTokenHelper.getUserEmailFromAccesToken(jwt);
        Car car = carRepository.getOne(advertisementDTO.getCarId());

        if(!email.equals(car.getUserEmail())) {
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        if(car == null) {
            throw new Exception("Car not found");
        }
        advertisementDTO.addCar(car);

        return advertisementClient.addAdvertisement(bearerToken, advertisementDTO);
    }
}
