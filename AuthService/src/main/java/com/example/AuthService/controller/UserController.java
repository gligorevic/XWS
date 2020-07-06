package com.example.AuthService.controller;

import com.example.AuthService.constants.Format;
import com.example.AuthService.domain.User;
import com.example.AuthService.dto.*;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.service.AdminService;
import com.example.AuthService.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.example.AuthService.security.SecurityConstants.TOKEN_BEARER_PREFIX;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        try {
            hasEmailAndPassword(loginRequestDTO);
            String jwt = userService.login(loginRequestDTO, request);
            log.info("Successfull auth {}", bCryptPasswordEncoder.encode(loginRequestDTO.getUsername()));
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Fail auth {}. {}.", bCryptPasswordEncoder.encode(loginRequestDTO.getUsername()), e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void hasEmailAndPassword(LoginRequestDTO loginRequestDTO) throws CustomException {
        if (StringUtils.isEmpty(loginRequestDTO.getUsername()) || StringUtils.isEmpty(loginRequestDTO.getPassword())) {
            throw new CustomException("Username or password not provided.", HttpStatus.NOT_ACCEPTABLE);
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
            log.error("AccessToken generation failed.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            hasAllRegistrationData(userDTO);

            return new ResponseEntity<>(userService.register(userDTO), HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/agent")
    public ResponseEntity<?> registerAgent(@RequestBody AgentRegistrationDTO agentRegistrationDTO) {
        try {
            hasAllRegistrationData(agentRegistrationDTO.getUserDTO());
            hasAllCompanyData(agentRegistrationDTO.getCompanyDTO());
            return new ResponseEntity<>(userService.registerAgent(agentRegistrationDTO.getUserDTO(), agentRegistrationDTO.getCompanyDTO()), HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void hasAllCompanyData(CompanyDTO companyDTO) throws CustomException {
        if(StringUtils.isEmpty(companyDTO.getCompanyName()) || StringUtils.isEmpty(companyDTO.getRegistrationNumber()) || StringUtils.isEmpty(companyDTO.getPhoneNumber()) || StringUtils.isEmpty(companyDTO.getAddress())){
            throw new CustomException("Company fields must not be empty.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(!Format.phoneNumber.matcher(companyDTO.getPhoneNumber()).matches()){
            throw new CustomException("Improper phone number format.", HttpStatus.NOT_ACCEPTABLE);
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
        } catch (CustomException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
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
    public ResponseEntity<?> logicalDeleteUser(@PathVariable String userId, Authentication authentication) {
        try {
            return new ResponseEntity<>(adminService.logicalDeleteUser(Long.parseLong(userId), authentication), HttpStatus.OK);
        } catch (CustomException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PutMapping("/password/change")
    @PreAuthorize("hasAuthority('PASSWORD_CHANGEING')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO newPasswordData, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        try {
            if(!user.getEmail().equals(newPasswordData.getUsername())) throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            userService.changePassword(newPasswordData);
            log.info("{} successfully changed password.", user.getEmail());
            return new ResponseEntity<>("Password successfully changed", HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{} tried to change {} password.", bCryptPasswordEncoder.encode(user.getEmail()), bCryptPasswordEncoder.encode(newPasswordData.getUsername()));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
