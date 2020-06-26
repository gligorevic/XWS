package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.GearShiftType;
import com.example.CarInfoService.domain.Model;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.repository.GearShiftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public GearShiftType editGearShiftType(Long gearTypeId, String gearTypeName) throws CustomException {

        GearShiftType gearShiftType = gearShiftTypeRepository.findGearShiftTypeById(gearTypeId);
        if(gearShiftType == null){
            throw new CustomException("Gearshift type doesn't exists", HttpStatus.BAD_REQUEST);
        }
        gearShiftType.setGearShiftName(gearTypeName);

        return gearShiftTypeRepository.save(gearShiftType);
    }

    public GearShiftType getGearShiftTypeById(Long gearTypeId) throws CustomException{
        Optional<GearShiftType> gearShiftType = gearShiftTypeRepository.findById(gearTypeId);
        if(gearShiftType == null)
            throw new CustomException("Gearshift type with that id doesn't exists", HttpStatus.BAD_REQUEST);

        return gearShiftType.get();


    }
}
