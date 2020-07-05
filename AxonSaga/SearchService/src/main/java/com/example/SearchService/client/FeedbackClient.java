package com.example.SearchService.client;

import com.example.SearchService.dto.AverageGradeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "feedback")
public interface FeedbackClient {
    @PostMapping("/grade/average")
    List<AverageGradeDTO> getAverageRates(@RequestBody List<String> userEmails);
}
