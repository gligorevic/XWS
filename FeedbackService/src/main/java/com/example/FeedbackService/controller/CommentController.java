package com.example.FeedbackService.controller;

import com.example.FeedbackService.domain.Comment;
import com.example.FeedbackService.dto.CommentDTO;
import com.example.FeedbackService.dto.CommentStatusDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/comment/{reqId}")
    public ResponseEntity<?> getAllCommentsForRequest(@PathVariable("reqId") String reqId) {
        try {
            return new ResponseEntity<>(commentService.getAllCommentsForRequest(Long.parseLong(reqId)), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comment")
    @PreAuthorize("hasAuthority('RENT_COMMENT_APPROVING')")
    public ResponseEntity<?> getAllCommentsForAdmin() {
        try {
            return new ResponseEntity<>(commentService.getAllCommentsForAdmin(), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        try {
            String userEmail = (String) authentication.getPrincipal();
            if (!userEmail.equals(commentDTO.getUsername())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(commentService.add(commentDTO), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/comment/{commentId}")
    @PreAuthorize("hasAuthority('RENT_COMMENT_APPROVING')")
    public ResponseEntity<?> changeCommentStatus(@RequestBody CommentStatusDTO commentStatusDTO,
                                                 @PathVariable("commentId") Long commentId, Authentication authentication) {
        try {

            Comment comment = commentService.getCommentById(commentId);

            switch (commentStatusDTO.getCommentStatus()) {
                case ACCEPTED:
                    return new ResponseEntity<>(commentService.acceptComment(comment), HttpStatus.OK);
                case REJECTED:
                    return new ResponseEntity<>(commentService.cancelComment(comment), HttpStatus.OK);
                default:
                    return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }
}