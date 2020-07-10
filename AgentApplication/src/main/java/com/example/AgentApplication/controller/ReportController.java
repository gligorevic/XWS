package com.example.AgentApplication.controller;

import com.example.AgentApplication.dto.ReportDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(reportService.getReportByRequestId(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_AGENT')")
    public ResponseEntity<?> addReport(@RequestBody ReportDTO reportDTO){
        try{
            return new ResponseEntity<>(reportService.addNewReport(reportDTO), HttpStatus.CREATED);
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
