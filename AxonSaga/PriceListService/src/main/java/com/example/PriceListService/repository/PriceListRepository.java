package com.example.PriceListService.repository;

import com.example.PriceListService.domain.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    List<PriceList> findAllByUserEmail(String userEmail);

    Optional<PriceList> findById(Long id);

    @Query("SELECT DISTINCT p FROM PriceList p WHERE :from BETWEEN  p.validFrom AND p.validTo OR :to BETWEEN p.validFrom AND p.validTo")
    List<PriceList> checkIfDatesAreOverlaping(@Param("from")Date from, @Param("to") Date to);

    @Query("SELECT DISTINCT p FROM PriceList p WHERE :from BETWEEN  p.validFrom AND p.validTo OR :to BETWEEN p.validFrom AND p.validTo OR ( :from < p.validFrom AND :to > p.validTo )")
    List<PriceList> getPricelistsForCalculating(@Param("from")Date from, @Param("to") Date to);

}
