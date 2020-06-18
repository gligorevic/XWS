package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAll();

    Advertisement findAdvertisementById(Long id);

    List<Advertisement> findAllByIdIn(Long[] adIds);

    @Query("SELECT a FROM Advertisement a WHERE a.car.id = :carId")
    Advertisement findAdvertisementByCarId(@Param("carId") Long carId);
}
