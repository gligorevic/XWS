package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Image;
import com.example.AgentApplication.domain.Statistic;
import com.example.AgentApplication.dto.CarStatisticDTO;
import com.example.AgentApplication.dto.SimpleAdvertisementDTO;
import com.example.AgentApplication.repository.CarRepository;
import com.example.AgentApplication.repository.CommentRepository;
import com.example.AgentApplication.repository.GradeRepository;
import com.example.AgentApplication.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RequestRepository requestRepository;

    public Statistic getStatistic(){
        List<CarStatisticDTO> list = new ArrayList<>();
        carRepository.findAll().stream().forEach(car -> {
            CarStatisticDTO carStatisticDTO = new CarStatisticDTO(car);
            carStatisticDTO.setNumberOfComments(commentRepository.getCommentsForCar(car.getId()).size());
            carStatisticDTO.setAverageGrade(calculateAverageGrade(car.getId()));
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
        HashMap<Date, Integer> requestsPerDay = new HashMap<>();
        for(int i = 0; i <=6; i++){
            Calendar calendar = setStartDate(i);
            Date start = calendar.getTime();
            calendar = setEndDate(i);
            Date end = calendar.getTime();
            requestsPerDay.put(end, requestRepository.getRequestsByBetweenDates(start, end).size());
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

    private Double calculateAverageGrade(Long carId){
        List<Integer> list = gradeRepository.getGradesForCar(carId);
        Double average = 0.0;
        if(!list.isEmpty())
            average = (double) list.stream().mapToInt(Integer::intValue).sum() / (double) list.size();

        return average;
    }
}
