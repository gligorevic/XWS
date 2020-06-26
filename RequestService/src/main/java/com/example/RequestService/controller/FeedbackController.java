package com.example.RequestService.controller;

import com.example.RequestService.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedbackController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/feedRequest")
    @PreAuthorize("hasAuthority('REQUEST_VIEWING')")
    public ResponseEntity<?> getRequestById(@RequestBody Long requestId){
        try {
            return new ResponseEntity<>(requestService.getRequestById(requestId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
