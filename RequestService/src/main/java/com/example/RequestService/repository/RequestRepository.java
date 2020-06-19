package com.example.RequestService.repository;

import com.example.RequestService.domain.PaidState;
import com.example.RequestService.domain.Request;
import org.bouncycastle.cert.ocsp.Req;
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

    List<Request> findAllByUserEmail(String userEmail);

    List<Request> findAllByUserSentRequest(String email);

    List<Request> findAllByAdId(Long id);

    @Query("SELECT r FROM Request r WHERE r.paidState=3")
    List<Request> findAllPending();

    @Query("SELECT r FROM Request r WHERE r.paidState=2")
    List<Request> findAllReserved();
    
}
