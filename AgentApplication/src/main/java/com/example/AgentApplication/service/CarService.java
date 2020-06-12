package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.*;
import com.example.AgentApplication.dto.CarDTO;
import com.example.AgentApplication.dto.SimpleCarDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.*;
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

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private GearShiftTypeRepository gearShiftTypeRepository;

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Autowired
    private BodyTypeRepository bodyTypeRepository;

    public Car addNewCar(CarDTO carDTO, String bearerToken) throws CustomException {
        String jwt =jwtTokenHelper.getJWTFromBearerToken(bearerToken);
        List<String> roles = jwtTokenHelper.getRoleFromAccesToken(jwt);
        String userEmail = jwtTokenHelper.getUserEmailFromAccesToken(jwt);

        carDTO.setUserEmail(userEmail);
        Car car = new Car(carDTO);
        Brand brand = brandRepository.findBrandByBrandName(carDTO.getBrandName());
        if(brand == null)
            throw new CustomException("Brand doesn't exist.", HttpStatus.BAD_REQUEST);

        Model model = modelRepository.findModelByModelName(carDTO.getModelName());
        if(model == null || !modelRepository.findModelsByBrand(brand.getId()).contains(model) )
            throw new CustomException("Error with car model entry." , HttpStatus.BAD_REQUEST);

        GearShiftType gearShiftType = gearShiftTypeRepository.findGearShiftTypeByGearShiftName(carDTO.getGearShiftName());
        if(gearShiftType == null || !model.getGearShiftTypes().contains(gearShiftType))
            throw new CustomException("Error with gear shift type entry.", HttpStatus.BAD_REQUEST);

        FuelType fuelType = fuelTypeRepository.findFuelTypeByFuelTypeName(carDTO.getFuelTypeName());
        if(fuelType  == null || !model.getFuelTypes().contains(fuelType))
            throw new CustomException("Error with fuel type entry.", HttpStatus.BAD_REQUEST);

        BodyType bodyType = bodyTypeRepository.findBodyTypeByBodyTypeName(carDTO.getBodyName());
        if(bodyType == null || !model.getBodyTypes().contains(bodyType))
            throw new CustomException("Error with body type entry.", HttpStatus.BAD_REQUEST);

        car.setBrand(brand);
        car.setModel(model);
        car.setGearShift(gearShiftType);
        car.setFuelType(fuelType);
        car.setBodyType(bodyType);

        return carRepository.save(car);
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
