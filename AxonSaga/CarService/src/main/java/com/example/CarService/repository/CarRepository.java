package com.example.CarService.repository;

import com.example.CarService.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

     List<Car> findCarsByUserAgentId(Long userAgentId);

}
