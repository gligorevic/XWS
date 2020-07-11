package com.example.PriceListService.controller;

import com.example.PriceListService.dto.DataForPriceCalculationDTO;
import com.baeldung.springsoap.gen.GetPricelistRequest;
import com.baeldung.springsoap.gen.GetPricelistResponse;
import com.example.PriceListService.dto.PriceListDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.service.PriceListService;
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
public class PriceListController {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";


    @Autowired
    private PriceListService priceListService;

    @PostMapping
    public ResponseEntity<?> addNewPricelist(@RequestBody PriceListDTO priceListDTO, Authentication authentication) throws CustomException {
        try{
            String userEmail = (String) authentication.getPrincipal();
            return new ResponseEntity<>(priceListService.addNewPriceList(priceListDTO, userEmail), HttpStatus.CREATED);
        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/price")
    public ResponseEntity<?> getPriceForAdInPeriod(@RequestBody DataForPriceCalculationDTO dataForPriceCalculationDTO) {
        try{
            return new ResponseEntity<>(priceListService.calculatePriceForRequest(dataForPriceCalculationDTO), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPricelistsByUserEmail(Authentication authentication){
        try{
            String userEmail = (String) authentication.getPrincipal();
            return new ResponseEntity<>(priceListService.getPricelistsByUserEmail(userEmail), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPricelistRequest")
    @ResponsePayload
    public GetPricelistResponse addResPeriodAgent(@RequestPayload GetPricelistRequest request) {
        try{
            GetPricelistResponse response = new GetPricelistResponse();
            response.setId(priceListService.addPricelistAgent(request.getPricelist()));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
