package com.example.RequestService.domain;

import com.example.RequestService.dto.CarStatisticDTO;

import java.util.HashMap;
import java.util.List;

public class Statistic {
    private Double averageGradeCars;

    private List<CarStatisticDTO> cars;

    private HashMap<Integer, Integer> requestsPerDay;

    public Statistic(Double averageGradeCars, List<CarStatisticDTO> cars, HashMap<Integer, Integer> requestsPerDay){
        this.averageGradeCars = averageGradeCars;
        this.cars = cars;
        this.requestsPerDay = requestsPerDay;
    }

    public Double getAverageGradeCars() {
        return averageGradeCars;
    }

    public void setAverageGradeCars(Double averageGradeCars) {
        this.averageGradeCars = averageGradeCars;
    }

    public List<CarStatisticDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarStatisticDTO> cars) {
        this.cars = cars;
    }

    public HashMap<Integer, Integer> getRequestsPerDay() {
        return requestsPerDay;
    }

    public void setRequestsPerDay(HashMap<Integer, Integer> requestsPerDay) {
        this.requestsPerDay = requestsPerDay;
    }
}
