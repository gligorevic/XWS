package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.*;
import com.example.CarInfoService.dto.ModelDTO;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.repository.*;
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


    public Model addNewModel(ModelDTO modelDTO) throws CustomException {

        Model model = new Model(modelDTO.getModelName());

        Brand brand = brandRepository.findBrandByBrandName(modelDTO.getBrandName());
        if(brand == null)
            return null;
        model.setBrand(brand);


        if(modelDTO.getBodyTypeNames() != null) {
            for (String bodyTypeName : modelDTO.getBodyTypeNames()) {
                BodyType bodyType = bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName);
                if (bodyType == null)
                    throw  new CustomException("Body type doesn't exists", HttpStatus.BAD_REQUEST);
                model.getBodyTypes().add(bodyType);
            }
        }

        if(modelDTO.getFuelTypeNames() != null) {
            for (String fuelTypeName : modelDTO.getFuelTypeNames()) {
                FuelType fuelType = fuelTypeRepository.findFuelTypeByFuelTypeName(fuelTypeName);
                if (fuelType == null)
                    throw  new CustomException("Fuel type doesn't exists", HttpStatus.BAD_REQUEST);
                model.getFuelTypes().add(fuelType);
            }
        }

        if(modelDTO.getGearShiftNames() != null) {
            for (String gearShiftName : modelDTO.getBodyTypeNames()) {
                GearShiftType gearShiftType = gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftName);
                if (gearShiftType == null)
                    throw  new CustomException("Gearshift type doesn't exists", HttpStatus.BAD_REQUEST);
                model.getGearShiftTypes().add(gearShiftType);
            }
        }

        return modelRepository.save(model);
    }

    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    public Model getModelByName(String modelName) throws CustomException {
        if(modelRepository.findModelByModelName(modelName) == null)
            throw  new CustomException("Model doesn't exists", HttpStatus.BAD_REQUEST);

        return modelRepository.findModelByModelName(modelName);
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

    public Model addFuelTypeToModel(Model model, FuelType fuelType) {

        model.getFuelTypes().add(fuelType);
        modelRepository.save(model);

        return model;
    }

    public Model addGearShiftTypeToModel(Model model, GearShiftType gearShiftType) {

        model.getGearShiftTypes().add(gearShiftType);
        modelRepository.save(model);

        return model;
    }

    public Model addBodyTypeToModel(Model model, BodyType bodyType) {

        model.getBodyTypes().add(bodyType);
        modelRepository.save(model);

        return model;
    }

    public Model getModelById(Long modelId) throws CustomException {
        if(modelRepository.findModelById(modelId) == null)
            throw  new CustomException("Model doesn't exists", HttpStatus.BAD_REQUEST);

        return modelRepository.findModelById(modelId);
    }
}
