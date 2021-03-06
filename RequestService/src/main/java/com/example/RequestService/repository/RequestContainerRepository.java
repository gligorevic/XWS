package com.example.RequestService.repository;

import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequestContainerRepository extends JpaRepository<RequestContainer, Long> {

    @Query("SELECT DISTINCT r.boundleList FROM RequestContainer r  LEFT JOIN r.boundleList boundle WHERE boundle.adId =:adId AND  boundle.paidState=3 AND (:startDate BETWEEN boundle.startDate AND boundle.endDate OR :endDate BETWEEN boundle.startDate AND boundle.endDate)")
    List<Request> getRequestsFromBundle( @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("adId") Long adId);

    @Query("SELECT DISTINCT r.boundleList FROM RequestContainer r LEFT JOIN r.boundleList boundle WHERE boundle.id =:requestId")
    List<Request> getRequestsInBundle(@Param("requestId") Long requestId);

}
