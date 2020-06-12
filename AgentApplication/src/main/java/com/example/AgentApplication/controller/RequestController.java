package com.example.AgentApplication.controller;

import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.dto.RequestContainerDTO;
import com.example.AgentApplication.dto.RequestDTO;
import com.example.AgentApplication.dto.ReservationPeriodDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO requestDTO){
        try{
            return new ResponseEntity<>(requestService.add(requestDTO), HttpStatus.CREATED);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bundle")
    public ResponseEntity<?> addBundleRequest(@RequestBody RequestContainerDTO requestContainerDTO){
        try{

            return new ResponseEntity<>(requestService.addBundle(requestContainerDTO), HttpStatus.CREATED);

        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



}
