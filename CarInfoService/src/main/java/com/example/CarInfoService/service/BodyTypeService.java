package com.example.CarInfoService.service;

import com.example.CarInfoService.domain.BodyType;
import com.example.CarInfoService.exception.CustomException;
import com.example.CarInfoService.repository.BodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeService {

    @Autowired
    private BodyTypeRepository bodyTypeRepository;

    public BodyType addBodyType(String bodyTypeName) throws CustomException {

        if(bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName) == null){
            BodyType bodyType = new BodyType(bodyTypeName);
            bodyTypeRepository.save(bodyType);
            return bodyType;
        }

        throw  new CustomException("Body type already exists", HttpStatus.BAD_REQUEST);
    }

    public List<BodyType> getAllBodyTypes(){

        return bodyTypeRepository.findAll();
    }

    public BodyType getBodyTypeByName(String bodyTypeName) throws CustomException {

        if(bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName) == null)
            throw  new CustomException("Body type doesn't exists", HttpStatus.BAD_REQUEST);

        return bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName);

    }

}
