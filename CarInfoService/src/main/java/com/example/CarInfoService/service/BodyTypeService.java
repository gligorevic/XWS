package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.repository.BodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeService {

    @Autowired
    private BodyTypeRepository bodyTypeRepository;

    public BodyType addBodyType(String bodyTypeName) {

        if(bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName) == null){
            BodyType bodyType = new BodyType(bodyTypeName);
            bodyTypeRepository.save(bodyType);
            return bodyType;
        }

        return null;
    }

    public List<BodyType> getAllBodyTypes(){

        return bodyTypeRepository.findAll();
    }

    public BodyType getBodyTypeByName(String bodyTypeName) {

        if(bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName) == null)
            return null;

        return bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName);

    }

}
