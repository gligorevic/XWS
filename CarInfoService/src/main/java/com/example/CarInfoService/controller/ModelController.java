package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.domain.GearShiftType;
import com.example.CarInfoService.domain.Model;
import com.example.CarInfoService.dto.ModelDTO;
import com.example.CarInfoService.service.BodyTypeService;
import com.example.CarInfoService.service.FuelTypeService;
import com.example.CarInfoService.service.GearShiftTypeService;
import com.example.CarInfoService.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private FuelTypeService fuelTypeService;

    @Autowired
    private GearShiftTypeService gearShiftTypeService;

    @Autowired
    private BodyTypeService bodyTypeService;



    @PostMapping("model")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addModel(@RequestBody ModelDTO modelDTO){

        try{
            Model model = modelService.addNewModel(modelDTO);
            if(model == null)
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(model, HttpStatus.CREATED);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("model/{modelName}/fuel-type/{fuelTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addFuelTypeToModel(@PathVariable("modelName") String modelName, @PathVariable("fuelTypeName") String fuelTypeName){

        try{
            Model model = modelService.getModelByName(modelName);
            FuelType fuelType = fuelTypeService.getFuelTypeByName(fuelTypeName);
            if(model == null || fuelType == null)
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(modelService.addFuelTypeToModel(model, fuelType), HttpStatus.CREATED);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("model/{modelName}/gear-shift-type/{gearShiftTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addGearShiftTypeToModel(@PathVariable("modelName") String modelName, @PathVariable("gearShiftTypeName") String gearShiftTypeName){

        try{
            Model model = modelService.getModelByName(modelName);
            GearShiftType gearShiftType = gearShiftTypeService.getGearShiftTypeByName(gearShiftTypeName);
            if(model == null || gearShiftType == null)
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(modelService.addGearShiftTypeToModel(model, gearShiftType), HttpStatus.CREATED);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("model/{modelName}/body-type/{bodyTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBodyTypeToModel(@PathVariable("modelName") String modelName, @PathVariable("bodyTypeName") String bodyTypeName){

        try{
            Model model = modelService.getModelByName(modelName);
            BodyType bodyType = bodyTypeService.getBodyTypeByName(bodyTypeName);
            if(model == null || bodyType == null)
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(modelService.addBodyTypeToModel(model, bodyType), HttpStatus.CREATED);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("model")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getAllModels(){
        try{

            List<Model> models = modelService.getAllModels();

            if(models == null || models.isEmpty())
                models = new ArrayList<>();

            return new ResponseEntity<>(models, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getModelByName(@PathVariable("modelName") String modelName){
        try{

            Model model = modelService.getModelByName(modelName);

            if(model == null)
                return new ResponseEntity<>("No model with that name.", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(model, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelName}/body-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getBodyTypeByModelName(@PathVariable("modelName") String modelName){
        try{

            Model model = modelService.getModelByName(modelName);

            if(model == null)
                return new ResponseEntity<>("No model with that name.", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(modelService.getBodyTypesFromModel(model.getId()), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelName}/gear-shift-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getGearShiftTypeByModelName(@PathVariable("modelName") String modelName){
        try{

            Model model = modelService.getModelByName(modelName);

            if(model == null)
                return new ResponseEntity<>("No model with that name.", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(modelService.getGearShiftTypesFromModel(model.getId()), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelName}/fuel-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getFuelTypeByModelName(@PathVariable("modelName") String modelName){
        try{

            Model model = modelService.getModelByName(modelName);

            if(model == null)
                return new ResponseEntity<>("No model with that name.", HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(modelService.getFuelTypesFromModel(model.getId()), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
