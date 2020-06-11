package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.domain.Car;
import com.example.AgentApplication.domain.City;
import com.example.AgentApplication.dto.AdvertisementDTO;
import com.example.AgentApplication.dto.SimpleAdvertisementDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.AdvertisementRepository;
import com.example.AgentApplication.repository.CarRepository;
import com.example.AgentApplication.repository.CityRepository;
import com.example.AgentApplication.repository.ReservationPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReservationPeriodRepository reservationPeriodRepository;

    @Autowired
    private CarRepository carRepository;

    @PersistenceContext
    private EntityManager em;

    public Advertisement addAdvertisement(AdvertisementDTO dto) throws CustomException, Exception{
        if(dto.getCityName() == null)
            throw new Exception("City not found");

        City city = cityRepository.findByName(dto.getCityName());

        if(city == null)
            throw new Exception("City not found");

        Car car = carRepository.findCarById(dto.getCarId());

        if(car == null)
            throw new Exception("Car not found");

        dto.setFreeFrom(getMidnightStartDate(dto.getFreeFrom()).getTime());
        dto.setFreeTo(getMidnightEndDate(dto.getFreeTo()).getTime());

        Advertisement advertisement = new Advertisement(dto);
        advertisement.setRentingCityLocation(city);
        advertisement.setCar(car);

        if(advertisementRepository.findAdvertisementByCarId(dto.getCarId()) != null)
            throw new CustomException("Advertisement already exists", HttpStatus.NOT_ACCEPTABLE);

        return advertisementRepository.save(advertisement);
    }

    public List<Advertisement> getAdvertisementsByUserId(String email){
        return advertisementRepository.findAdvertisementsByUserEmail(email);
    }

    public List<SimpleAdvertisementDTO> getAllAdvertisements(){
        return advertisementRepository.findAll().stream().map(advertisement -> new SimpleAdvertisementDTO(advertisement)).collect(Collectors.toList());
    }

    public List<Advertisement> getAdvertismentsBySearchParams(AdvertisementDTO advertisementDTO) throws CustomException {
        Query searchQuery = createQueryAndFillParameters( advertisementDTO);

        List<Advertisement> advertisements = searchQuery.getResultList();

        Calendar midnightStartDate = getMidnightStartDate(advertisementDTO.getFreeFrom());
        Calendar midnightEndDate = getMidnightEndDate(advertisementDTO.getFreeTo());

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
  
    public List<Advertisement> getAdvertisementsCart(Long[] adIds) {
        return advertisementRepository.findAllByIdIn(adIds);
    }

    public Advertisement getAdvertisementById(Long id){
        return advertisementRepository.findAdvertisementById(id);
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
}
