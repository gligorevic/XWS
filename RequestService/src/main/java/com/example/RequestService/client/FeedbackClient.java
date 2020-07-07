package com.example.RequestService.client;


import com.example.RequestService.domain.Request;
import com.example.RequestService.dto.FeedbackStatisticDTO;
import com.example.RequestService.dto.RequestStatisticDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "feedback")
public interface FeedbackClient {

    @PostMapping("/statistic")
    ResponseEntity<List<FeedbackStatisticDTO>> getFeedbackStatistic(@RequestBody List<RequestStatisticDTO> requests, @RequestHeader("Auth") String Auth);

}
