package com.example.CarService.service;

import com.example.CarService.domain.Car;
import com.example.CarService.dto.CarDTO;
import com.example.CarService.exception.CustomException;
import com.example.CarService.repository.CarRepository;
import com.example.CarService.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.CarService.security.SecurityConstants.HEADER_BEARER_TOKEN;
import static com.example.CarService.security.SecurityConstants.TOKEN_BEARER_PREFIX;

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

    public List<Car> getCars(String email){
        return carRepository.findCarsByUserEmail(email);
    }


}
