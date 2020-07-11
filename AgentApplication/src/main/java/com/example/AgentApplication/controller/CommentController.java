package com.example.AgentApplication.controller;


import com.example.AgentApplication.dto.CommentDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{adId}")
    public ResponseEntity<?> getCommentsForAdvertisement(@PathVariable("adId") Long adId){
        try{
            return new ResponseEntity<>(commentService.getCommentsByAdvertisementId(adId), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/request/{reqId}")
    public ResponseEntity<?> getAllCommentsForRequest(@PathVariable("reqId") String reqId, Authentication authentication) {
        try {
            return new ResponseEntity<>(commentService.getAllCommentsForRequest(Long.parseLong(reqId), authentication), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bundle/{reqId}")
    public ResponseEntity<?> getAllCommentsForBundleRequest(@PathVariable("reqId") String reqId, Authentication authentication) {
        try {
            return new ResponseEntity<>(commentService.getAllCommentsForBundleRequest(Long.parseLong(reqId), authentication), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        try {
            String userEmail = (String) authentication.getPrincipal();
            if (!userEmail.equals(commentDTO.getUserEmail())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(commentService.add(commentDTO), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/comment/bundle")
    public ResponseEntity<?> addBundleComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(commentDTO.getUserEmail())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(commentService.addBundleComment(commentDTO), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
