package com.example.FeedbackService.client;

import com.example.FeedbackService.dto.RequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "request")
public interface RequestClient {

    @PostMapping("/feedRequest")
    ResponseEntity<RequestDTO> getRequestById(@RequestBody Long requestId, @RequestHeader("Auth") String Auth);

}
