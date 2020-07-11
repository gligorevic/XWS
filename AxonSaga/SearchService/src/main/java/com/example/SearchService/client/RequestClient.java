package com.example.SearchService.client;

import com.example.SearchService.dto.ReservationPeriodDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "request")
public interface RequestClient {
    @PostMapping("/resPeriod")
    Boolean canselRequests(@RequestBody ReservationPeriodDTO reservationPeriodDTO);
}
