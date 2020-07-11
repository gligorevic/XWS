package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {

    @Query("SELECT DISTINCT g.grade FROM Grade g WHERE g.request.advertisement.id = :adId")
    List<Integer> getGradesForAdvertisement(@Param("adId") Long adId);

    @Query("SELECT DISTINCT g.grade FROM Grade g WHERE g.request.advertisement.car.id = :carId")
    List<Integer> getGradesForCar(@Param("carId") Long carId);

    Grade findByRequestId(Long id);

    @Query("SELECT g FROM Grade g WHERE g.inBundle = TRUE AND g.request.id = :id")
    Grade findByRequestContainerId(@Param("id") Long id);
}
