package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Car;
import com.example.AgentApplication.dto.CarDTO;
import com.example.AgentApplication.dto.SimpleCarDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.CarRepository;
import com.example.AgentApplication.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    public Car addNewCar(CarDTO carDTO, String bearerToken) throws CustomException {
        String jwt =jwtTokenHelper.getJWTFromBearerToken(bearerToken);
        List<String> roles = jwtTokenHelper.getRoleFromAccesToken(jwt);
        String userEmail = jwtTokenHelper.getUserEmailFromAccesToken(jwt);

        if(roles.indexOf("ROLE_AGENT") != -1 && carRepository.findCarsByUserEmail(carDTO.getUserEmail()).size() >= 3){
            throw new CustomException("User already has 3 cars.", HttpStatus.NOT_ACCEPTABLE);
        }
        carDTO.setUserEmail(userEmail);

        return carRepository.save(new Car(carDTO));
    }

    public List<SimpleCarDTO> getCars(String email){
        List<Car> cars = carRepository.findCarsByUserEmail(email);
        if(cars == null)
            return null;
        return cars.stream().map(car -> new SimpleCarDTO(car)).collect(Collectors.toList());
    }


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

    public Car getCarById(Long carId) {
        return carRepository.getOne(carId);
    }
}
