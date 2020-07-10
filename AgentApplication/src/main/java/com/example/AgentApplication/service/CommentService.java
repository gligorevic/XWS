package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Comment;
import com.example.AgentApplication.dto.CommentDTO;
import com.example.AgentApplication.dto.RequestContainerDTO;
import com.example.AgentApplication.dto.RequestDTO;
import com.example.AgentApplication.enumeration.CommentStatus;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.CommentRepository;
import com.example.AgentApplication.repository.RequestContainerRepository;
import com.example.AgentApplication.repository.RequestRepository;
import com.example.AgentApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestContainerRepository requestContainerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CommentDTO> getCommentsByAdvertisementId(Long id){
        return commentRepository.getCommentsForAdvertisement(id).stream().map(comment -> new CommentDTO(comment)).collect(Collectors.toList());
    }

    public Comment addComment(CommentDTO commentDTO){
        Comment comment = new Comment(commentDTO);
        comment.setCommentStatus(CommentStatus.ACCEPTED);
        comment.setUser(userRepository.findByEmail("agent@gmail.com")); //za sada samo agent moze da odgovori na komentar
        comment.setRequest(requestRepository.findRequestById(commentDTO.getRequestId()));
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForRequest(Long requestId, Authentication authentication) throws CustomException {
        RequestDTO requestDTO = new RequestDTO(requestRepository.findById(requestId).get());
        String userEmail = (String) authentication.getPrincipal();
        if(!userEmail.equals(requestDTO.getUserSentRequest()) && !userEmail.equals(requestDTO.getUserEmail())) throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);

        List<Comment> commentList = commentRepository.findAllByRequestId(requestDTO.getId());
        if(commentList.isEmpty()) return new ArrayList<>();

        return getComments(requestDTO.getUserSentRequest(), userEmail, commentList);
    }

    public List<Comment> getAllCommentsForBundleRequest(Long requestId, Authentication authentication) throws CustomException {
        RequestContainerDTO requestContainerDTO = new RequestContainerDTO(requestContainerRepository.findById(requestId).get());
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

    public Comment add(CommentDTO commentDTO) throws CustomException{

        RequestDTO requestDTO = new RequestDTO(requestRepository.findById(commentDTO.getRequestId()).get());

        if(requestDTO == null){
            throw new CustomException("No request with that id", HttpStatus.BAD_REQUEST);
        }
        if(!requestDTO.getUserSentRequest().equals(commentDTO.getUserEmail()) && !requestDTO.getUserEmail().equals(commentDTO.getUserEmail()))
            throw new CustomException("You can comment only your requests", HttpStatus.UNAUTHORIZED);

        List<Comment> comments = commentRepository.findAllByRequestId(commentDTO.getRequestId());
        if(comments.size() > 0){
            for(Comment comment : comments){
                if(comment.getUser().getEmail().equals(commentDTO.getUserEmail()))
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
        comment.setInBundle(false);
        comment.setRequest(requestRepository.findById(commentDTO.getRequestId()).get());
        comment.setUser(userRepository.findByEmail(commentDTO.getUserEmail()));
        comment.setCommentStatus(CommentStatus.ACCEPTED);
        return commentRepository.save(comment);
    }

    public Comment addBundleComment(CommentDTO commentDTO) throws CustomException {
        RequestContainerDTO requestContainerDTO = new RequestContainerDTO(requestContainerRepository.findById(commentDTO.getRequestId()).get());

        if (requestContainerDTO == null) {
            throw new CustomException("No request container with that id", HttpStatus.BAD_REQUEST);
        }
        if (!requestContainerDTO.getUserSentRequest().equals(commentDTO.getUserEmail()) && !requestContainerDTO.getUserEmail().equals(commentDTO.getUserEmail()))
            throw new CustomException("You can comment only your requests", HttpStatus.UNAUTHORIZED);

        List<Comment> comments = commentRepository.findAllByRequestId(commentDTO.getRequestId());
        if (comments.size() > 0) {
            for (Comment comment : comments) {
                if (comment.getUser().getEmail().equals(commentDTO.getUserEmail()))
                    throw new CustomException("You have already submitted a comment", HttpStatus.BAD_REQUEST);
            }
        }
        Comment comment = new Comment(commentDTO);
        if (comment == null) {
            throw new CustomException("Couldn't create comment", HttpStatus.BAD_REQUEST);
        }

        Date now = new Date();

        for (RequestDTO requestDTO : requestContainerDTO.getRequestDTOS()) {
            if (now.compareTo(requestDTO.getFreeTo()) < 0) {
                throw new CustomException("Sorry, this rental date has not expired yet", HttpStatus.BAD_REQUEST);
            }
        }

        comment.setCreationDate(now);
        comment.setInBundle(true);
        comment.setRequest(requestContainerRepository.findById(commentDTO.getRequestId()).get().getBoundleList().get(0));
        comment.setUser(userRepository.findByEmail(commentDTO.getUserEmail()));
        comment.setCommentStatus(CommentStatus.ACCEPTED);
        return commentRepository.save(comment);
    }
}
