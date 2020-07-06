package com.example.FeedbackService.service;

import com.example.FeedbackService.client.RequestClient;
import com.example.FeedbackService.domain.Comment;
import com.example.FeedbackService.domain.CommentStatus;
import com.example.FeedbackService.dto.CommentDTO;
import com.example.FeedbackService.dto.RequestContainerDTO;
import com.example.FeedbackService.dto.RequestDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RequestClient requestClient;


    public List<Comment> getAllCommentsForRequest(Long requestId, Authentication authentication, String auth) throws CustomException {
        RequestDTO requestDTO = getRequestById(requestId, auth);
        String userEmail = (String) authentication.getPrincipal();
        if(!userEmail.equals(requestDTO.getUserSentRequest()) && !userEmail.equals(requestDTO.getUserEmail())) throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);

        List<Comment> commentList = commentRepository.findAllByRequestId(requestDTO.getId());
        if(commentList.isEmpty()) return new ArrayList<>();

        return getComments(requestDTO.getUserSentRequest(), userEmail, commentList);
    }

    public List<Comment> getAllCommentsForBundleRequest(Long requestId, Authentication authentication, String auth) throws CustomException {
        RequestContainerDTO requestContainerDTO = requestClient.getRequestContainerById(requestId, auth).getBody();
        String userEmail = (String) authentication.getPrincipal();
        if(!userEmail.equals(requestContainerDTO.getUserSentRequest()) && !userEmail.equals(requestContainerDTO.getUserEmail())) throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);

        List<Comment> commentList = commentRepository.findAllByRequestContainerId(requestContainerDTO.getId());
        if(commentList.isEmpty()) return new ArrayList<>();

        return getComments(requestContainerDTO.getUserSentRequest(), userEmail, commentList);
    }

    private List<Comment> getComments(String userSentRequest, String userEmail, List<Comment> commentList) {
        if(userEmail.equals(userSentRequest)){
            if(commentList.size() == 1) {
                return commentList;
            } else {
                return commentList.get(1).getCommentStatus() == CommentStatus.ACCEPTED ? commentList : Arrays.asList(commentList.get(0));
            }
        } else if(commentList.size() == 1) {
            return commentList.get(0).getCommentStatus() == CommentStatus.ACCEPTED ? commentList : new ArrayList<>();
        }
        return commentList;
    }

    public Comment add(CommentDTO commentDTO, String auth) throws CustomException{

        RequestDTO requestDTO = getRequestById(commentDTO.getRequestId(), auth);

        if(requestDTO == null){
            throw new CustomException("No request with that id", HttpStatus.BAD_REQUEST);
        }
        if(!requestDTO.getUserSentRequest().equals(commentDTO.getUsername()) && !requestDTO.getUserEmail().equals(commentDTO.getUsername()))
            throw new CustomException("You can comment only your requests", HttpStatus.UNAUTHORIZED);

        List<Comment> comments = commentRepository.findAllByRequestId(commentDTO.getRequestId());
        if(comments.size() > 0){
            for(Comment comment : comments){
                if(comment.getUsername().equals(commentDTO.getUsername()))
                    throw new CustomException("You have already submitted a comment", HttpStatus.BAD_REQUEST);
            }
        }
        Comment comment = new Comment(commentDTO);
        if(comment == null){
            throw new CustomException("Couldn't create comment", HttpStatus.BAD_REQUEST);
        }

        Date now = new Date();

        if(now.compareTo(requestDTO.getFreeTo()) < 0){
            throw new CustomException("Sorry, this rental date has not expired yet", HttpStatus.BAD_REQUEST);
        }

        comment.setCreationDate(now);
        return commentRepository.save(comment);
    }


    public Comment addBundleComment(CommentDTO commentDTO, String auth) throws CustomException {
        RequestContainerDTO requestContainerDTO = requestClient.getRequestContainerById(commentDTO.getRequestId(), auth).getBody();

        if(requestContainerDTO == null){
            throw new CustomException("No request container with that id", HttpStatus.BAD_REQUEST);
        }
        if(!requestContainerDTO.getUserSentRequest().equals(commentDTO.getUsername()) && !requestContainerDTO.getUserEmail().equals(commentDTO.getUsername()))
            throw new CustomException("You can comment only your requests", HttpStatus.UNAUTHORIZED);

        List<Comment> comments = commentRepository.findAllByRequestId(commentDTO.getRequestId());
        if(comments.size() > 0){
            for(Comment comment : comments){
                if(comment.getUsername().equals(commentDTO.getUsername()))
                    throw new CustomException("You have already submitted a comment", HttpStatus.BAD_REQUEST);
            }
        }
        Comment comment = new Comment(commentDTO);
        if(comment == null){
            throw new CustomException("Couldn't create comment", HttpStatus.BAD_REQUEST);
        }

        Date now = new Date();

        for(RequestDTO requestDTO : requestContainerDTO.getRequestDTOS()){
            if(now.compareTo(requestDTO.getFreeTo()) < 0){
                throw new CustomException("Sorry, this rental date has not expired yet", HttpStatus.BAD_REQUEST);
            }
        }

        comment.setCreationDate(now);
        return commentRepository.save(comment);
    }

    private RequestDTO getRequestById(Long requestId, String auth) {

        return requestClient.getRequestById(requestId, auth).getBody();
    }

    public Comment getCommentById(Long id) throws CustomException{
        Comment comment = commentRepository.getOne(id);
        if (comment == null){
            throw new CustomException("No comment with that id", HttpStatus.BAD_REQUEST);
        }

        return comment;
    }

    public Comment acceptComment(Comment comment) throws CustomException{

        if(comment.getCommentStatus() != CommentStatus.PENDING){
            throw new CustomException("Comment status has already been changed", HttpStatus.BAD_REQUEST);
        }
        comment.setCommentStatus(CommentStatus.ACCEPTED);
        return commentRepository.save(comment);
    }

    public Comment cancelComment(Comment comment) throws CustomException{
        if(comment.getCommentStatus() != CommentStatus.PENDING){
            throw new CustomException("Comment status has already been changed", HttpStatus.BAD_REQUEST);
        }
        comment.setCommentStatus(CommentStatus.REJECTED);
        return commentRepository.save(comment);


    }

    public List<Comment> getAllCommentsForAdmin() throws CustomException{

        return commentRepository.findAllPending();
    }

}
