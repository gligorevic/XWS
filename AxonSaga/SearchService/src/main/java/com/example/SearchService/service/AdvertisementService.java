package com.example.SearchService.service;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Advertisement addAdvertisement(AdvertisementDTO dto){
        //za sada se za obe cene stavlja ona koju je user definisao jer jos nemamo cenovnik
        Advertisement advertisement = new Advertisement(dto);
        return advertisementRepository.save(advertisement);
    }

    public List<Advertisement> getAdvertisementsByUserId(String email){
        return advertisementRepository.findAdvertisementsByUserEmail(email);
    }

    public List<Advertisement> getAllAdvertisements(){

        System.out.println("Usao u servis ");
        return advertisementRepository.findAll();
    }

}
