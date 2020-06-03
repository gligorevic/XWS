package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTypeService {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    public FuelType add(String fuelTypeName) throws CustomException {
        if(fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName) == null)
            return fuelTypeRepository.save(new FuelType(fuelTypeName));
        else
            throw new CustomException("Fuel type already exists", HttpStatus.BAD_REQUEST);
    }

    public List<FuelType> getAllFuelTypes() {
        return fuelTypeRepository.findAll();
    }

    public FuelType getFuelTypeByName(String fuelTypeName) {
        if(fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName) == null)
            return null;

        return fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName);
    }
}
