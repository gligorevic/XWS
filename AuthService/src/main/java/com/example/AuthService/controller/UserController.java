package com.example.AuthService.controller;

import com.example.AuthService.dto.LoginRequestDTO;
import com.example.AuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.AuthService.security.SecurityConstants.TOKEN_BEARER_PREFIX;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            return new ResponseEntity<String>(userService.login(loginRequestDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody String bearerToken) {
        try {
            String accessBearerToken = TOKEN_BEARER_PREFIX + userService.verifyUser(bearerToken);
            return new ResponseEntity<String>(accessBearerToken, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MESSAGE_CREATING')")
    public ResponseEntity<?> sayHello() {
        System.out.println("Haluuu");
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }


}
