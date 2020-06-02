package com.example.SearchService.repository;

import com.example.SearchService.domain.ReservationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationPeriodRepository extends JpaRepository<ReservationPeriod, Long> {

    List<ReservationPeriod> findReservationPeriodsByAdvertisementId(Long id);
}
