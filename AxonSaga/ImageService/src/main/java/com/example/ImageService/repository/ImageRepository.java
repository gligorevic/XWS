package com.example.ImageService.repository;


import com.example.ImageService.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT img.imageName FROM Image AS img WHERE img.carId = :carId")
    List<String> findImagesByCarId(@Param("carId") Long carId);
}
