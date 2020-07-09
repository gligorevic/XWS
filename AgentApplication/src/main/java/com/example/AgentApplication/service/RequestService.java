package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.domain.User;
import com.example.AgentApplication.dto.*;
import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.domain.RequestContainer;
import com.example.AgentApplication.enumeration.PaidState;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.AdvertisementRepository;
import com.example.AgentApplication.repository.RequestContainerRepository;
import com.example.AgentApplication.repository.RequestRepository;
import com.example.AgentApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestContainerRepository requestContainerRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserRepository userRepository;

    public Request add(RequestDTO requestDTO) throws CustomException {

        requestDTO.setInBundle(false);
        requestDTO.setFreeFrom(getMidnightStartDate(requestDTO.getFreeFrom()).getTime());
        requestDTO.setEndDate(getMidnightEndDate(requestDTO.getEndDate()).getTime());
        Request request = new Request(requestDTO);

        Advertisement advertisement = advertisementRepository.findAdvertisementById(requestDTO.getId());
        if(advertisement == null)
            throw new CustomException("Advertisement doesn't exist", HttpStatus.BAD_REQUEST);
        request.setAdvertisement(advertisement);

        User userSentRequest = userRepository.findByEmail(requestDTO.getUserSentRequest());
        if(userSentRequest == null)
            throw new CustomException("No user with that email", HttpStatus.BAD_REQUEST);
        request.setUserSentRequest(userSentRequest);

        List<Request> createdRequests = requestRepository.findAllByAdvertisementAndUserSentRequest(request.getAdvertisement(), request.getUserSentRequest());

        if(!createdRequests.isEmpty()){
            if(checkRequestDatesOverlapping(createdRequests, request))
                throw new CustomException("You have already made request that is in this range of dates.", HttpStatus.BAD_REQUEST);
        }

        if(request.getAdvertisement().getCar().getUserEmail().equals(request.getUserSentRequest()))
            throw new CustomException("You can't send request to yourself", HttpStatus.BAD_REQUEST);

        return requestRepository.save(request);
    }

    private boolean checkRequestDatesOverlapping(List<Request> req, Request request) {

        for(Request r : req){
            if(r.getStartDate().compareTo(request.getStartDate()) <= 0 && r.getEndDate().compareTo(request.getStartDate()) >= 0){
                return true;
            }
            if(r.getStartDate().compareTo(request.getEndDate()) <= 0 && r.getEndDate().compareTo(request.getEndDate()) >= 0){
                return true;
            }
            if(r.getStartDate().compareTo(request.getStartDate()) >= 0 && r.getEndDate().compareTo(request.getEndDate()) <= 0){
                return true;
            }
        }
        return false;
    }

    public RequestContainer addBundle(RequestContainerDTO requestContainerDTO) throws CustomException {

        RequestContainer requestContainer = new RequestContainer(requestContainerDTO);
        if (requestContainer == null)
            throw new CustomException("Could not create bundle request", HttpStatus.BAD_REQUEST);

        if (!checkBundleRequest(requestContainerDTO.getRequestDTOS()))
            throw new CustomException("Something is wrong with this bundle request", HttpStatus.BAD_REQUEST);

        List<Request> requestsForBundle = new ArrayList<>();
        requestContainerRepository.save(requestContainer);
        for (RequestDTO requestDTO : requestContainerDTO.getRequestDTOS()) {
            requestDTO.setFreeFrom(getMidnightStartDate(requestDTO.getFreeFrom()).getTime());
            requestDTO.setEndDate(getMidnightEndDate(requestDTO.getEndDate()).getTime());
            Request request = new Request(requestDTO);
            if (request == null)
                throw new CustomException("Could not create request in bundle", HttpStatus.BAD_REQUEST);
            request.setInBundle(true);

            Advertisement advertisement = advertisementRepository.findAdvertisementById(requestDTO.getId());
            if(advertisement == null)
                throw new CustomException("Advertisement doesn't exist", HttpStatus.BAD_REQUEST);
            request.setAdvertisement(advertisement);

            User userSentRequest = userRepository.findByEmail(requestDTO.getUserSentRequest());
            if(userSentRequest == null)
                throw new CustomException("No user with that email ", HttpStatus.BAD_REQUEST);
            request.setUserSentRequest(userSentRequest);

            request.setRequestContainer(requestContainer);

            List<Request> createdRequests = requestRepository.findAllByAdvertisementAndUserSentRequest(request.getAdvertisement(), request.getUserSentRequest());
            if(!createdRequests.isEmpty()){
                if(checkRequestDatesOverlapping(createdRequests, request))
                    throw new CustomException("You have already made request that is in this range of dates.", HttpStatus.BAD_REQUEST);
            }
            requestsForBundle.add(request);
            requestContainer.getBoundleList().add(request);
        }

        requestRepository.saveAll(requestsForBundle);
        return requestContainerRepository.save(requestContainer);
    }

    private boolean checkBundleRequest(List<RequestDTO> requestDTOS) {

        if (requestDTOS.size() < 2)
            return false;

        for (int i = 0; i < requestDTOS.size() - 1; i++) {
            RequestDTO tempRequest = requestDTOS.get(i);

            if (tempRequest.getUserSentRequest().equals(tempRequest.getUserEmail())) {
                return false;
            }

            for (int j = i + 1; j < requestDTOS.size(); j++) {
                RequestDTO requestDTO = requestDTOS.get(j);

                if (tempRequest.getId() == requestDTO.getId())
                    return false;

                if (!tempRequest.getUserEmail().equals(requestDTO.getUserEmail()))
                    return false;

                if (!tempRequest.getUserSentRequest().equals(requestDTO.getUserSentRequest()))
                    return false;

                if (requestDTO.getUserSentRequest().equals(requestDTO.getUserEmail()))
                    return false;
            }
        }
        return true;
    }

    public List<RequestDTO> getAllRequestsForAd(Long adId) throws CustomException {

        List<Request> requestList = requestRepository.findAllByAdvertisementId(adId);
        if (requestList.isEmpty() || requestList == null) {
            throw new CustomException("No requests for this advertisement", HttpStatus.BAD_REQUEST);
        }

        List<RequestDTO> requestDTOList = new ArrayList<>();
        for(Request r : requestList){
            requestDTOList.add(new RequestDTO(r));
        }

        return requestDTOList;
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

    public List<Request> getPassedRequests(){
        return requestRepository.getRequestsPassed(new Date());
    }

    public List<Request> getReservedRequests() { return  requestRepository.getReservedRequests();}

    public List<RequestInfoDTO> getAllRequestsInfoByReciverUsername() throws CustomException {
        List<Request> requestList = requestRepository.findAll();

        Map<Long, RequestInfoDTO> requestAdvertMap = new HashMap<>();

        for (Request request : requestList) {
            Long key = request.getAdvertisement().getId();
            if (requestAdvertMap.containsKey(key)) {
                RequestInfoDTO value = requestAdvertMap.get(key);
                requestAdvertMap.replace(key, value.append(request.getPaidState()));
            } else {
                requestAdvertMap.put(key, new RequestInfoDTO(request, request.getPaidState()));
            }
        }

        return new ArrayList<>(requestAdvertMap.values());
    }

    public RequestDTO getRequestById(Long id){
        Request request =  requestRepository.getOne(id);

        return new RequestDTO(request);
    }

    public Request acceptRequest(Long requestId) throws CustomException{

        Request request = requestRepository.getOne(requestId);
        if(request == null){
            throw new CustomException("There is no request with that id", HttpStatus.BAD_REQUEST);
        }

        if(checkRequest(request.getCreationDate(), 24))
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

    public Request payRequest(Long requestId) throws CustomException{
        Request request = requestRepository.findById(requestId).get();
        request.setPaidState(PaidState.PAID);
        requestRepository.save(request);

        List<Request> requests = requestRepository.findAllPendingByAdIdExceptOne(request.getAdvertisement().getId(), requestId);
        for(Request r : requests) {
            r.setPaidState(PaidState.CANCELED);
        }

        requestRepository.saveAll(requests);

        return request;
    }

    public RequestContainer payBundleRequest(Long bundleId) throws CustomException{
        RequestContainer requestContainer = requestContainerRepository.findById(bundleId).get();

        List<Request> requestsToBePayed = requestContainer.getBoundleList();

        List<Request> paidRequests = requestsToBePayed.stream().map(request -> {request.setPaidState(PaidState.PAID); return  request;}).collect(Collectors.toList());
        requestContainer.setBoundleList(paidRequests);

        requestContainerRepository.save(requestContainer);

        List<Long> bundleAdIds = requestsToBePayed.stream().map(request -> request.getAdvertisement().getId()).collect(Collectors.toList());
        List<Long> requestToBeExcluded = requestsToBePayed.stream().map(request -> request.getId()).collect(Collectors.toList());

        List<Request> requests = requestRepository.findAllByAllAdIds(bundleAdIds, requestToBeExcluded);

        for(Request r : requests) {
            r.setPaidState(PaidState.CANCELED);
        }

        requestRepository.saveAll(requests);

        return requestContainer;
    }

    public List<RequestBundleDTO> getAllRequestsInBundle(Long requestId) throws CustomException {
        List<Request> requests = requestContainerRepository.getRequestsInBundle(requestId);
        if (requests.isEmpty())
            throw new CustomException("This request is not in any bundle", HttpStatus.BAD_REQUEST);

        return requests.stream().map(request -> new RequestBundleDTO(request)).collect(Collectors.toList());
    }

    public List<Request> getRequestsInBundle(Long requestId) {
        return requestContainerRepository.getRequestsInBundle(requestId);
    }

    public List<Request> acceptBundle(List<Request> requests) throws CustomException {

        for(Request r : requests){
            if (checkRequest(r.getEndDate(), 0))
                r.setPaidState(PaidState.RESERVED);
            else {
                requestRepository.saveAll(requests.stream().map(request -> {request.setPaidState(PaidState.CANCELED); return request;}).collect(Collectors.toList()));
                throw new CustomException("This bundle request is no longer valid", HttpStatus.BAD_REQUEST);
            }
        }

        return requestRepository.saveAll(requests);
    }

    public List<Request> declineBundle(List<Request> requests) {

        for(Request r : requests){
            r.setPaidState(PaidState.CANCELED);
        }
        return requestRepository.saveAll(requests);
    }

    public List<RequestDTO> getAllCreatedRequests(String email) {
        List<Request> requests = requestRepository.findAllByUserSentRequestEmail(email);

        List<RequestDTO> requestDTOs = new ArrayList<>();
        for(Request request : requests) {
            requestDTOs.add(new RequestDTO(request));
        }

        return requestDTOs;
    }

    public RequestContainerDTO getRequestContainer(Long containerId){

        RequestContainer requestContainer = requestContainerRepository.findById(containerId).get();

        return new RequestContainerDTO(requestContainer);
    }

    public List<Request> getAllPaid(String email) {
        return requestRepository.findAllPaid(email);
    }

}
