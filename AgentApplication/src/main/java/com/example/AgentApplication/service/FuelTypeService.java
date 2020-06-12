package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.FuelType;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTypeService {

    @Autowired
    private FuelTypeRepository fuelTypeRepository;


    public List<FuelType> getAllFuelTypes() {
        return fuelTypeRepository.findAll();
    }

    public FuelType getFuelTypeByName(String fuelTypeName) throws CustomException {
        if(fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName) == null)
            throw new CustomException("Fuel type doesn't exists", HttpStatus.BAD_REQUEST);

        return fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName);
    }
}
