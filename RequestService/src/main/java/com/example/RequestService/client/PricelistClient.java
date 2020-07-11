package com.example.RequestService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "pricelist-service")
public interface PricelistClient {

    @PostMapping("added-price/check")
    ResponseEntity<Boolean> checkIfNotPaid(@RequestHeader("Auth") String Auth);

}
