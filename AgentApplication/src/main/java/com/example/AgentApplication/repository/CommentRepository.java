package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT DISTINCT c FROM Comment c WHERE c.commentStatus=1 AND c.request.advertisement.id = :adId")
    List<Comment> getCommentsForAdvertisement(@Param("adId") Long adId);

}

