package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAll();

    @Query("SELECT DISTINCT c FROM Comment c WHERE c.commentStatus=1 AND c.request.advertisement.id = :adId")
    List<Comment> getCommentsForAdvertisement(@Param("adId") Long adId);

    @Query("SELECT DISTINCT c FROM Comment c WHERE c.commentStatus=1 AND c.request.advertisement.car.id = :carId")
    List<Comment> getCommentsForCar(@Param("carId") Long carId);

    List<Comment> findAllByRequestId(Long id);

    @Query("SELECT c FROM Comment c WHERE c.request.id = :id AND c.request.inBundle = true")
    List<Comment> findAllByRequestContainerId(@Param("id") Long id);

}

