package com.example.FeedbackService.repository;

import com.example.FeedbackService.domain.Comment;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByRequestId(Long id);

    @Query("SELECT c FROM Comment c WHERE c.requestId = :id AND c.inBundle = true")
    List<Comment> findAllByRequestContainerId(@Param("id") Long id);

    @Query("SELECT c FROM Comment c WHERE c.commentStatus=0")
    List<Comment> findAllPending();

    @Query("SELECT c FROM Comment c WHERE c.requestId=:requestId AND c.commentStatus=1")
    List<Comment> findAllByRequestIdAndAccepted(@Param("requestId") Long requestId);


}

