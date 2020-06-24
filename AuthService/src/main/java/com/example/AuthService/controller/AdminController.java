package com.example.AuthService.controller;

import com.example.AuthService.domain.User;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @PutMapping("/user/{userId}/privilege")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> setUserBlockedPrivileges(@PathVariable String userId, @RequestBody List<Long> privilegeList, Authentication authentication) {
        User admin = (User)authentication.getPrincipal();
        try {
            return new ResponseEntity<>(adminService.changeUserPrivileges(privilegeList, Long.parseLong(userId), admin.getEmail()), HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action is made by admin {}.", e.getMessage(), bCryptPasswordEncoder.encode(admin.getEmail()));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PutMapping("/user/{userId}/block")
    @PreAuthorize("hasAuthority('USER_BLOCKING')")
    public ResponseEntity<?> toggleBlockUser(@PathVariable String userId, Authentication authentication) {
        User admin = (User)authentication.getPrincipal();
        try {
            return new ResponseEntity<>(adminService.toggleBlockUser(Long.parseLong(userId), admin.getEmail()), HttpStatus.OK);
        } catch (CustomException e) {
            log.error("{}. Action is made by admin {}.", e.getMessage(), bCryptPasswordEncoder.encode(admin.getEmail()));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @GetMapping("/user/{userId}/privilege")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> getPrivilegesByUserId(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(adminService.getBlockedPrivilegesByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
