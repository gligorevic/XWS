package com.example.AgentApplication.controller;

import com.example.AgentApplication.domain.BodyType;
import com.example.AgentApplication.domain.FuelType;
import com.example.AgentApplication.domain.GearShiftType;
import com.example.AgentApplication.domain.Model;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.BodyTypeService;
import com.example.AgentApplication.service.FuelTypeService;
import com.example.AgentApplication.service.GearShiftTypeService;
import com.example.AgentApplication.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;


    @GetMapping
    public ResponseEntity<?> getAllModels(){
        try{

            return new ResponseEntity<>(modelService.getAllModels(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{modelId}")
    //@PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
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

    @GetMapping("/{modelId}/body-type")
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

    @GetMapping("/{modelId}/gear-shift-type")
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

    @GetMapping("/{modelId}/fuel-type")
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
