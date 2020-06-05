package com.example.RequestService.controller;

import com.example.RequestService.domain.Request;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.service.RequestService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO requestDTO){
        try{

            Request request = new Request(requestDTO);
            return new ResponseEntity<>(requestService.add(request), HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBundleRequest(@RequestBody RequestDTO requestDTO){
        try{

            Request request = new Request(requestDTO);
            return new ResponseEntity<>(requestService.add(request), HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
