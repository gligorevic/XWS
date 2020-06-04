package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.GearShiftType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.repository.GearShiftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GearShiftTypeService {

    @Autowired
    private GearShiftTypeRepository gearShiftTypeRepository;

    public GearShiftType add(String gearShiftTypeName) throws CustomException {

        if(gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftTypeName) == null){
            GearShiftType gearShiftType = new GearShiftType(gearShiftTypeName);
            gearShiftTypeRepository.save(gearShiftType);
            return gearShiftType;
        }

        throw new CustomException("Gearshift type already exists", HttpStatus.BAD_REQUEST);
    }

    public List<GearShiftType> getAllGearShiftTypes() {
        return gearShiftTypeRepository.findAll();
    }

    public GearShiftType getGearShiftTypeByName(String gearShiftTypeName) throws CustomException {
        if(gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftTypeName) == null)
            throw new CustomException("Gearshift type doesn't exists", HttpStatus.BAD_REQUEST);

        return gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftTypeName);
    }
}
