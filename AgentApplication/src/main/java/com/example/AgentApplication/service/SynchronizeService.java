package com.example.AgentApplication.service;

import com.baeldung.soap.ws.client.generated.CarsPort;
import com.baeldung.soap.ws.client.generated.CarsPortService;
import com.baeldung.soap.ws.client.generated.GetCarRequest;
import com.baeldung.soap.ws.client.generated.GetCarResponse;
import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.domain.Car;
import com.example.AgentApplication.domain.Comment;
import com.example.AgentApplication.repository.AdvertisementRepository;
import com.example.AgentApplication.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SynchronizeService {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private PriceListService priceListService;

    @Autowired
    private PriceListItemService priceListItemService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReservationPeriodService reservationPeriodService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ImageService imageService;

    public void synchronizeCars(){
        List<Car> cars = carRepository.findAll();
        CarsPortService service = new CarsPortService();
        CarsPort carsPort = service.getCarsPortSoap11();
        cars.stream().forEach(car -> {
            if(car.getRemoteId() == null){
                Map<String, byte[]> imagesMap = imageService.getImages(car.getId());
                GetCarRequest getCarRequest = new GetCarRequest();
                try {
                    com.baeldung.soap.ws.client.generated.Car car1 = new com.baeldung.soap.ws.client.generated.Car(car);
                    imagesMap.keySet().stream().forEach(key -> {
                        com.baeldung.soap.ws.client.generated.Map map = new com.baeldung.soap.ws.client.generated.Map();
                        map.setKey(key);
                        map.setValue(imagesMap.get(key));
                        car1.getMapImages().add(map);
                    });
                    getCarRequest.setCar(car1);
                    GetCarResponse getCarResponse = carsPort.getCar(getCarRequest);
                    car.setRemoteId(getCarResponse.getId());
                }catch (Exception e){
                    System.out.println("Mikroservis ne radi");
                }
            }
        });
    }

    public void synchronizeAdvertisements(){
        List<Advertisement> advertisements = advertisementRepository.findAll();
        advertisements.forEach(advertisement -> {
            if(advertisement.getRemoteId()==null){

            }
        });
    }

}
