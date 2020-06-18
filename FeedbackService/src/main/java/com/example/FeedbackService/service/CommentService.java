package com.example.FeedbackService.service;

import com.example.FeedbackService.domain.Comment;
import com.example.FeedbackService.domain.CommentStatus;
import com.example.FeedbackService.dto.CommentDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


    public List<Comment> getAllCommentsForRequest(Long requestId) throws CustomException {

        List<Comment> commentList = commentRepository.findAllByRequestId(requestId);
        if(commentList == null){
            throw new CustomException("No comments for this request", HttpStatus.BAD_REQUEST);
        }

        if(commentList.isEmpty())
            return new ArrayList<>();

        return commentList;
    }

    public Comment add(CommentDTO commentDTO) throws CustomException{

        Comment comment = new Comment(commentDTO);
        if(comment == null){
            throw new CustomException("Couldn't create comment", HttpStatus.BAD_REQUEST);
        }
        return commentRepository.save(comment);
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
