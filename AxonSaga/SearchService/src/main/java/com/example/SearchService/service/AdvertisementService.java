package com.example.SearchService.service;

import com.baeldung.springsoap.gen.GetAdvertisementRequest;
import com.example.SearchService.client.FeedbackClient;
import com.example.SearchService.client.ImageClient;
import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.domain.City;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.dto.AdvertisementStatisticDTO;
import com.example.SearchService.dto.AverageGradeDTO;
import com.example.SearchService.dto.SimpleAdvertisementDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.repository.AdvertisementRepository;
import com.example.SearchService.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private FeedbackClient feedbackClient;

    @Autowired
    private ImageClient imageClient;

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

    public List<AdvertisementStatisticDTO> getAdvertisementsByUserIdStatistic(String email){
        List<AdvertisementStatisticDTO> list = new ArrayList<>();
        advertisementRepository.findAdvertisementsByUserEmail(email).stream().forEach(advertisement -> list.add(new AdvertisementStatisticDTO(advertisement)));
        return list;
    }

    public List<SimpleAdvertisementDTO> getAllAdvertisements(){
        return getAverageRates(advertisementRepository.findAll());
    }

    private List<SimpleAdvertisementDTO> getAverageRates(List<Advertisement> advertisements) {
        List<SimpleAdvertisementDTO> retAdvert = new ArrayList<>();
        for(Advertisement a : advertisements)
            retAdvert.add(new SimpleAdvertisementDTO(a));

        List<AverageGradeDTO> averageGradeDTOS = feedbackClient.getAverageRates(new ArrayList<>(new HashSet<>(advertisements.stream().map(advertisement -> advertisement.getUserEmail()).collect(Collectors.toList()))));

        for(AverageGradeDTO avgGrade : averageGradeDTOS)
            for(SimpleAdvertisementDTO simpleAd : retAdvert)
                if(avgGrade.getAgentUsername().equals(simpleAd.getAgentUsername()))
                    simpleAd.setAvgRate(avgGrade.getAverageGrade());

        return retAdvert;
    }

    public AdvertisementDTO getAdvertisementById(Long id){
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        List<String> images = imageClient.getCarImagesUrl(advertisement.getCarId());
        return new AdvertisementDTO(advertisement, images);
    }

    public Long addNewAdvertisementByAgent(GetAdvertisementRequest request) {
        Advertisement advertisement = new Advertisement(request.getAdvertisement());
        advertisement.setRentingCityLocation(cityRepository.findByName(request.getAdvertisement().getRentingCityLocation()));

        Advertisement newAdvertisement = advertisementRepository.save(advertisement);
        if (newAdvertisement == null) {
            return null;
        } else {
            return newAdvertisement.getId();
        }

    }
}
