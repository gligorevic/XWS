package com.example.SearchService.controller;

import com.baeldung.springsoap.gen.GetAdvertisementRequest;
import com.baeldung.springsoap.gen.GetAdvertisementResponse;
import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.dto.AdvertisementStatisticDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.service.AdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
@RestController
public class AdvertisementController {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(AdvertisementController.class);

    @GetMapping
    public ResponseEntity<?> getAllAdvertisements(){
        try{
            return new ResponseEntity<>(advertisementService.getAllAdvertisements(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<?> addAdvertisement(@RequestBody AdvertisementDTO advertisementDTO, Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            Advertisement advertisement = advertisementService.addAdvertisement(advertisementDTO);
            log.info("Successful added advertisement {} by user {}", bCryptPasswordEncoder.encode(advertisement.getId().toString()), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(advertisement, HttpStatus.OK);
        } catch(CustomException e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e){
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<List<AdvertisementStatisticDTO>> getAdvertisementsByUserIdStatistic(Authentication authentication){
        String userEmail = (String) authentication.getPrincipal();
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementsByUserIdStatistic(userEmail), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAuthority('ADVERTISEMENT_ADMINISTRATION')")
    public ResponseEntity<List<Advertisement>> getAdvertisementsByUserId(@PathVariable String email){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementsByUserId(email), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementById(id), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAdvertisementRequest")
    @ResponsePayload
    public GetAdvertisementResponse addCarFromAgentApp(@RequestPayload GetAdvertisementRequest request) {
        try{
            GetAdvertisementResponse response = new GetAdvertisementResponse();
            response.setId(advertisementService.addNewAdvertisementByAgent(request));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @PostMapping("/simple/{id}")
    public ResponseEntity<?> getSimpleAdvertisementById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(advertisementService.getAdvertisementForCalculating(id), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/priceKm/{priceKm}/priceDay/{priceDay}")
    public ResponseEntity<?> setPriceForAdvertisement(@PathVariable Long id, @PathVariable Integer priceKm, @PathVariable Integer priceDay){
        try{
            return new ResponseEntity<>(advertisementService.setPriceForAdvertisement(id, priceKm, priceDay), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
