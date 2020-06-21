package com.example.RequestService.controller;

import com.example.RequestService.exception.CustomException;
import com.example.RequestService.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserRequestController {

    @Autowired
    private UserRequestService userRequestService;

    @GetMapping("/{email}/created")
    public ResponseEntity<?> getAllCreatedRequests(@PathVariable("email") String email, Authentication authentication, @RequestHeader("Auth") String auth){
        try{
            String userEmail = (String)authentication.getPrincipal();
            if(!email.equals(userEmail))
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(userRequestService.getAllCreatedRequests(email, auth), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email}/paid")
    public ResponseEntity<?> getAllPaid(@PathVariable("email") String email, Authentication authentication){
        try{
            String userEmail = (String)authentication.getPrincipal();
            if(!email.equals(userEmail))
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(userRequestService.getAllPaid(email), HttpStatus.OK);

        } catch(CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
