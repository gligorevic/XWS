package com.example.SearchService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "image-service")
public interface ImageClient {
    @GetMapping("/{carId}")
    List<String> getCarImagesUrl(@PathVariable("carId") Long carId);
}
