package com.example.AuthService.controller;

import com.example.AuthService.exception.CustomException;
import com.example.AuthService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PutMapping("/user/{userId}/privilege")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> setUserBlockedPrivileges(@PathVariable String userId, @RequestBody List<Long> privilegeList) {
        try {
            return new ResponseEntity<>(adminService.changeUserPrivileges(privilegeList, Long.parseLong(userId)), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PutMapping("/user/{userId}/block")
    @PreAuthorize("hasAuthority('USER_BLOCKING')")
    public ResponseEntity<?> toggleBlockUser(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(adminService.toggleBlockUser(Long.parseLong(userId)), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @GetMapping("/user/{userId}/privilege")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> getPrivilegesByUserId(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(adminService.getBlockedPrivilegesByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
