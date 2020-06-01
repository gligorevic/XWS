package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.*;
import com.example.CarInfoService.dto.ModelDTO;
import com.example.CarInfoService.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Model addNewModel(ModelDTO modelDTO) {

        Model model = new Model(modelDTO.getModelName());

        Brand brand = brandRepository.findBrandByBrandName(modelDTO.getBrandName());
        if(brand == null)
            return null;
        model.setBrand(brand);


        for(String bodyTypeName: modelDTO.getBodyTypeNames()){
            BodyType bodyType = bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName);
            if(bodyType == null)
                return null;
            model.getBodyTypes().add(bodyType);
        }

        for(String fuelTypeName: modelDTO.getFuelTypeNames()){
            FuelType fuelType = fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName);
            if(fuelType == null)
                return null;
            model.getFuelTypes().add(fuelType);
        }

        for(String gearShiftName: modelDTO.getBodyTypeNames()) {
            GearShiftType gearShiftType = gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftName);
            if(gearShiftType == null)
                return null;
            model.getGearShiftTypes().add(gearShiftType);
        }

        modelRepository.save(model);

        return model;
    }
}
