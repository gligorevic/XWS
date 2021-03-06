package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.repository.FuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public FuelType getFuelTypeByName(String fuelTypeName) throws CustomException {
        if(fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName) == null)
            throw new CustomException("Fuel type doesn't exists", HttpStatus.BAD_REQUEST);

        return fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName);
    }

    public FuelType editFuelType(Long fuelTypeId, String fuelTypeName) throws CustomException{

        FuelType fuelType = fuelTypeRepository.findFuelTypeById(fuelTypeId);
        if(fuelType == null){
            throw new CustomException("Fuel type doesn't exists", HttpStatus.BAD_REQUEST);
        }
        fuelType.setFuelTypeName(fuelTypeName);

        return fuelTypeRepository.save(fuelType);
    }

    public FuelType getFuelTypeById(Long fuelTypeId) throws CustomException{

        Optional<FuelType> fuelType = fuelTypeRepository.findById(fuelTypeId);
        if(fuelType == null)
            throw new CustomException("Fuel type with that id doesn't exists", HttpStatus.BAD_REQUEST);

        return fuelType.get();
    }
}
