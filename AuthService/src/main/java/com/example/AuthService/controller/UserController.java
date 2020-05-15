package com.example.AuthService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping
    public ResponseEntity<String> sayDjesNeshke() {
        return new ResponseEntity<>("Djes Neshke", HttpStatus.OK);
    }
}
