package com.example.PriceListService.controller;

import com.example.PriceListService.dto.ReportDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> addReport(@RequestBody ReportDTO reportDTO, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            return new ResponseEntity<>(reportService.addNewReport(reportDTO, userEmail), HttpStatus.CREATED);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
