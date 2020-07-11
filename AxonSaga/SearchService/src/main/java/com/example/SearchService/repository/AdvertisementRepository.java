package com.example.SearchService.repository;

import com.example.SearchService.domain.Advertisement;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a where a.freeTo >= ?1")
    List<Advertisement> findAllAdsValidTwoDaysFromNow(@Param("twoDaysFromNow") Date twoDaysFromNow);

    List<Advertisement> findAll();

    List<Advertisement> findAdvertisementsByUserEmail(String userEmail);

    Advertisement findAdvertisementById(Long id);

    List<Advertisement> findAllByIdIn(Long[] adIds);

    Advertisement findAdvertisementByCarId(Long carId);
}
