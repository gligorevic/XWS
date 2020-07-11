package com.example.FeedbackService.service;


import com.example.FeedbackService.MQConfig.ChannelBinding;
import com.example.FeedbackService.client.RequestClient;
import com.example.FeedbackService.domain.Comment;
import com.example.FeedbackService.domain.CommentStatus;
import com.example.FeedbackService.domain.Grade;
import com.example.FeedbackService.dto.*;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.repository.CommentRepository;
import com.example.FeedbackService.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private RequestClient requestClient;

    private MessageChannel email;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss");

    public GradeService(ChannelBinding channelBinding) {
        this.email = channelBinding.mailing();
    }

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

        Grade createdGrade = gradeRepository.save(grade);

        Message<EmailMessage> msg = MessageBuilder.withPayload(new EmailMessage(createdGrade.getAgentUsername(), "New grade!", "User " + createdGrade.getUsername() + " put a grade on advertisement " + requestDTO.getBrandName() + " " + requestDTO.getModelName() + " has been canceled by our administration " + " at " + simpleDateFormat.format(new Date()))).build();
        this.email.send(msg);

        return createdGrade;
    }

    public Grade addBundleGrade(GradeDTO gradeDTO, String auth) throws CustomException {
        RequestContainerDTO requestContainerDTO = requestClient.getRequestContainerById(gradeDTO.getRequestId(), auth).getBody();
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

        Grade createdGrade = gradeRepository.save(grade);

        Message<EmailMessage> msg = MessageBuilder.withPayload(new EmailMessage(createdGrade.getAgentUsername(), "New grade!", "User " + createdGrade.getUsername() + " put a grade on advertisement " + requestContainerDTO.getRequestDTOS().stream().map(requestDTO -> requestDTO.getBrandName() + " " + requestDTO.getModelName() + ", ").collect(Collectors.joining()) + " has been canceled by our administration " + " at " + simpleDateFormat.format(new Date()))).build();
        this.email.send(msg);

        return createdGrade;
    }


    public List<AverageGradeDTO> calculateAgentAverageGrade(List<String> userEmails) {
        return gradeRepository.calculateAverageGrades(userEmails);
    }

    public Long gradeAgent(com.baeldung.springsoap.gen.Grade grade){
        Grade saved = gradeRepository.save(new Grade(grade));
        return saved.getId();
    }

}
