package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findRequestById(Long id);

    @Query("SELECT DISTINCT r FROM Request  r WHERE r.advertisement.id =?3 AND r.paidState=3 AND ((?1 BETWEEN r.startDate AND r.endDate) OR (?2 BETWEEN r.startDate AND r.endDate) OR (?1 <r.startDate AND  r.endDate < ?2))")
    List<Request> getRequestsForCanceling(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("adId") Long adId);

    @Query("SELECT DISTINCT r FROM Request r WHERE r.paidState=1 AND r.endDate < :today")
    List<Request> getRequestsPassed(@Param("today") Date today);

    @Query("SELECT DISTINCT r FROM Request  r WHERE (r.creationDate BETWEEN :startDate AND :endDate)")
    List<Request> getRequestsByBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT DISTINCT r FROM Request r WHERE r.paidState =2")
    List<Request> getReservedRequests();

    List<Request> findAllByAdvertisementAndUserSentRequest(Advertisement ad, User userSent);

    List<Request> findAllByAdvertisementId(Long id);

    @Query("SELECT r FROM Request r WHERE r.paidState=3 AND r.advertisement.id = ?1 AND NOT(r.id = ?2)")
    List<Request> findAllPendingByAdIdExceptOne(@Param("adId") Long adId, @Param("reqToExclude") Long reqToExclude);

    @Query("SELECT r FROM Request r WHERE r.advertisement.id IN ?1 AND r.id NOT IN ?2")
    List<Request> findAllByAllAdIds(@Param("adIds")List<Long> adIds, @Param("requestToBeExcluded")List<Long> requestToBeExcluded);

    @Query("SELECT r FROM Request r WHERE r.paidState=1 AND r.userSentRequest = :userEmail")
    List<Request> findAllPaid(@Param("userEmail") String userEmail);

    List<Request> findAllByUserSentRequestEmail(String email);



}
