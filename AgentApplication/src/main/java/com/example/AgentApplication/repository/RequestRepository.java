package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT DISTINCT r FROM Request  r  WHERE r.advertisement.id =:adId AND  r.paidState=3 AND (:startDate BETWEEN r.startDate AND r.endDate OR :endDate BETWEEN r.startDate AND r.endDate)")
    List<Request> getRequestsForCanceling(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("adId") Long adId);
}
