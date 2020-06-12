package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.GearShiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GearShiftTypeRepository extends JpaRepository<GearShiftType, Long> {

    GearShiftType findGearShiftTypeById(Long id);

    GearShiftType findGearShiftTypeByGearShiftName(String gearShiftTypeName);

    List<GearShiftType> findAll();
}
