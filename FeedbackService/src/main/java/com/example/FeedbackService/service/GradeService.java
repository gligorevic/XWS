package com.example.FeedbackService.service;


import com.example.FeedbackService.client.RequestClient;
import com.example.FeedbackService.domain.Comment;
import com.example.FeedbackService.domain.CommentStatus;
import com.example.FeedbackService.domain.Grade;
import com.example.FeedbackService.dto.AverageGradeDTO;
import com.example.FeedbackService.dto.CommentDTO;
import com.example.FeedbackService.dto.GradeDTO;
import com.example.FeedbackService.dto.RequestDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.repository.CommentRepository;
import com.example.FeedbackService.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private RequestClient requestClient;


    public int getGradeForRequest(Long requestId) throws CustomException {

        Grade grade = gradeRepository.findByRequestId(requestId);
        if (grade == null) {
            return 0;
        }

        return grade.getGrade();
    }

    public Grade add(GradeDTO gradeDTO, String auth) throws CustomException {

        RequestDTO requestDTO = requestClient.getRequestById(gradeDTO.getRequestId(), auth).getBody();

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

        return gradeRepository.save(grade);
    }


    public List<AverageGradeDTO> calculateAgentAverageGrade(List<String> userEmails) {
        return gradeRepository.calculateAverageGrades(userEmails);
    }
}
