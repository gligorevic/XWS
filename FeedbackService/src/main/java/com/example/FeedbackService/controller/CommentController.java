package com.example.FeedbackService.controller;

import com.example.FeedbackService.domain.Comment;
import com.example.FeedbackService.dto.CommentDTO;
import com.example.FeedbackService.dto.CommentStatusDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(CommentController.class);


    @GetMapping("/comment/{reqId}")
    public ResponseEntity<?> getAllCommentsForRequest(@PathVariable("reqId") String reqId, Authentication authentication, @RequestHeader("Auth") String auth) {
        try {
            return new ResponseEntity<>(commentService.getAllCommentsForRequest(Long.parseLong(reqId), authentication, auth), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAuthority('RENT_COMMENTING')")
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO, Authentication authentication, @RequestHeader("Auth") String auth) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            if (!userEmail.equals(commentDTO.getUsername())) {
                throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Comment comment = commentService.add(commentDTO, auth);
            log.info("User {} successfully added comment for request {}", bCryptPasswordEncoder.encode(userEmail), bCryptPasswordEncoder.encode(comment.getRequestId().toString()));

            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/comment/{commentId}")
    @PreAuthorize("hasAuthority('RENT_COMMENT_APPROVING')")
    public ResponseEntity<?> changeCommentStatus(@RequestBody CommentStatusDTO commentStatusDTO,
                                                 @PathVariable("commentId") Long commentId, Authentication authentication) {
        String userEmail = (String) authentication.getPrincipal();
        try {
            Comment comment = commentService.getCommentById(commentId);

            switch (commentStatusDTO.getCommentStatus()) {
                case ACCEPTED:
                    Comment commentToAccept = commentService.acceptComment(comment);
                    log.info("Successfully accepted comment {} by user {}", bCryptPasswordEncoder.encode(commentToAccept.getId().toString()), bCryptPasswordEncoder.encode(userEmail));
                    return new ResponseEntity<>(commentToAccept, HttpStatus.OK);
                case REJECTED:
                    Comment commentToReject = commentService.acceptComment(comment);
                    log.info("Successfully rejected comment {} by user {}", bCryptPasswordEncoder.encode(commentToReject.getId().toString()), bCryptPasswordEncoder.encode(userEmail));
                    return new ResponseEntity<>(commentToReject, HttpStatus.OK);
                default:
                    return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (CustomException e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            log.error("{}. Action initiated by {}.", e.getMessage(), bCryptPasswordEncoder.encode(userEmail));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }
}