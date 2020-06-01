package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
