package com.example.FeedbackService.service;

import com.example.FeedbackService.dto.FeedbackStatisticDTO;
import com.example.FeedbackService.dto.RequestStatisticDTO;
import com.example.FeedbackService.repository.CommentRepository;
import com.example.FeedbackService.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public List<FeedbackStatisticDTO> getStatistic(List<RequestStatisticDTO> requests){
        List<FeedbackStatisticDTO> list = new ArrayList<>();
        HashMap<Long, List<Long>> map = new HashMap<>();
        requests.stream().forEach(request -> {
            if(!map.containsKey(request.getAdId()))
                map.put(request.getAdId(), new ArrayList<>());

            map.get(request.getAdId()).add(request.getId());
        });
        map.keySet().stream().forEach(aLong -> {
            FeedbackStatisticDTO feedbackStatisticDTO = new FeedbackStatisticDTO(aLong);
            feedbackStatisticDTO.setNumberOfComments(commentRepository.findAllByRequestIdIn(map.get(aLong)).size());
            feedbackStatisticDTO.setAverageGrade(gradeRepository.calculateAverageGradeAdvertisement(map.get(aLong)));

            list.add(feedbackStatisticDTO);
        });
        return list;
    }


}
