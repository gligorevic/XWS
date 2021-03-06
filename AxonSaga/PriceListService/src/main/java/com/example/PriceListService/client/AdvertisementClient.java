package com.example.PriceListService.client;

import com.example.PriceListService.dto.AdvertisementCalculatingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "search-service")
public interface AdvertisementClient {

    @PostMapping("/simple/{id}")
    ResponseEntity<AdvertisementCalculatingDTO> getAdvertisemenById(@PathVariable("id") Long id, @RequestHeader("Auth") String Auth);

    @PostMapping("/{id}/priceKm/{priceKm}/priceDay/{priceDay}")
    ResponseEntity<AdvertisementCalculatingDTO> setPriceForAdvertisement(@PathVariable("id") Long id, @PathVariable("priceKm") Integer priceKm, @PathVariable("priceDay") Integer priceDay, @RequestHeader("Auth") String Auth);

}
