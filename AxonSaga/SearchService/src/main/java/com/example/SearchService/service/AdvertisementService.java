package com.example.SearchService.service;

import com.baeldung.springsoap.gen.GetAdvertisementRequest;
import com.example.SearchService.client.FeedbackClient;
import com.example.SearchService.client.ImageClient;
import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.domain.City;
import com.example.SearchService.dto.*;
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

        City city = cityRepository.findCityByName(dto.getCityName());

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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        return getAverageRates(advertisementRepository.findAllAdsValidTwoDaysFromNow(calendar.getTime()));
    }

    private List<SimpleAdvertisementDTO> getAverageRates(List<Advertisement> advertisements) {
        List<SimpleAdvertisementDTO> retAdvert = new ArrayList<>();
        for(Advertisement a : advertisements)
            retAdvert.add(new SimpleAdvertisementDTO(a));

        try {
            List<AverageGradeDTO> averageGradeDTOS = feedbackClient.getAverageRates(new ArrayList<>(new HashSet<>(advertisements.stream().map(advertisement -> advertisement.getUserEmail()).collect(Collectors.toList()))));

            for (AverageGradeDTO avgGrade : averageGradeDTOS)
                for (SimpleAdvertisementDTO simpleAd : retAdvert)
                    if (avgGrade.getAgentUsername().equals(simpleAd.getAgentUsername()))
                        simpleAd.setAvgRate(avgGrade.getAverageGrade());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return retAdvert;
        }
    }

    public AdvertisementDTO getAdvertisementById(Long id){
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        List<String> images = imageClient.getCarImagesUrl(advertisement.getCarId());
        return new AdvertisementDTO(advertisement, images);
    }

    public Long addNewAdvertisementByAgent(GetAdvertisementRequest request) {
        Advertisement advertisement = new Advertisement(request.getAdvertisement());
        advertisement.setRentingCityLocation(cityRepository.findCityByName(request.getAdvertisement().getRentingCityLocation()));

        Advertisement newAdvertisement = advertisementRepository.save(advertisement);
        if (newAdvertisement == null) {
            return null;
        } else {
            return newAdvertisement.getId();
        }

    }

    public AdvertisementCalculatingDTO getAdvertisementForCalculating(Long id){
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        return new AdvertisementCalculatingDTO(advertisement);
    }

    public AdvertisementDTO setPriceForAdvertisement(Long id, Integer priceKm, Integer priceDay) {
        Advertisement advertisement = advertisementRepository.findById(id).get();

        advertisement.setPriceFrom(priceDay);
        advertisement.setPriceTo(priceKm);

        advertisementRepository.save(advertisement);
        return new AdvertisementDTO(advertisement);
    }
}
