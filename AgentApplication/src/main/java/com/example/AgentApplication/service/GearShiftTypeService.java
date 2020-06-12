package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.GearShiftType;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.GearShiftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GearShiftTypeService {

    @Autowired
    private GearShiftTypeRepository gearShiftTypeRepository;

    public List<GearShiftType> getAllGearShiftTypes() {
        return gearShiftTypeRepository.findAll();
    }

    public GearShiftType getGearShiftTypeByName(String gearShiftTypeName) throws CustomException {
        if(gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftTypeName) == null)
            throw new CustomException("Gearshift type doesn't exists", HttpStatus.BAD_REQUEST);

        return gearShiftTypeRepository.findGearShiftTypeByGearShiftName(gearShiftTypeName);
    }
}
