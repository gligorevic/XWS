package com.example.AgentApplication.service;

import com.baeldung.soap.ws.client.generated.*;
import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.domain.Car;
import com.example.AgentApplication.domain.City;
import com.example.AgentApplication.domain.Grade;
import com.example.AgentApplication.dto.*;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;
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

    @Autowired
    private ImageService imageService;

    @Autowired
    private GradeRepository gradeRepository;

    @PersistenceContext
    private EntityManager em;

    public Advertisement addAdvertisement(AdvertisementPostDTO dto) throws CustomException, Exception{
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

        //soap
        AdvertisementPortService service = new AdvertisementPortService();
        AdvertisementPort advertisementPort = service.getAdvertisementPortSoap11();
        GetAdvertisementRequest getAdvertisementRequest = new GetAdvertisementRequest();
        try {
            com.baeldung.soap.ws.client.generated.Advertisement advertisement1 = new com.baeldung.soap.ws.client.generated.Advertisement(car, advertisement);
            getAdvertisementRequest.setAdvertisement(advertisement1);

            GetAdvertisementResponse getAdvertisementResponse = advertisementPort.getAdvertisement(getAdvertisementRequest);
            advertisement.setRemoteId(getAdvertisementResponse.getId());
        }catch (Exception e){
            System.out.println("Mikroservis ne radi");
        }

        return advertisementRepository.save(advertisement);
    }

    public List<Advertisement> getAdvertisements(){
        return advertisementRepository.findAll();
    }

    public List<SimpleAdvertisementDTO> getSimpleAdvertisements(){
        List<SimpleAdvertisementDTO> advertisements = advertisementRepository.findAll().stream().map(advertisement -> new SimpleAdvertisementDTO(advertisement)).collect(Collectors.toList());
        advertisements.stream().forEach(ad -> {
            calculateGrade(ad);
        });
        return advertisements;
    }
    private void calculateGrade(SimpleAdvertisementDTO ad){
        List<Integer> list = gradeRepository.getGradesForAdvertisement(ad.getId());
        Double average = 0.0;
        if(!list.isEmpty())
            average = (double) list.stream().mapToInt(Integer::intValue).sum() / (double) list.size();

        ad.setAverageGrade(average);
    }

    public List<SimpleAdvertisementDTO> getAdvertismentsBySearchParams(SearchDTO searchDTO) throws CustomException {
        Query searchQuery = createQueryAndFillParameters( searchDTO);

        List<Advertisement> advertisements = searchQuery.getResultList();

        Calendar midnightStartDate = getMidnightStartDate(searchDTO.getFreeFrom());
        Calendar midnightEndDate = getMidnightEndDate(searchDTO.getFreeTo());

        List<Long> unavailableAdvertisements = reservationPeriodRepository.getAllReservationPeriodsBetweenDates(midnightStartDate.getTime(), midnightEndDate.getTime());

        if(unavailableAdvertisements.size() > 0) {
            return convertToSimpleDTO(advertisements.stream().filter(advertisement -> !unavailableAdvertisements.contains(advertisement.getId())).collect(Collectors.toList()));
        } else {
            return convertToSimpleDTO(advertisements);
        }
    }

    private List<SimpleAdvertisementDTO> convertToSimpleDTO(List<Advertisement> advertisements){
        List<SimpleAdvertisementDTO> list = new ArrayList<>();
        advertisements.stream().forEach(advertisement -> {
            SimpleAdvertisementDTO ad = new SimpleAdvertisementDTO(advertisement);
            calculateGrade(ad);
            list.add(ad);
        });
        return list;
    }

    private Query createQueryAndFillParameters( SearchDTO searchDTO) throws CustomException {
        String sqlStatement = createSearchSqlStatement(searchDTO);

        Query searchQuery = em.createNativeQuery(sqlStatement, Advertisement.class);

        Long cityId = getCityId(searchDTO.getCityName());

        if(cityId != null)
            searchQuery.setParameter("cityId", cityId);
        if(searchDTO.getFreeFrom() != null)
            searchQuery.setParameter("freeFrom", searchDTO.getFreeFrom());
        if(searchDTO.getFreeTo() != null)
            searchQuery.setParameter("freeTo", searchDTO.getFreeTo());

        return searchQuery;
    }

    private String createSearchSqlStatement(SearchDTO advertisementDTO) {
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

    public AdvertisementDTO getAdvertisementById(Long id){
        Advertisement advertisement = advertisementRepository.findAdvertisementById(id);
        Car car = carRepository.findCarById(advertisement.getCar().getId());
        List<String> images = imageService.getAllImagesByCarId(advertisement.getCar().getId());
        AdvertisementDTO advertisementDTO = new AdvertisementDTO(advertisementRepository.findAdvertisementById(id), new CarDTO(car, images));
        return advertisementDTO;
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
