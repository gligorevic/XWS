package com.example.FeedbackService.controller;

import com.example.FeedbackService.dto.RequestStatisticDTO;
import com.example.FeedbackService.exception.CustomException;
import com.example.FeedbackService.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PostMapping
    public ResponseEntity<?> getStatistic(@RequestBody List<RequestStatisticDTO> requests) {
        try {
            return new ResponseEntity<>(statisticService.getStatistic(requests), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
