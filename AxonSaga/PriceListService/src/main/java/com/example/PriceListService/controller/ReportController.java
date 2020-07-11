package com.example.PriceListService.controller;

import com.baeldung.springsoap.gen.GetPricelistItemRequest;
import com.baeldung.springsoap.gen.GetPricelistItemResponse;
import com.baeldung.springsoap.gen.GetReportRequest;
import com.baeldung.springsoap.gen.GetReportResponse;
import com.example.PriceListService.dto.ReportDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RestController
@RequestMapping(value="/report")
public class ReportController {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";

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
    public ResponseEntity<?> addReport(@RequestBody ReportDTO reportDTO, Authentication authentication, @RequestHeader("Auth") String auth){
        String userEmail = (String) authentication.getPrincipal();
        try{
            return new ResponseEntity<>(reportService.addNewReport(reportDTO, userEmail, auth), HttpStatus.CREATED);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReportRequest")
    @ResponsePayload
    public GetReportResponse addReportAgent(@RequestPayload GetReportRequest request) {
        try{
            GetReportResponse response = new GetReportResponse();
            response.setId(reportService.addReportAgent(request.getReport()));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
