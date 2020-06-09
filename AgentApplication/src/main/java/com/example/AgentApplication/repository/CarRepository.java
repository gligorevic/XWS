package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

     List<Car> findCarsByUserEmail(String userEmail);

     Car findCarById(Long id);
}
