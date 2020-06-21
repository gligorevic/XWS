package com.example.FeedbackService.controller;

import com.example.FeedbackService.dto.GradeDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class GradeController {

    @Autowired
    private GradeService gradeService;


    @GetMapping("/grade/{reqId}")
    public ResponseEntity<?> getGradeForRequest(@PathVariable("reqId") String reqId) {
        try {
            return new ResponseEntity<>(gradeService.getGradeForRequest(Long.parseLong(reqId)), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/grade")
    public ResponseEntity<?> addGrade(@RequestBody GradeDTO gradeDTO, Authentication authentication){
        try{
            String userEmail = (String) authentication.getPrincipal();
            if(!userEmail.equals(gradeDTO.getUsername())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(gradeService.add(gradeDTO), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

}

