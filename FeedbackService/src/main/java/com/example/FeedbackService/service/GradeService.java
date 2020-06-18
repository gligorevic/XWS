package com.example.FeedbackService.service;


import com.example.FeedbackService.domain.Comment;
import com.example.FeedbackService.domain.CommentStatus;
import com.example.FeedbackService.domain.Grade;
import com.example.FeedbackService.dto.CommentDTO;
import com.example.FeedbackService.dto.GradeDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.repository.CommentRepository;
import com.example.FeedbackService.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }


    public Grade getGradeForRequest(Long requestId) throws CustomException {

        Grade grade = gradeRepository.findByRequestId(requestId);
        if(grade == null){
            throw new CustomException("No grade for this request", HttpStatus.BAD_REQUEST);
        }

        return grade;
    }

        public Grade add(GradeDTO gradeDTO) throws CustomException{

        Grade grade = new Grade(gradeDTO);
        if(grade == null)
            throw new CustomException("Couldn't create grade", HttpStatus.BAD_REQUEST);

        return gradeRepository.save(grade);
    }


}
