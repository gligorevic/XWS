package com.example.CarService.client;

import com.example.CarService.dto.AdvertisementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "search-service")
public interface AdvertisementClient {
    @PostMapping("/")
    ResponseEntity<?> addAdvertisement(@RequestHeader("Auth") String Auth, @RequestBody AdvertisementDTO advertisementDTO);

}
