package com.example.AgentApplication.domain;

import com.example.AgentApplication.dto.CarStatisticDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Statistic {
    private Double averageGradeCars;

    private List<CarStatisticDTO> cars;

    private HashMap<Date, Integer> requestsPerDay;

    public Statistic(Double averageGradeCars, List<CarStatisticDTO> cars, HashMap<Date, Integer> requestsPerDay){
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

    public HashMap<Date, Integer> getRequestsPerDay() {
        return requestsPerDay;
    }

    public void setRequestsPerDay(HashMap<Date, Integer> requestsPerDay) {
        this.requestsPerDay = requestsPerDay;
    }
}
