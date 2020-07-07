package com.example.RequestService.service;


import com.example.RequestService.client.AdvertisementClient;
import com.example.RequestService.client.FeedbackClient;
import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.Statistic;
import com.example.RequestService.dto.AdvertisementStatisticDTO;
import com.example.RequestService.dto.CarStatisticDTO;
import com.example.RequestService.dto.FeedbackStatisticDTO;
import com.example.RequestService.dto.RequestStatisticDTO;
import com.example.RequestService.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticService {

    @Autowired
    private FeedbackClient feedbackClient;

    @Autowired
    private AdvertisementClient advertisementClient;

    @Autowired
    private RequestRepository requestRepository;

    public Statistic getStatistic(String authentication){
        List<CarStatisticDTO> list = new ArrayList<>();
        List<RequestStatisticDTO> requests = new ArrayList<>();
        List<AdvertisementStatisticDTO> advertisementList = advertisementClient.getAdvertisementsByUserEmail(authentication).getBody();
        advertisementList.stream().forEach(ad -> {
            requestRepository.findAllByAdId(ad.getId()).stream().forEach( request ->
                    requests.add(new RequestStatisticDTO(request))
            );
        });
        List<FeedbackStatisticDTO> feedbackList = feedbackClient.getFeedbackStatistic(requests, authentication).getBody();
        advertisementList.stream().forEach(ad -> {
            CarStatisticDTO carStatisticDTO = new CarStatisticDTO(ad);
            carStatisticDTO.setKm(ad.getKmPassed());
            feedbackList.stream().forEach(feedbackStatisticDTO -> {
                if(feedbackStatisticDTO.getAdId().equals(ad.getId())) {
                    carStatisticDTO.setAverageGrade(feedbackStatisticDTO.getAverageGrade());
                    carStatisticDTO.setNumberOfComments(feedbackStatisticDTO.getNumberOfComments());
                }
            });
            list.add(carStatisticDTO);
        });

        Double sum = 0.0;
        Double numberOfCars = 0.0; //samo se racunaju oni koji su ocenjeni, a ne svi
        for(CarStatisticDTO carStatisticDTO : list){
            if(carStatisticDTO.getAverageGrade() > 0.0){
                sum += carStatisticDTO.getAverageGrade();
                numberOfCars++;
            }
        }
        HashMap<Integer, Integer> requestsPerDay = new HashMap<>();
        for(int i = 0; i <=6; i++){
            Calendar calendar = setStartDate(i);
            Date start = calendar.getTime();
            calendar = setEndDate(i);
            Date end = calendar.getTime();
            requestsPerDay.put(-i, requestRepository.getRequestsByBetweenDates(start, end).size());
        }

        return new Statistic(sum/numberOfCars, list, requestsPerDay);
    }

    private Calendar setStartDate(int i){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -i);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private Calendar setEndDate(int i){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -i);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar;
    }

}
