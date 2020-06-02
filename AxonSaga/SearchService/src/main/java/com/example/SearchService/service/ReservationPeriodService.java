package com.example.SearchService.service;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.domain.ReservationPeriod;
import com.example.SearchService.dto.ReservationPeriodDTO;
import com.example.SearchService.repository.AdvertisementRepository;
import com.example.SearchService.repository.ReservationPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class ReservationPeriodService {

    @Autowired
    private ReservationPeriodRepository reservationPeriodRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public ReservationPeriod addNewReservationPeriod(ReservationPeriodDTO dto){
        //postavljanje satnice 00:00 za pocetni datum
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getStartDate());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        dto.setStartDate(calendar.getTime());

        //postavljanje satnice 23:59 za datum zavrsetka
        calendar.setTime(dto.getEndDate());
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        dto.setEndDate(calendar.getTime());

        Advertisement advertisement = advertisementRepository.findAdvertisementById(dto.getAdvertisementId());

        //svi rezervisani periodi za izabrani oglas
        List<ReservationPeriod> periods = reservationPeriodRepository.findReservationPeriodsByAdvertisementId(dto.getAdvertisementId());

        //provera da li se uneti period preklapa sa vec unetim periodima
        for(ReservationPeriod period: periods){
            if((dto.getStartDate().after(period.getStartDate()) && dto.getStartDate().before(period.getEndDate())) || (dto.getEndDate().after(period.getStartDate()) && dto.getEndDate().before(period.getEndDate()))){
                return null;
            }
        }

        ReservationPeriod reservationPeriod = new ReservationPeriod(dto.getStartDate(), dto.getEndDate(), advertisement);
        return reservationPeriodRepository.save(reservationPeriod);
    }
}
