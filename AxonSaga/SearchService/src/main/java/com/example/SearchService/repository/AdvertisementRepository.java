package com.example.SearchService.repository;

import com.example.SearchService.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAll();

    List<Advertisement> findAdvertisementsByUserEmail(String userEmail);

    Advertisement findAdvertisementById(Long id);

    List<Advertisement> findAllByIdIn(Long[] adIds);

    Advertisement findAdvertisementByCarId(Long carId);
}
