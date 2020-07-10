package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Grade;
import com.example.AgentApplication.dto.GradeDTO;
import com.example.AgentApplication.dto.RequestContainerDTO;
import com.example.AgentApplication.dto.RequestDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.GradeRepository;
import com.example.AgentApplication.repository.RequestContainerRepository;
import com.example.AgentApplication.repository.RequestRepository;
import com.example.AgentApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestContainerRepository requestContainerRepository;

    @Autowired
    private UserRepository userRepository;

    public int getGradeForRequest(Long requestId) {

        Grade grade = gradeRepository.findByRequestId(requestId);
        if (grade == null) {
            return 0;
        }

        return grade.getGrade();
    }


    public int getGradeForBundleRequest(Long containerId) {
        Grade grade = gradeRepository.findByRequestContainerId(containerId);
        if (grade == null) {
            return 0;
        }

        return grade.getGrade();
    }

    public Grade add(GradeDTO gradeDTO) throws CustomException {

        RequestDTO requestDTO = new RequestDTO(requestRepository.findById(gradeDTO.getRequestId()).get());
        if (requestDTO == null) {
            throw new CustomException("No request with that id", HttpStatus.BAD_REQUEST);
        }

        Date now = new Date();
        if (now.compareTo(requestDTO.getFreeTo()) < 0) {
            throw new CustomException("Sorry, this rental date has not expired yet", HttpStatus.BAD_REQUEST);
        }

        Grade grade = new Grade(gradeDTO);
        if (grade == null)
            throw new CustomException("Couldn't create grade", HttpStatus.BAD_REQUEST);

        grade.setRequest(requestRepository.findById(gradeDTO.getRequestId()).get());
        grade.setUser(userRepository.findByEmail(gradeDTO.getUsername()));
        return gradeRepository.save(grade);
    }

    public Grade addBundleGrade(GradeDTO gradeDTO) throws CustomException {
        RequestContainerDTO requestContainerDTO = new RequestContainerDTO(requestContainerRepository.findById(gradeDTO.getRequestId()).get());
        if (requestContainerDTO == null)
            throw new CustomException("No bundle request with that id", HttpStatus.BAD_REQUEST);

        Date now = new Date();

        for(RequestDTO requestDTO : requestContainerDTO.getRequestDTOS()){
            if (now.compareTo(requestDTO.getFreeTo()) < 0) {
                throw new CustomException("Sorry, one of your requests in bundle didn't expire", HttpStatus.BAD_REQUEST);
            }
        }

        Grade grade = new Grade(gradeDTO);
        if (grade == null)
            throw new CustomException("Couldn't create grade", HttpStatus.BAD_REQUEST);

        grade.setRequest(requestContainerRepository.findById(gradeDTO.getRequestId()).get().getBoundleList().get(0));
        grade.setUser(userRepository.findByEmail(gradeDTO.getUsername()));
        return gradeRepository.save(grade);
    }
}
