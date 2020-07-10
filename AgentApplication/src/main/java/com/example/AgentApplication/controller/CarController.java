package com.example.AgentApplication.controller;

import com.example.AgentApplication.dto.CarDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.CarService;
import com.example.AgentApplication.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/{carId}")
    public ResponseEntity<?> getCarById(@PathVariable("carId") Long carId){
        try{
            List<String> images = imageService.getAllImagesByCarId(carId);
            return new ResponseEntity<>(new CarDTO(carService.getCarById(carId), images), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> addNewCar(@RequestPart("car") CarDTO carDTO, @RequestPart("file") MultipartFile[] files){
        try{
            System.out.println(files[0].getOriginalFilename());
            return new ResponseEntity<>(carService.addNewCar(carDTO, files), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<?> getCarsByOwnerEmail(){
        try{
            return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/locationToken")
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> generateLocationToken(@RequestBody Long carId){
        try{
            return new ResponseEntity<>(carService.generateLocationToken(carId), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/locationToken/{carId}")
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> getLocationToken(@PathVariable String carId){
        try{
            return new ResponseEntity<>(carService.getLocationToken(Long.parseLong(carId)), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
