package com.example.RequestService.client;

import com.example.RequestService.dto.AdvertisementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "search-service")
public interface AdvertisementClient {

    @PostMapping("/reqAdList")
    ResponseEntity<List<AdvertisementDTO>> getAdvertisementsByIds(@RequestBody List<Long> advertisementIds, @RequestHeader("Auth") String Auth);
}
