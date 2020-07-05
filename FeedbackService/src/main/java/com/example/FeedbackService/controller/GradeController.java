package com.example.FeedbackService.controller;

import com.example.FeedbackService.domain.Grade;
import com.example.FeedbackService.dto.GradeDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.service.GradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(GradeController.class);

    @GetMapping("/grade/{reqId}")
    public ResponseEntity<?> getGradeForRequest(@PathVariable("reqId") String reqId) {
        try {
            return new ResponseEntity<>(gradeService.getGradeForRequest(Long.parseLong(reqId)), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/grade")
    public ResponseEntity<?> addGrade(@RequestBody GradeDTO gradeDTO, Authentication authentication, @RequestHeader("Auth") String auth){
        String userEmail = (String) authentication.getPrincipal();
        try{
            if(!userEmail.equals(gradeDTO.getUsername())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Grade grade = gradeService.add(gradeDTO, auth);
            log.info("User {} added grade for request {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(gradeDTO.getRequestId().toString()));

            return new ResponseEntity<>(grade, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch(Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/grade/average")
    public ResponseEntity<?> getAgentAverageGrade(@RequestBody List<String> userEmails){
        try{
            return new ResponseEntity<>(gradeService.calculateAgentAverageGrade(userEmails), HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}

