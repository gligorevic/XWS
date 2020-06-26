package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.*;
import com.example.CarInfoService.dto.ModelDTO;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.service.BodyTypeService;
import com.example.CarInfoService.service.FuelTypeService;
import com.example.CarInfoService.service.GearShiftTypeService;
import com.example.CarInfoService.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(ModelController.class);


    @PostMapping("model")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addModel(@RequestBody ModelDTO modelDTO, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Model model = modelService.addNewModel(modelDTO);
            log.info("Model successfully added by user {}", bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(model, HttpStatus.CREATED);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("model/{modelId}/fuel-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addFuelTypeToModel(@PathVariable("modelId") Long modelId, @RequestBody String fuelTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Model model = modelService.getModelById(modelId);
            FuelType fuelType = fuelTypeService.getFuelTypeByName(fuelTypeName);
            Model newModel = modelService.addFuelTypeToModel(model, fuelType);
            log.info("Fuel type {} successfully added to model {} by user {}", fuelType.getFuelTypeName(), newModel.getModelName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(newModel, HttpStatus.CREATED);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("model/{modelId}/gear-shift-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addGearShiftTypeToModel(@PathVariable("modelId") Long modelId, @RequestBody String gearShiftTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Model model = modelService.getModelById(modelId);
            GearShiftType gearShiftType = gearShiftTypeService.getGearShiftTypeByName(gearShiftTypeName);
            Model newModel = modelService.addGearShiftTypeToModel(model, gearShiftType);
            log.info("Gearshift type {} successfully added to model {} by user {}", gearShiftType.getGearShiftName(), newModel.getModelName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(modelService.addGearShiftTypeToModel(newModel, gearShiftType), HttpStatus.CREATED);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("model/{modelId}/body-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBodyTypeToModel(@PathVariable("modelId") Long modelId, @RequestBody String bodyTypeName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Model model = modelService.getModelById(modelId);
            BodyType bodyType = bodyTypeService.getBodyTypeByName(bodyTypeName);
            Model newModel = modelService.addBodyTypeToModel(model, bodyType);
            log.info("Body type {} successfully added to model {} by user {}", bodyType.getBodyTypeName(), newModel.getModelName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(modelService.addBodyTypeToModel(newModel, bodyType), HttpStatus.CREATED);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("model/{modelId}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> editModel(@PathVariable("modelId") Long modelId, @RequestBody String modelName, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Model oldModel = modelService.getModelById(modelId);
            Model newModel = modelService.editModel(modelId, modelName);
            log.info("User {} edited model name from {} to {}.", oldModel.getModelName(), newModel.getModelName(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(newModel, HttpStatus.OK);

        } catch(CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("model")
    public ResponseEntity<?> getAllModels(){
        try{

            return new ResponseEntity<>(modelService.getAllModels(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelId}/body-type")
    public ResponseEntity<?> getBodyTypeByModelId(@PathVariable("modelId") Long modelId){
        try{

            Model model = modelService.getModelById(modelId);

            return new ResponseEntity<>(modelService.getBodyTypesFromModel(model.getId()), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelId}/gear-shift-type")
    public ResponseEntity<?> getGearShiftTypeByModelId(@PathVariable("modelId") Long modelId){
        try{

            Model model = modelService.getModelById(modelId);

            return new ResponseEntity<>(modelService.getGearShiftTypesFromModel(model.getId()), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("model/{modelId}/fuel-type")
    public ResponseEntity<?> getFuelTypeByModelId(@PathVariable("modelId") Long modelId){
        try{

            Model model = modelService.getModelById(modelId);

            return new ResponseEntity<>(modelService.getFuelTypesFromModel(model.getId()), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
