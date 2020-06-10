package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.domain.ReservationPeriod;
import com.example.AgentApplication.dto.ReservationPeriodDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.AdvertisementRepository;
import com.example.AgentApplication.repository.ReservationPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationPeriodService {

    @Autowired
    private ReservationPeriodRepository reservationPeriodRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public ReservationPeriod addNewReservationPeriod(ReservationPeriodDTO dto) throws CustomException {
        Calendar midnightStartDate = getMidnightStartDate(dto.getStartDate());
        dto.setStartDate(midnightStartDate.getTime());

        Calendar midnightEndDate = getMidnightEndDate(dto.getEndDate());
        dto.setEndDate(midnightEndDate.getTime());

        Advertisement advertisement = advertisementRepository.findAdvertisementById(dto.getAdvertisementId());

        //svi rezervisani periodi za izabrani oglas
        List<ReservationPeriod> periods = reservationPeriodRepository.checkIfOverlaping(dto.getAdvertisementId(), dto.getStartDate(), dto.getEndDate());

        if(!periods.isEmpty()){
            throw new CustomException("Periods are overlaping.", HttpStatus.NOT_ACCEPTABLE);
        }

        ReservationPeriod reservationPeriod = new ReservationPeriod(dto.getStartDate(), dto.getEndDate(), advertisement);
        return reservationPeriodRepository.save(reservationPeriod);
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

    public List<ReservationPeriod> getReservationPeriodsByAdvertisementId(Long id){
        return reservationPeriodRepository.findReservationPeriodsByAdvertisementId(id);
    }
}