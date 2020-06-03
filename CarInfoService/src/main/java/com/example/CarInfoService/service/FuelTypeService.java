package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTypeService {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    public FuelType add(String fuelTypeName) {

        if(fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName) == null){
            FuelType fuelType = new FuelType(fuelTypeName);
            fuelTypeRepository.save(fuelType);
            return fuelType;
        }

        return null;
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
