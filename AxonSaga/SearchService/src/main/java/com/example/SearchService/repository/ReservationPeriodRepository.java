package com.example.SearchService.repository;

import com.example.SearchService.domain.ReservationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationPeriodRepository extends JpaRepository<ReservationPeriod, Long> {

    List<ReservationPeriod> findReservationPeriodsByAdvertisementId(Long id);

    @Query("SELECT r FROM ReservationPeriod r WHERE r.advertisement.id = ?1 AND (r.startDate BETWEEN ?2 AND ?3 OR r.endDate BETWEEN ?2 AND ?3)")
    List<ReservationPeriod> checkIfOverlaping(@Param("advertismentId") Long advertismentId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
