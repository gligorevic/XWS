package com.example.AgentApplication.controller;

import com.example.AgentApplication.domain.Grade;
import com.example.AgentApplication.dto.GradeDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;


    @GetMapping("/{reqId}")
    public ResponseEntity<?> getGradeForRequest(@PathVariable("reqId") String reqId) {
        try {
            return new ResponseEntity<>(gradeService.getGradeForRequest(Long.parseLong(reqId)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bundle/{reqId}")
    public ResponseEntity<?> getGradeForBundleRequest(@PathVariable("reqId") String reqId) {
        try {
            return new ResponseEntity<>(gradeService.getGradeForBundleRequest(Long.parseLong(reqId)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ENDUSER')")
    public ResponseEntity<?> addGrade(@RequestBody GradeDTO gradeDTO, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            if(!userEmail.equals(gradeDTO.getUsername())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

            Grade grade = gradeService.add(gradeDTO);
            return new ResponseEntity<>(grade, HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bundle")
    @PreAuthorize("hasAuthority('ROLE_ENDUSER')")
    public ResponseEntity<?> addBundleGrade(@RequestBody GradeDTO gradeDTO, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            if(!userEmail.equals(gradeDTO.getUsername())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Grade grade = gradeService.addBundleGrade(gradeDTO);
            return new ResponseEntity<>(grade, HttpStatus.CREATED);

        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
