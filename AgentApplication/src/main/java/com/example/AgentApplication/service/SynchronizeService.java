package com.example.AgentApplication.service;

import com.baeldung.soap.ws.client.generated.*;
import com.example.AgentApplication.domain.*;
import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.domain.Car;
import com.example.AgentApplication.domain.Comment;
import com.example.AgentApplication.domain.Grade;
import com.example.AgentApplication.domain.Report;
import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.domain.ReservationPeriod;
import com.example.AgentApplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SynchronizeService {


    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestContainerRepository requestContainerRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PriceListItemRepository priceListItemRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private ReservationPeriodRepository reservationPeriodRepository;


    public Boolean synchronizeCars(){
        List<Car> cars = carRepository.findAll();
        CarsPortService service = new CarsPortService();
        CarsPort carsPort = service.getCarsPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
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
                    carRepository.save(car);
                    ret.set(true);
                }catch (Exception e){
                    System.out.println("Mikroservis ne radi");
                }
            }
        });
        return ret.get();
    }

    public Boolean synchronizeAdvertisements(){
        List<Advertisement> advertisements = advertisementRepository.findAll();
        AdvertisementPortService service = new AdvertisementPortService();
        AdvertisementPort advertisementPort = service.getAdvertisementPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        advertisements.forEach(advertisement -> {
            if(advertisement.getRemoteId()==null){
                GetAdvertisementRequest getAdvertisementRequest = new GetAdvertisementRequest();
                try {
                    com.baeldung.soap.ws.client.generated.Advertisement advertisement1 = new com.baeldung.soap.ws.client.generated.Advertisement(advertisement);
                    getAdvertisementRequest.setAdvertisement(advertisement1);

                    GetAdvertisementResponse getAdvertisementResponse = advertisementPort.getAdvertisement(getAdvertisementRequest);
                    advertisement.setRemoteId(getAdvertisementResponse.getId());
                    advertisementRepository.save(advertisement);
                    ret.set(true);
                }catch (Exception e){
                    System.out.println("Mikroservis ne radi");
                }
            }
        });
        return ret.get();
    }

    public Boolean synchronizePricelists(){
        List<PriceList> priceLists = priceListRepository.findAll();
        PricelistPortService service = new PricelistPortService();
        PricelistPort pricelistPort = service.getPricelistPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        priceLists.stream().forEach(priceList -> {
            if(priceList.getRemoteId() == null) {
                GetPricelistRequest request = new GetPricelistRequest();
                try {
                    Pricelist pricelist = new Pricelist(priceList);
                    request.setPricelist(pricelist);
                    GetPricelistResponse response = pricelistPort.getPricelist(request);
                    priceList.setRemoteId(response.getId());
                    priceListRepository.save(priceList);
                    ret.set(true);
                } catch (Exception e) {
                    System.out.println("Mikroservis ne radi");
                }
            }
        });
        return ret.get();
    }

    public Boolean synchronizePricelistItems(){
        List<PriceListItem> priceListItems = priceListItemRepository.findAll();
        PricelistPortService service = new PricelistPortService();
        PricelistPort pricelistPort = service.getPricelistPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        priceListItems.stream().forEach(priceListItem -> {
            if(priceListItem.getRemoteId() == null){
                GetPricelistItemRequest request = new GetPricelistItemRequest();
                try {
                    Advertisement advertisement = advertisementRepository.findAdvertisementById(priceListItem.getAdvertisementId());
                    PricelistItem pricelistItem = new PricelistItem(priceListItem, advertisement.getRemoteId());
                    request.setPricelistItem(pricelistItem);
                    GetPricelistItemResponse response = pricelistPort.getPricelistItem(request);
                    priceListItem.setRemoteId(response.getId());
                    priceListItemRepository.save(priceListItem);
                    ret.set(true);
                }catch (Exception e){
                    System.out.println("Mikroservis ne radi");
                }
            }
        });
        return ret.get();
    }

    public Boolean synchronizeRequests(){
        List<Request> requests = requestRepository.findAll();
        RequestsPortService requestsPortService = new RequestsPortService();
        RequestsPort requestsPort = requestsPortService.getRequestsPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        requests.stream().forEach(request -> {
            if(request.getRemoteId() == null){
                GetRequestRequest getReqRequest = new GetRequestRequest();
                try {
                    com.baeldung.soap.ws.client.generated.Request request1 = new com.baeldung.soap.ws.client.generated.Request(request);
                    getReqRequest.setRequest(request1);
                    GetRequestResponse getReqResponse = requestsPort.getRequest(getReqRequest);
                    request.setRemoteId(getReqResponse.getId());
                    requestRepository.save(request);
                    ret.set(true);
                }catch (Exception e){
                    System.out.println("Mikroservis ne radi");
                }
            }
        });
        return ret.get();
    }

    public Boolean synchronizeRequestContainers(){
        List<RequestContainer> requestContainers = requestContainerRepository.findAll();
        RequestsPortService requestsPortService = new RequestsPortService();
        RequestsPort requestsPort = requestsPortService.getRequestsPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        requestContainers.stream().forEach(requestContainer -> {
            if(requestContainer.getRemoteId() == null){
                GetContainerRequest getContainerRequest = new GetContainerRequest();
                Container container = new Container(requestContainer);
                List<com.baeldung.soap.ws.client.generated.Request> bundle = new ArrayList<>();
                requestContainer.getBoundleList().stream().forEach(request -> {
                    try {
                        bundle.add(new com.baeldung.soap.ws.client.generated.Request(request));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                });
                container.getBoundleList().addAll(bundle);
                getContainerRequest.setContainer(container);
                GetContainerResponse getContainerResponse = requestsPort.getContainer(getContainerRequest);
                requestContainer.setRemoteId(getContainerResponse.getId().get(0));
                for(int i = 1; i<getContainerResponse.getId().size(); i ++){
                    requestContainer.getBoundleList().get(i-1).setRemoteId(getContainerResponse.getId().get(i));
                }
                requestRepository.saveAll(requestContainer.getBoundleList());
                requestContainerRepository.save(requestContainer);
                ret.set(true);
            }
        });
        return ret.get();
    }

    public Boolean synchronizeReports(){
        List<Report> reports = reportRepository.findAll();
        PricelistPortService service = new PricelistPortService();
        PricelistPort pricelistPort = service.getPricelistPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        reports.stream().forEach(report -> {
            if(report.getRemoteId() == null){
                GetReportRequest reportRequest = new GetReportRequest();
                try {
                    com.baeldung.soap.ws.client.generated.Report report1 = new com.baeldung.soap.ws.client.generated.Report(report);
                    reportRequest.setReport(report1);
                    GetReportResponse response = pricelistPort.getReport(reportRequest);
                    report.setRemoteId(response.getId());
                    reportRepository.save(report);
                    ret.set(true);
                }catch (Exception e){
                    System.out.println("Mikroservis ne radi");
                }
            }
        });
        return ret.get();
    }

    public Boolean synchronizeComments(){
        List<Comment> comments = commentRepository.findAll();
        FeedbackPortService service = new FeedbackPortService();
        FeedbackPort feedbackPort = service.getFeedbackPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        comments.stream().forEach(comment -> {
            if(comment.getRemoteId() == null){
                GetCommentRequest request = new GetCommentRequest();
                try {
                    com.baeldung.soap.ws.client.generated.Comment comment1 = new com.baeldung.soap.ws.client.generated.Comment(comment);
                    request.setComment(comment1);
                    GetCommentResponse response = feedbackPort.getComment(request);
                    comment.setRemoteId(response.getId());
                    commentRepository.save(comment);
                    ret.set(true);
                }catch (Exception e){
                    System.out.println("Mikroservis ne radi.");
                }
            }
        });
        return ret.get();
    }

    public Boolean synchronizeGrades(){
        List<Grade> grades = gradeRepository.findAll();
        FeedbackPortService service = new FeedbackPortService();
        FeedbackPort feedbackPort = service.getFeedbackPortSoap11();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        grades.stream().forEach(grade -> {
            if(grade.getRemoteId() == null){
                GetGradeRequest request = new GetGradeRequest();
                try {
                    com.baeldung.soap.ws.client.generated.Grade grade1 = new com.baeldung.soap.ws.client.generated.Grade(grade);
                    request.setGrade(grade1);
                    GetGradeResponse response = feedbackPort.getGrade(request);
                    grade.setRemoteId(response.getId());
                    gradeRepository.save(grade);
                    ret.set(true);
                }catch (Exception e){
                    System.out.println("Mikroservis ne radi.");
                }
            }
        });
        return ret.get();
    }

    public Boolean synchronizeReservationPeriods(){
        List<ReservationPeriod> reservationPeriods = reservationPeriodRepository.findAll();
        AtomicReference<Boolean> ret = new AtomicReference<>(false);
        AdvertisementPortService service = new AdvertisementPortService();
        AdvertisementPort advertisementPort = service.getAdvertisementPortSoap11();
        reservationPeriods.stream().forEach(reservationPeriod -> {
            try {
                com.baeldung.soap.ws.client.generated.ReservationPeriod period = new com.baeldung.soap.ws.client.generated.ReservationPeriod(reservationPeriod);
                GetReservationPeriodRequest request = new GetReservationPeriodRequest();
                request.setReservationPeriod(period);
                GetReservationPeriodResponse response = advertisementPort.getReservationPeriod(request);
                reservationPeriod.setRemoteId(response.getId());
                reservationPeriodRepository.save(reservationPeriod);
                ret.set(true);
            }catch (Exception e){
                System.out.println("Mikroservis ne radi");
            }
        });

        return ret.get();
    }

}
