package com.example.CarInfoService.repository;

import com.example.CarInfoService.domain.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    FuelType findFuelTypeByFuelTypeName(String fuelTypeName);

    FuelType findFuelTypeById(Long id);
}
