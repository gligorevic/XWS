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

    @Query("SELECT DISTINCT r FROM Request  r WHERE r.advertisement.id =?3 AND r.paidState=3 AND ((?1 BETWEEN r.startDate AND r.endDate) OR (?2 BETWEEN r.startDate AND r.endDate) OR (?1 <r.startDate AND  r.endDate < ?2))")
    List<Request> getRequestsForCanceling(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("adId") Long adId);

    @Query("SELECT DISTINCT r FROM Request r WHERE r.paidState=1 AND r.endDate < :today")
    List<Request> getRequestsPassedAndPaid(@Param("today") Date today);

    @Query("SELECT DISTINCT r FROM Request  r WHERE (r.creationDate BETWEEN :startDate AND :endDate)")
    List<Request> getRequestsByBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
