package com.example.AgentApplication.service;

import com.baeldung.soap.ws.client.generated.CarsPort;
import com.baeldung.soap.ws.client.generated.CarsPortService;
import com.baeldung.soap.ws.client.generated.GetCarRequest;
import com.baeldung.soap.ws.client.generated.GetCarResponse;
import com.example.AgentApplication.domain.*;
import com.example.AgentApplication.dto.CarDTO;
import com.example.AgentApplication.dto.SimpleCarDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.*;
import com.example.AgentApplication.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private ImageService imageService;

    public Car addNewCar(CarDTO carDTO, MultipartFile[] files) throws CustomException, IOException {
        carDTO.setUserEmail("email");
        Car car = new Car(carDTO, files[0].getOriginalFilename());
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

        Map<String, byte[]> imagesMap = generateImagesMap(files);


        //soap
        CarsPortService service = new CarsPortService();
        CarsPort carsPort = service.getCarsPortSoap11();
        GetCarRequest getCarRequest = new GetCarRequest();
        com.baeldung.soap.ws.client.generated.Car car1 = new com.baeldung.soap.ws.client.generated.Car();
        car1.setBodyName(bodyType.getBodyTypeName());
        car1.setBrandName(brand.getBrandName());
        car1.setFuelTypeName(fuelType.getFuelTypeName());
        car1.setGearShiftName(gearShiftType.getGearShiftName());
        car1.setModelName(model.getModelName());
        car1.setKmPassed(car.getKmPassed());
        car1.setUserEmail("agent@gmail.com");
        car1.setMainImageUrl(car.getMainImageUrl());
        imagesMap.keySet().stream().forEach(key -> {
            com.baeldung.soap.ws.client.generated.Map map = new com.baeldung.soap.ws.client.generated.Map();
            map.setKey(key);
            map.setValue(imagesMap.get(key));
            car1.getMapImages().add(map);
        });

        getCarRequest.setCar(car1);
        GetCarResponse getCarResponse = carsPort.getCar(getCarRequest);
        System.out.println(getCarResponse.getId());
        car.setRemoteId(getCarResponse.getId());

        Car newCar = carRepository.save(car);
        imageService.saveImages(imagesMap, newCar.getId());

        return newCar;
    }

    private Map<String, byte[]> generateImagesMap(MultipartFile[] files) throws IOException {
        Map<String, byte[]> imagesMap = new HashMap<>();
        for(int i = 0; i < files.length; i++) {
            imagesMap.put(files[i].getOriginalFilename(), files[i].getBytes());
        }
        return imagesMap;
    }

    public List<SimpleCarDTO> getCars(){
        List<Car> cars = carRepository.findAll();
        if(cars == null)
            return null;
        return cars.stream().map(car -> new SimpleCarDTO(car)).collect(Collectors.toList());
    }


    public String generateLocationToken(Long carId) throws CustomException {
        Car car = carRepository.getOne(carId);

        if(car == null)
            throw new CustomException("Bad request", HttpStatus.BAD_REQUEST);

        String locationToken = jwtTokenHelper.generateLocationToken(car);
        car.setLocationToken(locationToken);
        carRepository.save(car);

        return locationToken;
    }

    public String getLocationToken(long carId) throws CustomException {
        Car car = carRepository.getOne(carId);

        if(car == null)
            throw new CustomException("Bad request", HttpStatus.BAD_REQUEST);

        return car.getLocationToken();
    }

    public Car getCarById(Long carId) {
        return carRepository.getOne(carId);
    }
}
