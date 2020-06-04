package com.example.SearchService.service;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Advertisement addAdvertisement(AdvertisementDTO dto){
        //za sada se za obe cene stavlja ona koju je user definisao jer jos nemamo cenovnik
        dto.setFreeFrom(getMidnightStartDate(dto.getFreeFrom()).getTime());
        dto.setFreeTo(getMidnightEndDate(dto.getFreeTo()).getTime());
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

    public Advertisement getAdvertisementById(Long id){
        return advertisementRepository.findAdvertisementById(id);
    }

    private Calendar getMidnightEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        return calendar;
    }

    private Calendar getMidnightStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar;
    }

}
