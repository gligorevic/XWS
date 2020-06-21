package com.example.RequestService.service;

import com.example.RequestService.client.AdvertisementClient;
import com.example.RequestService.domain.PaidState;
import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import com.example.RequestService.dto.*;
import com.example.RequestService.exception.CustomException;
import com.example.RequestService.repository.RequestContainerRepository;
import com.example.RequestService.repository.RequestRepository;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Autowired
    private AdvertisementClient advertisementClient;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestContainerRepository requestContainerRepository;

    public Request add(RequestDTO requestDTO) throws CustomException {

        Date now = new Date();
        requestDTO.setInBundle(false);
        Request request = new Request(requestDTO);
        request.setAdId(requestDTO.getId());
        request.setCrationDate(now);

        if(request.getUserEmail().equals(request.getUserSentRequest()))
            throw new CustomException("You can't send request to yourself", HttpStatus.BAD_REQUEST);

        return requestRepository.save(request);
    }

    public RequestContainer addBundle(RequestContainerDTO requestContainerDTO) throws CustomException {

        RequestContainer requestContainer = new RequestContainer(requestContainerDTO);
        if(requestContainer == null)
            throw new CustomException("Could not create bundle request", HttpStatus.BAD_REQUEST);

        if(!checkBundleRequest(requestContainerDTO.getRequestDTOS()))
            throw new CustomException("Something is wrong with this bundle request", HttpStatus.BAD_REQUEST);

        Date now = new Date();
        for(RequestDTO requestDTO : requestContainerDTO.getRequestDTOS()){
            requestDTO.setFreeFrom(getMidnightStartDate(requestDTO.getFreeFrom()).getTime());
            requestDTO.setFreeTo(getMidnightEndDate(requestDTO.getFreeTo()).getTime());
            requestDTO.setInBundle(true);
            Request request = new Request(requestDTO);
            if(request == null)
                throw new CustomException("Could not create request in bundle", HttpStatus.BAD_REQUEST);
            request.setCrationDate(now);
            requestContainer.getBoundleList().add(requestRepository.save(request));
        }

        return requestContainerRepository.save(requestContainer);
    }

    private boolean checkBundleRequest(List<RequestDTO> requestDTOS){

        if(requestDTOS.size() < 2)
            return false;

        for(int i = 0; i < requestDTOS.size() - 1; i++){
            RequestDTO tempRequest = requestDTOS.get(i);

            if(tempRequest.getUserSentRequest().equals(tempRequest.getUserEmail())){
                return false;
            }

            for(int j = i + 1; j < requestDTOS.size(); j++){
                RequestDTO requestDTO = requestDTOS.get(j);

                if(tempRequest.getId() == requestDTO.getId())
                    return false;

                if(!tempRequest.getUserEmail().equals(requestDTO.getUserEmail()))
                    return false;

                if(!tempRequest.getUserSentRequest().equals(requestDTO.getUserSentRequest()))
                    return false;

                if(requestDTO.getUserSentRequest().equals(requestDTO.getUserEmail()))
                    return false;
            }
        }
        return true;
    }

    public List<Request> cancelRequestsReservationPeriod(ReservationPeriodDTO reservationPeriodDTO){
        List<Request> requests = requestContainerRepository.getRequestsFromBundle(reservationPeriodDTO.getStartDate(), reservationPeriodDTO.getEndDate(), reservationPeriodDTO.getAdvertisementId());
        if(!requests.isEmpty()) {
            for (Request r : requests) {
                r.setPaidState(PaidState.CANCELED);
            }
            requestRepository.saveAll(requests);
        }
        return requests;
    }

    public List<RequestInfoDTO> getAllRequestsInfo(String username, String auth) throws CustomException {
        Map<Long, RequestInfoDTO> requestAdvertMap = new HashMap<>();
        List<Request> requestList = requestRepository.findAllByUserEmail(username);

        for(Request request : requestList) {
            Long key = request.getAdId();
            if(requestAdvertMap.containsKey(key)) {
                RequestInfoDTO value = requestAdvertMap.get(key);
                requestAdvertMap.replace(key, value.append(request.getPaidState()));
            } else {
                requestAdvertMap.put(request.getId(), new RequestInfoDTO(request.getAdId(),request.getPaidState()));
            }
        }

        List<AdvertisementDTO> advertisementsFromRequest;
        if(requestAdvertMap.size() > 0) {

            advertisementsFromRequest = advertisementClient.getAdvertisementsByIds(new ArrayList<>(requestAdvertMap.keySet()), auth).getBody();

            if(advertisementsFromRequest == null){
                return new ArrayList<>();
            }

            if(!advertisementsFromRequest.isEmpty()){
                for (AdvertisementDTO a : advertisementsFromRequest) {
                    Long key = a.getId();
                    RequestInfoDTO value = requestAdvertMap.get(key);
                    value.setBrandName(a.getBrandName());
                    value.setModelName(a.getModelName());
                    requestAdvertMap.replace(a.getId(), value);
                }
            }

        }
        return new ArrayList<>(requestAdvertMap.values());
    }

    public List<Request> getAllRequestsForAd(Long adId, String userEmail) throws CustomException {

        List<Request> requestList = requestRepository.findAllByAdId(adId);
        if(requestList.isEmpty() || requestList == null){
            throw new CustomException("No requests for this advertisement", HttpStatus.BAD_REQUEST);
        }

        if(!userEmail.equals(requestList.get(0).getUserEmail())) {
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return requestList;
    }

    public Request acceptRequest(Long requestId) throws CustomException{

        Request request = requestRepository.getOne(requestId);
        if(request == null){
            throw new CustomException("There is no request with that id", HttpStatus.BAD_REQUEST);
        }

        if(checkRequest(request.getCrationDate(), 24))
            request.setPaidState(PaidState.RESERVED);
        else{
            request.setPaidState(PaidState.CANCELED);
            requestRepository.save(request);
            throw new CustomException("This request is no longer valid", HttpStatus.BAD_REQUEST);
        }

        return requestRepository.save(request);
    }

    public Request declineRequest(Long requestId) throws CustomException {
        Request request = requestRepository.getOne(requestId);
        if(request == null){
            throw new CustomException("There is no request with that id", HttpStatus.BAD_REQUEST);
        }
        request.setPaidState(PaidState.CANCELED);

        return requestRepository.save(request);
    }


    private Calendar getMidnightStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar;
    }

    private Calendar getMidnightEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        return calendar;
    }

    public List<RequestBundleDTO> getAllRequestsInBundle(Long requestId, String auth) throws CustomException {

        List<Request> requests = requestContainerRepository.getRequestsInBundle(requestId);

        if(requests.isEmpty() || requests == null){
            throw new CustomException("This request is not in any bundle", HttpStatus.BAD_REQUEST);
        }
        List<RequestBundleDTO> requestBundleDTOS = new ArrayList<>();
        List<Long> requestAdIds = new ArrayList<>(requests.stream().map(request -> request.getAdId()).collect(Collectors.toList()));
        List<AdvertisementDTO> advertisementsFromRequest = advertisementClient.getAdvertisementsByIds(requestAdIds, auth).getBody();

        for(Request request : requests){
            List<AdvertisementDTO> advertisementDTO = advertisementsFromRequest.stream().filter(adDTO -> adDTO.getId() == request.getAdId()).collect(Collectors.toList());
            requestBundleDTOS.add(new RequestBundleDTO(request, advertisementDTO.get(0)));
        }

        return requestBundleDTOS;
    }

    public boolean requestInBundle(Long requestId){
        List<Request> requests = requestContainerRepository.getRequestsInBundle(requestId);
        if(requests == null || requests.isEmpty())
            return false;
        return true;
    }

    public List<Request> getRequestsInBundle(Long requestId){
        return requestContainerRepository.getRequestsInBundle(requestId);
    }

    @Scheduled(cron = "0 0 * ? * *")
    public void requestCleaner(){

        System.out.println("Cleaning init...");

        List<Request> pendingRequests = requestRepository.findAllPending();
        List<Request> reservedRequests = requestRepository.findAllReserved();

        if(!pendingRequests.isEmpty()){
            for(Request request : pendingRequests){
                if(!checkRequest(request.getCrationDate(), 24)){
                    System.out.println("Cleaned: " + request.getId());
                    request.setPaidState(PaidState.CANCELED);
                    requestRepository.save(request);
                }
            }
        }

        if(!reservedRequests.isEmpty()){
            for(Request request : reservedRequests){
                if(!checkRequest(request.getCrationDate(), 12)){
                    System.out.println("Cleaned: " + request.getId());
                    request.setPaidState(PaidState.CANCELED);
                    requestRepository.save(request);
                }
            }
        }
    }

    public boolean checkRequest(Date dateToCheck, int hours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToCheck);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        dateToCheck = calendar.getTime();
        Date now = new Date();

        if(dateToCheck.compareTo(now) > 0 ){
            return true;
        }else {
            return false;
        }
    }
}
