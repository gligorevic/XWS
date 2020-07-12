package com.example.AgentApplication.controller;

import com.example.AgentApplication.service.SynchronizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/synchronize")
public class SynchronizeController {

    @Autowired
    private SynchronizeService synchronizeService;

    @PutMapping("/cars")
    public ResponseEntity<?> cars() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizeCars(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/ad")
    public ResponseEntity<?> advertisements() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizeAdvertisements(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/pricelists")
    public ResponseEntity<?> pricelists() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizePricelists(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/pricelistItems")
    public ResponseEntity<?> pricelistItems() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizePricelistItems(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/containers")
    public ResponseEntity<?> containers() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizeRequestContainers(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/requests")
    public ResponseEntity<?> requests() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizeRequests(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/reports")
    public ResponseEntity<?> reports() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizeReports(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/reservationPeriods")
    public ResponseEntity<?> reservationPeriods() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizeReservationPeriods(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/comments")
    public ResponseEntity<?> comments() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizeComments(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/grades")
    public ResponseEntity<?> grades() {
        try {
            return new ResponseEntity<>(synchronizeService.synchronizeGrades(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
