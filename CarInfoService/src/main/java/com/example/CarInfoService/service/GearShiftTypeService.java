package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.GearShiftType;
import com.example.CarInfoService.repository.GearShiftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GearShiftTypeService {

    @Autowired
    private GearShiftTypeRepository gearShiftTypeRepository;

    public GearShiftType add(String gearShiftTypeName) {

        if(gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftTypeName) == null){
            GearShiftType gearShiftType = new GearShiftType(gearShiftTypeName);
            gearShiftTypeRepository.save(gearShiftType);
            return gearShiftType;
        }

        return null;
    }

    public List<GearShiftType> getAllGearShiftTypes() {
        return gearShiftTypeRepository.findAll();
    }

    public GearShiftType getGearShiftTypeByName(String gearShiftTypeName) {
        if(gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftTypeName) == null)
            return null;

        return gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftTypeName);
    }
}
