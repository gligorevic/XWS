package com.example.AuthService.controller;

import com.example.AuthService.service.PrivilegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivilegesController {

    @Autowired
    private PrivilegeService privilegeService;

    private static final Logger log = LoggerFactory.getLogger(PrivilegesController.class);

    @GetMapping("/privilege")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> getAllPrivileges() {
        try {
            return new ResponseEntity<>(privilegeService.getAllPrivileges(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
