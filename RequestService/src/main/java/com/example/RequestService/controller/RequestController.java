package com.example.RequestService.controller;

import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import com.example.RequestService.dto.RequestContainerDTO;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.dto.ReservationPeriodDTO;
import com.example.RequestService.exception.CustomException;
import com.example.RequestService.service.RequestService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO requestDTO){
        try{

            Request request = new Request(requestDTO);
            request.setAdId(requestDTO.getId());
            return new ResponseEntity<>(requestService.add(request), HttpStatus.CREATED);

        }catch (Exception e){
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

    @PutMapping
    public ResponseEntity<?> cancelRequestsReservationPeriod(@RequestBody ReservationPeriodDTO reservationPeriodDTO){
        try{
            return new ResponseEntity<>(requestService.cancelRequestsReservationPeriod(reservationPeriodDTO), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
