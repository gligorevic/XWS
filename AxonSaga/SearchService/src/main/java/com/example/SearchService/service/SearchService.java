package com.example.SearchService.service;

import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.domain.City;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.repository.CityRepository;
import com.example.SearchService.repository.ReservationPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReservationPeriodRepository reservationPeriodRepository;

    @Autowired
    private CalendarService calendarService;

    @PersistenceContext
    private EntityManager em;

    public List<Advertisement> getAdvertismentsBySearchParams(AdvertisementDTO advertisementDTO) throws CustomException {
        Query searchQuery = createQueryAndFillParameters( advertisementDTO);

        List<Advertisement> advertisements = searchQuery.getResultList();

        Calendar midnightStartDate = calendarService.getMidnightStartDate(advertisementDTO.getFreeFrom());
        Calendar midnightEndDate = calendarService.getMidnightEndDate(advertisementDTO.getFreeTo());

        List<Long> unavailableAdvertisements = reservationPeriodRepository.getAllReservationPeriodsBetweenDates(midnightStartDate.getTime(), midnightEndDate.getTime());

        if(unavailableAdvertisements.size() > 0) {
            return advertisements.stream().filter(advertisement -> !unavailableAdvertisements.contains(advertisement.getId())).collect(Collectors.toList());
        } else {
            return advertisements;
        }
    }

    private Query createQueryAndFillParameters( AdvertisementDTO advertisementDTO) throws CustomException {
        String sqlStatement = createSearchSqlStatement(advertisementDTO);

        Query searchQuery = em.createNativeQuery(sqlStatement, Advertisement.class);

        Long cityId = getCityId(advertisementDTO.getCityName());

        if(cityId != null)
            searchQuery.setParameter("cityId", cityId);
        if(advertisementDTO.getFreeFrom() != null)
            searchQuery.setParameter("freeFrom", advertisementDTO.getFreeFrom());
        if(advertisementDTO.getFreeTo() != null)
            searchQuery.setParameter("freeTo", advertisementDTO.getFreeTo());

        return searchQuery;
    }

    private String createSearchSqlStatement(AdvertisementDTO advertisementDTO) {
        String startQuery = "SELECT * FROM ADVERTISEMENT AS AD WHERE";
        String sqlQuery = "SELECT * FROM ADVERTISEMENT AS AD WHERE";

        if(advertisementDTO.getCityName() != null) {
            sqlQuery = new StringBuilder(sqlQuery).append(" AD.RENTING_CITY_LOCATION_ID = :cityId AND").toString();
        }
        if(advertisementDTO.getFreeFrom() != null) {
            sqlQuery = new StringBuilder(sqlQuery).append(" :freeFrom > AD.FREE_FROM AND :freeFrom < AD.FREE_TO AND").toString();
        }
        if(advertisementDTO.getFreeTo() != null) {
            sqlQuery = new StringBuilder(sqlQuery).append(" :freeTo > AD.FREE_FROM AND :freeTo < AD.FREE_TO AND").toString();
        }

        if(startQuery.equals(sqlQuery))
            return "SELECT * FROM ADVERTISEMENT";
        else if(sqlQuery.substring(sqlQuery.length() - 3).equals("AND")) {
            return sqlQuery.substring(0, sqlQuery.length() - 4);
        } else {
            return sqlQuery;
        }
    }

    private Long getCityId(String cityName) throws CustomException{
        if(cityName != null) {
            City city = cityRepository.findByName(cityName);
            if(city == null)
                throw new CustomException("City doesn't exist", HttpStatus.BAD_REQUEST);

            return city.getId();
        }
        return null;
    }



}
