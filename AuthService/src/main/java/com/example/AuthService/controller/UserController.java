package com.example.AuthService.controller;

import com.example.AuthService.constants.Format;
import com.example.AuthService.domain.User;
import com.example.AuthService.dto.LoginRequestDTO;
import com.example.AuthService.dto.UserDTO;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.service.AdminService;
import com.example.AuthService.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

import static com.example.AuthService.security.SecurityConstants.TOKEN_BEARER_PREFIX;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            hasEmailAndPassword(loginRequestDTO);
            return new ResponseEntity<>(userService.login(loginRequestDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void hasEmailAndPassword(LoginRequestDTO loginRequestDTO) throws CustomException {
        if (StringUtils.isEmpty(loginRequestDTO.getUsername()) || StringUtils.isEmpty(loginRequestDTO.getPassword())) {
            throw new CustomException("Fields must not be empty.", HttpStatus.NOT_ACCEPTABLE);
        } else if (!Format.email.matcher(loginRequestDTO.getUsername()).matches()) {
            throw new CustomException("Improper email format.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody String bearerToken) {
        try {
            String accessBearerToken = TOKEN_BEARER_PREFIX + userService.verifyUser(bearerToken);
            return new ResponseEntity<>(accessBearerToken, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            hasAllRegistrationData(userDTO);
            return new ResponseEntity<UserDTO>(userService.register(userDTO), HttpStatus.CREATED);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    private void hasAllRegistrationData(@RequestBody UserDTO userDTO) throws CustomException {
        if (StringUtils.isEmpty(userDTO.getEmail()) || StringUtils.isEmpty(userDTO.getFirstName()) || StringUtils.isEmpty(userDTO.getLastName()) || StringUtils.isEmpty(userDTO.getPassword()) || StringUtils.isEmpty(userDTO.getRoleName())) {
            throw new CustomException("Fields must not be empty.", HttpStatus.NOT_ACCEPTABLE);
        } else if (!Format.email.matcher(userDTO.getEmail()).matches()) {
            throw new CustomException("Improper email format.", HttpStatus.NOT_ACCEPTABLE);
        } else if (!Format.password.matcher(userDTO.getPassword()).matches()) {
            throw new CustomException("Password doesn't match requirements.", HttpStatus.NOT_ACCEPTABLE);
        } else if (!Format.name.matcher(userDTO.getFirstName()).matches()) {
            throw new CustomException("First name doesn't match reqirements", HttpStatus.NOT_ACCEPTABLE);
        } else if (!Format.name.matcher(userDTO.getLastName()).matches()) {
            throw new CustomException("Last name doesn't match reqirements", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAuthority('PROFILE_VIEWING')")
    public ResponseEntity<?> getUserProfile(@PathVariable String email, Authentication authentication) {
        try {
            hasEmail(email);
            return new ResponseEntity<>(userService.getUser(email, authentication), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void hasEmail(String email) throws CustomException {
        if (StringUtils.isEmpty(email) || !Format.email.matcher(email).matches()) {
            throw new CustomException("Improper email format.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('USER_DELETING')")
    public ResponseEntity<?> logicalDeleteUser(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(adminService.logicalDeleteUser(Long.parseLong(userId)), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }
}
