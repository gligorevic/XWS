package com.example.RequestService.dto;



public class CarStatisticDTO {

    private AdvertisementStatisticDTO ad;

    private Double averageGrade;

    private Integer km;

    private Integer numberOfComments;

    public CarStatisticDTO(AdvertisementStatisticDTO ad){
        this.ad = ad;
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

    public AdvertisementStatisticDTO getAd() {
        return ad;
    }

    public void setAd(AdvertisementStatisticDTO ad) {
        this.ad = ad;
    }
}
