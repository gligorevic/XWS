package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Comment;
import com.example.AgentApplication.dto.CommentDTO;
import com.example.AgentApplication.dto.SimpleAdvertisementDTO;
import com.example.AgentApplication.enumeration.CommentStatus;
import com.example.AgentApplication.repository.CommentRepository;
import com.example.AgentApplication.repository.RequestRepository;
import com.example.AgentApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RequestRepository requestRepository;

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
}
