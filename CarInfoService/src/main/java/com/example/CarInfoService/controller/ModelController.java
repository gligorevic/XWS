package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.domain.FuelType;
import com.example.CarInfoService.domain.GearShiftType;
import com.example.CarInfoService.domain.Model;
import com.example.CarInfoService.dto.ModelDTO;
import com.example.CarInfoService.exception.CustomException;
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
            return new ResponseEntity<>(modelService.addNewModel(modelDTO), HttpStatus.CREATED);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("model/{modelId}/fuel-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addFuelTypeToModel(@PathVariable("modelId") Long modelId, @RequestBody String fuelTypeName){

        try{
            Model model = modelService.getModelById(modelId);
            FuelType fuelType = fuelTypeService.getFuelTypeByName(fuelTypeName);

            return new ResponseEntity<>(modelService.addFuelTypeToModel(model, fuelType), HttpStatus.CREATED);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("model/{modelId}/gear-shift-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addGearShiftTypeToModel(@PathVariable("modelId") Long modelId, @RequestBody String gearShiftTypeName){

        try{
            Model model = modelService.getModelById(modelId);
            GearShiftType gearShiftType = gearShiftTypeService.getGearShiftTypeByName(gearShiftTypeName);

            return new ResponseEntity<>(modelService.addGearShiftTypeToModel(model, gearShiftType), HttpStatus.CREATED);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("model/{modelId}/body-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBodyTypeToModel(@PathVariable("modelId") Long modelId, @RequestBody String bodyTypeName){

        try{
            Model model = modelService.getModelById(modelId);
            BodyType bodyType = bodyTypeService.getBodyTypeByName(bodyTypeName);

            return new ResponseEntity<>(modelService.addBodyTypeToModel(model, bodyType), HttpStatus.CREATED);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("model")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getAllModels(){
        try{

            return new ResponseEntity<>(modelService.getAllModels(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelId}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getModelById(@PathVariable("modelId") Long modelId){
        try{

            return new ResponseEntity<>(modelService.getModelById(modelId), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelId}/body-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getBodyTypeByModelId(@PathVariable("modelId") Long modelId){
        try{

            Model model = modelService.getModelById(modelId);

            return new ResponseEntity<>(modelService.getBodyTypesFromModel(model.getId()), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelId}/gear-shift-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getGearShiftTypeByModelId(@PathVariable("modelId") Long modelId){
        try{

            Model model = modelService.getModelById(modelId);

            return new ResponseEntity<>(modelService.getGearShiftTypesFromModel(model.getId()), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelId}/fuel-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getFuelTypeByModelId(@PathVariable("modelId") Long modelId){
        try{

            Model model = modelService.getModelById(modelId);

            return new ResponseEntity<>(modelService.getFuelTypesFromModel(model.getId()), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
