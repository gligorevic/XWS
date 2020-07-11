package com.example.SearchService.service;

import com.example.SearchService.client.RequestClient;
import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.domain.ReservationPeriod;
import com.example.SearchService.dto.ReservationPeriodDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.repository.AdvertisementRepository;
import com.example.SearchService.repository.ReservationPeriodRepository;
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

    @Autowired
    private RequestClient requestClient;

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

    public Long saveReservationPeriodAgent(com.baeldung.springsoap.gen.ReservationPeriod reservationPeriod) throws CustomException {
        ReservationPeriodDTO dto = new ReservationPeriodDTO(reservationPeriod);
        ReservationPeriod resPeriod = addNewReservationPeriod(dto);
        try{
            requestClient.canselRequests(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resPeriod.getId();
    }
}
