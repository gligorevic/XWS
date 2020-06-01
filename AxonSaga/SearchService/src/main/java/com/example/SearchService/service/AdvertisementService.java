package com.example.SearchService.service;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Advertisement addAdvertisement(AdvertisementDTO dto){
        //za sada se za obe cene stavlja ona koju je user definisao jer jos nemamo cenovnik
        Advertisement advertisement = new Advertisement(dto.getCarId(), dto.getKmRestriction(), dto.getPrice(), dto.getPrice(), dto.getBrandName(), dto.getModelName(), dto.getGearShiftName(), dto.getFuelTypeName(), dto.getBodyName(), dto.getKmPassed(), dto.getNumberChildSeats(), dto.getCollisionDamage(), dto.getUserAgentId());
        return advertisementRepository.save(advertisement);
    }

}
