package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.*;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private GearShiftTypeRepository gearShiftTypeRepository;

    @Autowired
    private BodyTypeRepository bodyTypeRepository;

    @Autowired
    private FuelTypeRepository fuelTypeRepository;


    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }


    public List<Model> getModelsByBrandName(String brandName) throws CustomException {

        Brand brand = brandRepository.findBrandByBrandName(brandName);
        if(brand == null)
            throw  new CustomException("Brand doesn't exists", HttpStatus.BAD_REQUEST);

        return modelRepository.findModelsByBrand(brand.getId());
    }

    public List<BodyType> getBodyTypesFromModel(Long id) {

        List<Long> types = modelRepository.findBodyTypesByModelId(id);
        List<BodyType> bodyTypes = new ArrayList<>();

        for(Long typeId : types){
            bodyTypes.add(bodyTypeRepository.findBodyTypeById(typeId));
        }

        return bodyTypes;
    }

    public List<GearShiftType> getGearShiftTypesFromModel(Long id) {

        List<Long> types = modelRepository.findGearShiftTypesByModelId(id);
        List<GearShiftType> gearShiftTypes = new ArrayList<>();

        for(Long typeId : types){
            gearShiftTypes.add(gearShiftTypeRepository.findGearShiftTypeById(typeId));
        }

        return gearShiftTypes;
    }

    public List<FuelType> getFuelTypesFromModel(Long id) {

        List<Long> types = modelRepository.findFuelTypesByModelId(id);
        List<FuelType> fuelTypes = new ArrayList<>();

        for(Long typeId : types){
            fuelTypes.add(fuelTypeRepository.findFuelTypeById(typeId));
        }

        return fuelTypes;
    }


    public Model getModelById(Long modelId) throws CustomException {
        if(modelRepository.findModelById(modelId) == null)
            throw  new CustomException("Model doesn't exists", HttpStatus.BAD_REQUEST);

        return modelRepository.findModelById(modelId);
    }
}
