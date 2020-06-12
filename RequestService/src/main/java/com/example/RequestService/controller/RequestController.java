package com.example.RequestService.controller;

import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import com.example.RequestService.dto.RequestContainerDTO;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.dto.ReservationPeriodDTO;
import com.example.RequestService.exception.CustomException;
import com.example.RequestService.service.RequestService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getAllRequestsForAd(@PathVariable("adId") String adId){
        try{
            return new ResponseEntity<>(requestService.getAllRequestsForAd(Long.parseLong(adId)), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<?> getAllRequestsInfo(@PathVariable("username") String username){
        try{
            return new ResponseEntity<>(requestService.getAllRequestsInfo(username), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestBody RequestDTO requestDTO){
        try{

            return new ResponseEntity<>(requestService.add(requestDTO), HttpStatus.CREATED);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bundle")
    public ResponseEntity<?> addBundleRequest(@RequestBody RequestContainerDTO requestContainerDTO){
        try{

            return new ResponseEntity<>(requestService.addBundle(requestContainerDTO), HttpStatus.CREATED);

        }catch (CustomException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> cancelRequestsReservationPeriod(@RequestBody ReservationPeriodDTO reservationPeriodDTO){
        try{
            return new ResponseEntity<>(requestService.cancelRequestsReservationPeriod(reservationPeriodDTO), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
