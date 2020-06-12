package com.example.CarInfoService.controller;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.service.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BodyTypeController {

    @Autowired
    private BodyTypeService bodyTypeService;

    @PostMapping("body-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> addBodyType(@RequestBody String bodyTypeName){
        try{

            return new ResponseEntity<>(bodyTypeService.addBodyType(bodyTypeName), HttpStatus.CREATED);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("body-type/{bodyTypeId}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> editGearShiftType(@PathVariable("bodyTypeId") Long bodyTypeId, @RequestBody String bodyTypeName){

        try{
            return new ResponseEntity<>(bodyTypeService.editBodyType(bodyTypeId, bodyTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("body-type")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getAllBodyTypes(){
        try{

            List<BodyType> bodyTypes = bodyTypeService.getAllBodyTypes();

            if(bodyTypes == null || bodyTypes.isEmpty())
                bodyTypes = new ArrayList<>();

            return new ResponseEntity<>(bodyTypes, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("body-type/{bodyTypeName}")
    @PreAuthorize("hasAuthority('CAR_CODEBOOK_CRUD')")
    public ResponseEntity<?> getBodyTypeByName(@PathVariable("bodyTypeName") String bodyTypeName){
        try{

            return new ResponseEntity<>(bodyTypeService.getBodyTypeByName(bodyTypeName), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

}
