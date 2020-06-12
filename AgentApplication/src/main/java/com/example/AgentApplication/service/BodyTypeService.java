package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.BodyType;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.BodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeService {

    @Autowired
    private BodyTypeRepository bodyTypeRepository;


    public List<BodyType> getAllBodyTypes(){

        return bodyTypeRepository.findAll();
    }

    public BodyType getBodyTypeByName(String bodyTypeName) throws CustomException {

        if(bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName) == null)
            throw  new CustomException("Body type doesn't exists", HttpStatus.BAD_REQUEST);

        return bodyTypeRepository.findBodyTypeByBodyTypeName(bodyTypeName);

    }

}
