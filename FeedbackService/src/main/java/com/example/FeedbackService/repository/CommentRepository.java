package com.example.FeedbackService.repository;

import com.example.FeedbackService.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {


}

