package com.example.AuthService.controller;

import com.example.AuthService.dto.PrivilegeChangeDTO;
import com.example.AuthService.dto.UserDTO;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.service.AdminService;
import com.example.AuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PutMapping("/user/changePrivileges")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> setUserBlockedPrivileges(@RequestBody PrivilegeChangeDTO privilegeChangeDTO) {
        try {
            return new ResponseEntity<Boolean>(adminService.changeUserPrivileges(privilegeChangeDTO.getPrivilegeList(), privilegeChangeDTO.getEnduserId()), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PutMapping("/user/toggleBlock/{userId}")
    @PreAuthorize("hasAuthority('USER_BLOCKING')")
    public ResponseEntity<?> toggleBlockUser(@PathVariable String userId) {
        try {
            return new ResponseEntity<Boolean>(adminService.toggleBlockUser(Long.parseLong(userId)), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PutMapping("/user/deleteUser/{userId}")
    @PreAuthorize("hasAuthority('USER_DELETING')")
    public ResponseEntity<?> logicalDeleteUser(@PathVariable String userId) {
        try {
            return new ResponseEntity<UserDTO>(adminService.logicalDeleteUser(Long.parseLong(userId)), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), e.getHttpStatus());
        }
    }

    @GetMapping("/user/blockedPrivileges/{userId}")
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
