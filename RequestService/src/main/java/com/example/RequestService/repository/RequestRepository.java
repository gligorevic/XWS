package com.example.RequestService.repository;

import com.example.RequestService.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.adId=:adId AND r.paidState=3 AND (:startDate BETWEEN r.startDate AND r.endDate OR :endDate BETWEEN r.startDate AND r.endDate)")
    List<Request> getRequestsForCanceling(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("adId") Long adId);

    
}
