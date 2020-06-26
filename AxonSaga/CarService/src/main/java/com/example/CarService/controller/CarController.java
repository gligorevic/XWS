package com.example.CarService.controller;

import com.baeldung.springsoap.gen.GetCarRequest;
import com.baeldung.springsoap.gen.GetCarResponse;
import com.example.CarService.client.ImageClient;
import com.example.CarService.domain.Car;
import com.example.CarService.dto.CarDTO;
import com.example.CarService.exception.CustomException;
import com.example.CarService.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
@RestController
public class CarController {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";

    @Autowired
    private CarService carService;

    @Autowired
    private ImageClient imageClient;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(CarController.class);

    @GetMapping("/{carId}")
    @PreAuthorize("hasAuthority('CAR_ADMINISTRATION')")
    public ResponseEntity<?> getCarById(@PathVariable("carId") Long carId){
        try{
            List<String> images = imageClient.getCarImagesUrl(carId);
            CarDTO carDTO = new CarDTO(carService.getCarById(carId), images);
            return new ResponseEntity<>(carDTO, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAR_ADMINISTRATION')")
    public ResponseEntity<?> addNewCar(@RequestPart("car") CarDTO carDTO, @RequestPart("file") MultipartFile[] files, @RequestHeader (name="Auth") String bearerToken, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Car car = carService.addNewCar(carDTO, files, bearerToken);
            log.info("Successfully added car {} by user {}", bCryptPasswordEncoder.encode(car.getId().toString()), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (CustomException e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/owner/{email}")
    @PreAuthorize("hasAuthority('CAR_ADMINISTRATION')")
    public ResponseEntity<?> getCarsByOwnerEmail(@PathVariable String email){
        try{
            return new ResponseEntity<>(carService.getCars(email), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCarRequest")
    @ResponsePayload
    public GetCarResponse addCarFromAgentApp(@RequestPayload GetCarRequest request) {
        try{
            GetCarResponse response = new GetCarResponse();
            response.setId(carService.addNewCarByAgent(request));

            return response;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
