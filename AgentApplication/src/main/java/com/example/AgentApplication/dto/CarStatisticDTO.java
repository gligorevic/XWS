package com.example.AgentApplication.dto;

import com.example.AgentApplication.domain.Car;

public class CarStatisticDTO {

    private SimpleCarDTO car;

    private Double averageGrade;

    private Integer km;

    private Integer numberOfComments;

    public CarStatisticDTO(Car car){
        this.car = new SimpleCarDTO(car);
        this.km = car.getKmPassed();
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public SimpleCarDTO getCar() {
        return car;
    }

    public void setCar(SimpleCarDTO car) {
        this.car = car;
    }
}
