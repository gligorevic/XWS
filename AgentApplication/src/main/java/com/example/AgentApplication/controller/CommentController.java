package com.example.AgentApplication.controller;


import com.example.AgentApplication.dto.CommentDTO;
import com.example.AgentApplication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO){
        try{
            return new ResponseEntity<>(commentService.addComment(commentDTO), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
