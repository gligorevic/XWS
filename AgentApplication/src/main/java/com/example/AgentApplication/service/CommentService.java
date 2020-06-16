package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Comment;
import com.example.AgentApplication.dto.CommentDTO;
import com.example.AgentApplication.dto.SimpleAdvertisementDTO;
import com.example.AgentApplication.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDTO> getCommentsByAdvertisementId(Long id){
        return commentRepository.getCommentsForAdvertisement(id).stream().map(comment -> new CommentDTO(comment)).collect(Collectors.toList());
    }
}
