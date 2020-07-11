package com.example.CarService.service;

import com.baeldung.springsoap.gen.GetCarRequest;
import com.example.CarService.domain.Car;
import com.example.CarService.dto.CarDTO;
import com.example.CarService.dto.SimpleCarDTO;
import com.example.CarService.exception.CustomException;
import com.example.CarService.repository.CarRepository;
import com.example.CarService.security.JWTTokenHelper;
import com.example.CoreAPI.commands.CreateCarCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CarService {
    public static final Integer MAX_ENDUSER_CARS = 3;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Inject
    private transient CommandGateway commandGateway;

    public Car addNewCar(CarDTO carDTO, MultipartFile[] files, String bearerToken) throws CustomException, IOException {
        String jwt =jwtTokenHelper.getJWTFromBearerToken(bearerToken);
        List<String> roles = jwtTokenHelper.getRoleFromAccesToken(jwt);
        String userEmail = jwtTokenHelper.getUserEmailFromAccesToken(jwt);

        if(roles.stream().anyMatch(role -> role.contains("ROLE_AGENT")) && carRepository.findCarsByUserEmail(carDTO.getUserEmail()).size() == MAX_ENDUSER_CARS){
            throw new CustomException("User already has 3 cars.", HttpStatus.NOT_ACCEPTABLE);
        }
        carDTO.setUserEmail(userEmail);
        Car newCar = carRepository.save(new Car(carDTO, files[0].getOriginalFilename()));

        Map<String, byte[]> imagesMap = generateImagesMap(files);
        commandGateway.send(new CreateCarCommand(newCar.getId(), imagesMap));

        return newCar;
    }

    private Map<String, byte[]> generateImagesMap(MultipartFile[] files) throws IOException {
        Map<String, byte[]> imagesMap = new HashMap<>();
        for(int i = 0; i < files.length; i++) {
            imagesMap.put(files[i].getOriginalFilename(), files[i].getBytes());
        }
        return imagesMap;
    }

    public List<SimpleCarDTO> getCars(String email){
        List<Car> cars = carRepository.findCarsByUserEmail(email);
        if(cars == null)
            return null;
        return cars.stream().map(car -> new SimpleCarDTO(car)).collect(Collectors.toList());
    }

    public Car getCarById(Long carId) {
        return carRepository.getOne(carId);
    }

    public void deleteCarById(Long carId) {
        carRepository.deleteById(carId);
    }

    public Long addNewCarByAgent(GetCarRequest request){
        Car car = new Car(request.getCar());
        Map<String, byte[]> imagesMap = new HashMap<>();
        request.getCar().getMapImages().stream().forEach(map -> {
            imagesMap.put(map.getKey(), map.getValue());
        });
        Car newCar = carRepository.save(car);
        commandGateway.send(new CreateCarCommand(newCar.getId(), imagesMap));
        if(newCar == null){
            return null;
        }else{
            return newCar.getId();
        }
    }
}
