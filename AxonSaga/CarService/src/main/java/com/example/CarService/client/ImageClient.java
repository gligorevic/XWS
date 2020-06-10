package com.example.CarService.client;

import com.example.CarService.dto.AdvertisementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "image-service")
public interface ImageClient {
    @GetMapping("/{carId}")
    List<String> getCarImagesUrl(@PathVariable("carId") Long carId);
}
