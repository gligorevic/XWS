package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    FuelType findFuelTypeByFuelTypeName(String fuelTypeName);

    FuelType findFuelTypeById(Long id);
}
