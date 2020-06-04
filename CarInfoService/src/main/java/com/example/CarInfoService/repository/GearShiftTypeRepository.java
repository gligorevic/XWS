package com.example.CarInfoService.repository;

import com.example.CarInfoService.domain.GearShiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GearShiftTypeRepository extends JpaRepository<GearShiftType, Long> {

    GearShiftType findGearShiftTypeById(Long id);

    GearShiftType findGearShiftTypeByGearShiftName(String gearShiftTypeName);

    List<GearShiftType> findAll();
}
