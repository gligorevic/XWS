package com.example.SearchService.service;

import com.example.SearchService.client.FeedbackClient;
import com.example.SearchService.client.ImageClient;
import com.example.SearchService.domain.Advertisement;
import com.example.SearchService.dto.AdvertisementDTO;
import com.example.SearchService.dto.AverageGradeDTO;
import com.example.SearchService.dto.SimpleAdvertisementDTO;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.repository.ReservationPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private CityService cityService;

    @Autowired
    private ReservationPeriodRepository reservationPeriodRepository;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private FeedbackClient feedbackClient;

    @PersistenceContext
    private EntityManager em;

    public List<SimpleAdvertisementDTO> getAdvertismentsBySearchParams(AdvertisementDTO advertisementDTO) throws CustomException {
        Query searchQuery = createQueryAndFillParameters(advertisementDTO);
        List<Advertisement> advertisements = searchQuery.getResultList();
        List<Long> unavailableAdvertisements = getUnavailableAdvertisements(advertisementDTO);
        List<Advertisement> availableAdvertisemenst = filterUnavailableAdvertisements(advertisements, unavailableAdvertisements);
        return getAverageRates(availableAdvertisemenst);
    }

    private List<SimpleAdvertisementDTO> getAverageRates(List<Advertisement> availableAdvertisemenst) {
        List<SimpleAdvertisementDTO> retAdvert = new ArrayList<>();
        for(Advertisement a : availableAdvertisemenst)
            retAdvert.add(new SimpleAdvertisementDTO(a));

        List<AverageGradeDTO> averageGradeDTOS = feedbackClient.getAverageRates(new ArrayList<>(new HashSet<>(availableAdvertisemenst.stream().map(advertisement -> advertisement.getUserEmail()).collect(Collectors.toList()))));

        for(AverageGradeDTO avgGrade : averageGradeDTOS)
            for(SimpleAdvertisementDTO simpleAd : retAdvert)
                if(avgGrade.getAgentUsername().equals(simpleAd.getAgentUsername()))
                    simpleAd.setAvgRate(avgGrade.getAverageGrade());

        return retAdvert;
    }

    private Query createQueryAndFillParameters(AdvertisementDTO advertisementDTO) throws CustomException {
        String sqlStatement = createSearchSqlStatement(advertisementDTO);
        Query searchQuery = fillQueryParams(advertisementDTO, sqlStatement);
        return searchQuery;
    }

    private Query fillQueryParams(AdvertisementDTO advertisementDTO, String sqlStatement) throws CustomException {
        Query searchQuery = em.createNativeQuery(sqlStatement, Advertisement.class);

        if (advertisementDTO.getCityName() != null) {
            Long cityId = cityService.getCityId(advertisementDTO.getCityName());
            searchQuery.setParameter("cityId", cityId);
        }
        if (advertisementDTO.getFreeFrom() != null)
            searchQuery.setParameter("freeFrom", advertisementDTO.getFreeFrom());
        if (advertisementDTO.getFreeTo() != null)
            searchQuery.setParameter("freeTo", advertisementDTO.getFreeTo());
        if (advertisementDTO.getBrandName() != null)
            searchQuery.setParameter("brandName", advertisementDTO.getBrandName());
        if (advertisementDTO.getModelName() != null)
            searchQuery.setParameter("modelName", advertisementDTO.getModelName());
        if (advertisementDTO.getFuelTypeName() != null)
            searchQuery.setParameter("fuelTypeName", advertisementDTO.getFuelTypeName());
        if (advertisementDTO.getGearShiftName() != null)
            searchQuery.setParameter("gearShiftName", advertisementDTO.getGearShiftName());
        if (advertisementDTO.getBodyName() != null)
            searchQuery.setParameter("bodyName", advertisementDTO.getBodyName());
        if (advertisementDTO.getKmPassed() != null)
            searchQuery.setParameter("kmPassed", advertisementDTO.getKmPassed());
        if (advertisementDTO.getKmRestriction() != null)
            searchQuery.setParameter("kmRestriction", advertisementDTO.getKmRestriction());
        if (advertisementDTO.getCollisionDamage() != null && advertisementDTO.getCollisionDamage())
            searchQuery.setParameter("collisionDamage", advertisementDTO.getCollisionDamage());
        if (advertisementDTO.getNumberChildSeats() != null)
            searchQuery.setParameter("childSeatsNum", advertisementDTO.getNumberChildSeats());
        if (advertisementDTO.getPrice() != null)
            searchQuery.setParameter("priceFrom", advertisementDTO.getPrice());
        if (advertisementDTO.getPriceTo() != null)
            searchQuery.setParameter("priceTo", advertisementDTO.getPriceTo());

        return searchQuery;
    }

    private String createSearchSqlStatement(AdvertisementDTO advertisementDTO) {
        String startQuery = "SELECT * FROM ADVERTISEMENT AS AD WHERE";
        StringBuilder sqlQueryBuilder = new StringBuilder("SELECT * FROM ADVERTISEMENT AS AD WHERE");

        if (advertisementDTO.getCityName() != null)
            sqlQueryBuilder.append(" AD.RENTING_CITY_LOCATION_ID = :cityId AND");
        if (advertisementDTO.getFreeFrom() != null)
            sqlQueryBuilder.append(" :freeFrom > AD.FREE_FROM AND :freeFrom < AD.FREE_TO AND");
        if (advertisementDTO.getFreeTo() != null)
            sqlQueryBuilder.append(" :freeTo > AD.FREE_FROM AND :freeTo < AD.FREE_TO AND");
        if (advertisementDTO.getBrandName() != null)
            sqlQueryBuilder.append(" AD.BRAND_NAME LIKE :brandName AND");
        if (advertisementDTO.getModelName() != null)
            sqlQueryBuilder.append(" AD.MODEL_NAME LIKE :modelName AND");
        if (advertisementDTO.getFuelTypeName() != null)
            sqlQueryBuilder.append(" AD.FUEL_TYPE_NAME LIKE :fuelTypeName AND");
        if (advertisementDTO.getGearShiftName() != null)
            sqlQueryBuilder.append(" AD.GEAR_SHIFT_NAME LIKE :gearShiftName AND");
        if (advertisementDTO.getBodyName() != null)
            sqlQueryBuilder.append(" AD.BODY_NAME LIKE :bodyName AND");
        if (advertisementDTO.getKmPassed() != null)
            sqlQueryBuilder.append(" AD.KM_PASSED <= :kmPassed AND");
        if (advertisementDTO.getKmRestriction() != null)
            sqlQueryBuilder.append(" AD.KM_RESTRICTION >= :kmRestriction AND");
        if (advertisementDTO.getCollisionDamage() != null && advertisementDTO.getCollisionDamage())
            sqlQueryBuilder.append(" AD.COLLISION_DAMAGE = :collisionDamage AND");
        if (advertisementDTO.getNumberChildSeats() != null)
            sqlQueryBuilder.append(" AD.NUMBER_CHILD_SEATS >= :childSeatsNum AND");
        if (advertisementDTO.getPrice() != null)
            sqlQueryBuilder.append(" AD.PRICE_FROM >= :priceFrom AND");
        if (advertisementDTO.getPriceTo() != null)
            sqlQueryBuilder.append(" AD.PRICE_TO <= :priceTo AND");


        String sqlQuery = sqlQueryBuilder.toString();
        if (startQuery.equals(sqlQuery))
            return "SELECT * FROM ADVERTISEMENT";
        else if (sqlQuery.substring(sqlQuery.length() - 3).equals("AND")) {
            return sqlQuery.substring(0, sqlQuery.length() - 4);
        } else {
            return sqlQuery;
        }
    }

    private List<Long> getUnavailableAdvertisements(AdvertisementDTO advertisementDTO) {
        Calendar midnightStartDate = calendarService.getMidnightStartDate(advertisementDTO.getFreeFrom());
        Calendar midnightEndDate = calendarService.getMidnightEndDate(advertisementDTO.getFreeTo());
        return reservationPeriodRepository.getAllReservationPeriodsBetweenDates(midnightStartDate.getTime(), midnightEndDate.getTime());
    }

    private List<Advertisement> filterUnavailableAdvertisements(List<Advertisement> advertisements, List<Long> unavailableAdvertisements) {
        return unavailableAdvertisements.size() > 0 ?
                advertisements.stream().filter(advertisement -> !unavailableAdvertisements.contains(advertisement.getId())).collect(Collectors.toList())
                : advertisements;
    }
}
