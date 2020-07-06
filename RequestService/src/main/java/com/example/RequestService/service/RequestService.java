package com.example.RequestService.service;

import com.example.RequestService.domain.PaidState;
import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import com.example.RequestService.dto.*;
import com.example.RequestService.exception.CustomException;
import com.example.RequestService.repository.RequestContainerRepository;
import com.example.RequestService.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequestService {


    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestContainerRepository requestContainerRepository;

    public Request add(RequestDTO requestDTO) throws CustomException {

        requestDTO.setInBundle(false);
        Request request = new Request(requestDTO);
        request.setAdId(requestDTO.getId());

        if (request.getUserEmail().equals(request.getUserSentRequest()))
            throw new CustomException("You can't send request to yourself", HttpStatus.BAD_REQUEST);

        return requestRepository.save(request);
    }

    public RequestContainer addBundle(RequestContainerDTO requestContainerDTO) throws CustomException {

        RequestContainer requestContainer = new RequestContainer(requestContainerDTO);

        if (requestContainer == null)
            throw new CustomException("Could not create bundle request", HttpStatus.BAD_REQUEST);

        if (!checkBundleRequest(requestContainerDTO.getRequestDTOS()))
            throw new CustomException("Something is wrong with this bundle request", HttpStatus.BAD_REQUEST);

        requestContainerRepository.save(requestContainer);
        for (RequestDTO requestDTO : requestContainerDTO.getRequestDTOS()) {
            requestDTO.setFreeFrom(getMidnightStartDate(requestDTO.getFreeFrom()).getTime());
            requestDTO.setFreeTo(getMidnightEndDate(requestDTO.getFreeTo()).getTime());
            requestDTO.setInBundle(true);
            Request request = new Request(requestDTO);
            request.setRequestContainer(requestContainer);
            if (request == null)
                throw new CustomException("Could not create request in bundle", HttpStatus.BAD_REQUEST);
            requestContainer.getBoundleList().add(requestRepository.save(request));
        }

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

    public List<Request> cancelRequestsReservationPeriod(ReservationPeriodDTO reservationPeriodDTO) {
        List<Request> requests = requestContainerRepository.getRequestsFromBundle(reservationPeriodDTO.getStartDate(), reservationPeriodDTO.getEndDate(), reservationPeriodDTO.getAdvertisementId());
        if (!requests.isEmpty()) {
            for (Request r : requests) {
                r.setPaidState(PaidState.CANCELED);
            }
            requestRepository.saveAll(requests);
        }
        return requests;
    }

    public List<RequestInfoDTO> getAllRequestsInfoByReciverUsername(String username) throws CustomException {
        List<Request> requestList = requestRepository.findAllByUserEmail(username);

        Map<Long, RequestInfoDTO> requestAdvertMap = new HashMap<>();

        for (Request request : requestList) {
            Long key = request.getAdId();
            if (requestAdvertMap.containsKey(key)) {
                RequestInfoDTO value = requestAdvertMap.get(key);
                requestAdvertMap.replace(key, value.append(request.getPaidState()));
            } else {
                requestAdvertMap.put(key, new RequestInfoDTO(request, request.getPaidState()));
            }
        }

        return new ArrayList<>(requestAdvertMap.values());
    }

    public List<Request> getAllRequestsForAd(Long adId, String userEmail) throws CustomException {

        List<Request> requestList = requestRepository.findAllByAdId(adId);
        if (requestList.isEmpty() || requestList == null) {
            throw new CustomException("No requests for this advertisement", HttpStatus.BAD_REQUEST);
        }

        if (!userEmail.equals(requestList.get(0).getUserEmail())) {
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return requestList;
    }

    public RequestDTO getRequestById(Long id) {
        Request request = requestRepository.getOne(id);

        return new RequestDTO(request);
    }

    public Request acceptRequest(Long requestId) throws CustomException {

        Request request = requestRepository.getOne(requestId);
        if (request == null) {
            throw new CustomException("There is no request with that id", HttpStatus.BAD_REQUEST);
        }

        if (checkRequest(request.getCrationDate(), 24))
            request.setPaidState(PaidState.RESERVED);
        else {
            request.setPaidState(PaidState.CANCELED);
            requestRepository.save(request);
            throw new CustomException("This request is no longer valid", HttpStatus.BAD_REQUEST);
        }

        return requestRepository.save(request);
    }

    public Request declineRequest(Long requestId) throws CustomException {
        Request request = requestRepository.getOne(requestId);
        if (request == null) {
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

    public List<RequestBundleDTO> getAllRequestsInBundle(Long requestId) throws CustomException {
        List<Request> requests = requestContainerRepository.getRequestsInBundle(requestId);
        if (requests.isEmpty())
            throw new CustomException("This request is not in any bundle", HttpStatus.BAD_REQUEST);

        return requests.stream().map(request -> new RequestBundleDTO(request)).collect(Collectors.toList());
    }

    public boolean requestInBundle(Long requestId) {
        List<Request> requests = requestContainerRepository.getRequestsInBundle(requestId);
        if (requests == null || requests.isEmpty())
            return false;
        return true;
    }

    public List<Request> getRequestsInBundle(Long requestId) {
        return requestContainerRepository.getRequestsInBundle(requestId);
    }

    @Scheduled(cron = "0 0 * ? * *")
    public void requestCleaner() {

        System.out.println("Cleaning init...");

        List<Request> pendingRequests = requestRepository.findAllPending();
        List<Request> reservedRequests = requestRepository.findAllReserved();

        if (!pendingRequests.isEmpty()) {
            for (Request request : pendingRequests) {
                if (!checkRequest(request.getCrationDate(), 24)) {
                    System.out.println("Cleaned: " + request.getId());
                    request.setPaidState(PaidState.CANCELED);
                    requestRepository.save(request);
                }
            }
        }

        if (!reservedRequests.isEmpty()) {
            for (Request request : reservedRequests) {
                if (!checkRequest(request.getCrationDate(), 12)) {
                    System.out.println("Cleaned: " + request.getId());
                    request.setPaidState(PaidState.CANCELED);
                    requestRepository.save(request);
                }
            }
        }
    }

    public boolean checkRequest(Date dateToCheck, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToCheck);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        dateToCheck = calendar.getTime();
        Date now = new Date();

        if (dateToCheck.compareTo(now) > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Request payRequest(Long requestId, String userEmail) throws CustomException {
        Request request = requestRepository.findById(requestId).get();
        if (request == null || !request.getUserSentRequest().equals(userEmail))
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
        request.setPaidState(PaidState.PAID);
        requestRepository.save(request);

        List<Request> requests = requestRepository.findAllPendingByAdIdExceptOne(request.getAdId(), requestId);
        for (Request r : requests) {
            if (checkIfRequestDatesOverlapping(r, request))
                if (r.isInBundle()) changeRequestsState(r.getRequestContainer(), PaidState.CANCELED);
                else r.setPaidState(PaidState.CANCELED);
        }

        requestRepository.saveAll(requests);

        return request;
    }

    public RequestContainer payBundleRequest(Long bundleId, String userEmail) throws CustomException {
        RequestContainer requestContainer = requestContainerRepository.findById(bundleId).get();

        if (requestContainer == null || !requestContainer.getUserSentRequest().equals(userEmail))
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);

        List<Request> requestsToBePayed = requestContainer.getBoundleList();

        changeRequestsState(requestContainer, PaidState.PAID);

        List<Long> bundleAdIds = requestsToBePayed.stream().map(request -> request.getAdId()).collect(Collectors.toList());
        List<Long> requestToBeExcluded = requestsToBePayed.stream().map(request -> request.getId()).collect(Collectors.toList());

        Map<Long, Request> requestsMap = new HashMap<>();
        for(Request r : requestsToBePayed) {
            requestsMap.put(r.getAdId(), r);
        }

        List<Request> requests = requestRepository.findAllByAllAdIds(bundleAdIds, requestToBeExcluded);
        for (Request r : requests) {
            if(checkIfRequestDatesOverlapping(requestsMap.get(r.getAdId()), r))
                if (r.isInBundle()) changeRequestsState(r.getRequestContainer(), PaidState.CANCELED);
                else r.setPaidState(PaidState.CANCELED);
        }

        requestRepository.saveAll(requests);

        return requestContainer;
    }


    private boolean checkIfRequestDatesOverlapping(Request request, Request r) {
        if (r.getStartDate().compareTo(request.getStartDate()) <= 0 && r.getEndDate().compareTo(request.getStartDate()) >= 0) {
            return true;
        }
        if (r.getStartDate().compareTo(request.getEndDate()) <= 0 && r.getEndDate().compareTo(request.getEndDate()) >= 0) {
            return true;
        }
        if (r.getStartDate().compareTo(request.getStartDate()) >= 0 && r.getEndDate().compareTo(request.getEndDate()) <= 0) {
            return true;
        }
        return false;
    }

    public Request cancelRequest(Long requestId, String userEmail) throws CustomException {
        Request request = requestRepository.findById(requestId).get();
        if (request == null || !request.getUserSentRequest().equals(userEmail))
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
        request.setPaidState(PaidState.CANCELED);
        return requestRepository.save(request);
    }

    public RequestContainer cancelBundleRequest(Long bundleId, String userEmail) throws CustomException {
        RequestContainer requestContainer = requestContainerRepository.findById(bundleId).get();
        if (requestContainer == null || !requestContainer.getUserSentRequest().equals(userEmail))
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);

        changeRequestsState(requestContainer, PaidState.CANCELED);

        return requestContainer;
    }

    private void changeRequestsState(RequestContainer requestContainer, PaidState padiState) {
        List<Request> canceledRequests = requestContainer.getBoundleList().stream().map(request -> {
            request.setPaidState(padiState);
            return request;
        }).collect(Collectors.toList());
        requestContainer.setBoundleList(canceledRequests);

        requestContainerRepository.save(requestContainer);
    }
}
