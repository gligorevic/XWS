package com.example.AuthService.controller;

import com.example.AuthService.domain.Company;
import com.example.AuthService.domain.User;
import com.example.AuthService.dto.CompanyDTO;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/company/requests")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> findAllPending(){
        try{
            return new ResponseEntity<>(companyService.findAllPending(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{id}/company")
    public ResponseEntity<?> getCompany(@PathVariable("id") Long userId){
        try{
            return new ResponseEntity<>(companyService.getCompany(userId), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{userId}/company/reregister")
    @PreAuthorize("hasAuthority('DATA_SYNCHRONIZATION')")
    public ResponseEntity<?> reregisterCompany(@PathVariable("userId") Long userId, @RequestBody CompanyDTO companyDTO, Authentication authentication){
        try{
            User user = (User) authentication.getPrincipal();
            if(!user.getId().equals(userId))
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(companyService.reregisterCompany(user, companyDTO), HttpStatus.OK);
        }catch(CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/company/{id}/accept")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> companyRequestAccept(@PathVariable("id") Long requestId){
        try{
            return new ResponseEntity<>(companyService.acceptRequest(requestId), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/company/{id}/decline")
    @PreAuthorize("hasAuthority('ENDUSER_PERMISION_CHANGING')")
    public ResponseEntity<?> companyRequestDecline(@PathVariable("id") Long requestId){
        try{
            return new ResponseEntity<>(companyService.declineRequest(requestId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{id}/company/status")
    public ResponseEntity<?> getCompanyStatus(@PathVariable("id") Long userId, Authentication authentication){
        try{
            User user = (User) authentication.getPrincipal();

            if(!user.getId().equals(userId))
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(companyService.getCompanyStatus(userId), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
