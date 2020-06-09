package com.example.SearchService.service;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.domain.City;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.dto.SimpleAdvertisementDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.repository.AdvertisementRepository;
import com.example.SearchService.repository.CityRepository;
import com.example.SearchService.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CalendarService calendarService;

    public Advertisement addAdvertisement(AdvertisementDTO dto) throws CustomException, Exception{
        if(dto.getCityName() == null)
            throw new Exception("City not found");

        City city = cityRepository.findByName(dto.getCityName());

        if(city == null)
            throw new Exception("City not found");

        dto.setFreeFrom(calendarService.getMidnightStartDate(dto.getFreeFrom()).getTime());
        dto.setFreeTo(calendarService.getMidnightEndDate(dto.getFreeTo()).getTime());

        Advertisement advertisement = new Advertisement(dto);
        advertisement.setRentingCityLocation(city);

        if(advertisementRepository.findAdvertisementByCarId(dto.getCarId()) != null)
            throw new CustomException("Advertisement already exists", HttpStatus.NOT_ACCEPTABLE);

        return advertisementRepository.save(advertisement);
    }

    public List<Advertisement> getAdvertisementsByUserId(String email){
        return advertisementRepository.findAdvertisementsByUserEmail(email);
    }

    public List<SimpleAdvertisementDTO> getAllAdvertisements(){
        return advertisementRepository.findAll().stream().map(advertisement -> new SimpleAdvertisementDTO(advertisement)).collect(Collectors.toList());
    }

    public AdvertisementDTO getAdvertisementById(Long id){
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        List<String> images = imageRepository.findImagesByCarId(advertisement.getCarId());
        return new AdvertisementDTO(advertisement, images);
    }
}
