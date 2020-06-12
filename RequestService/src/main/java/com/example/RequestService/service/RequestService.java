package com.example.RequestService.service;

import com.example.RequestService.domain.PaidState;
import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import com.example.RequestService.dto.RequestContainerDTO;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.dto.RequestInfoDTO;
import com.example.RequestService.dto.ReservationPeriodDTO;
import com.example.RequestService.exception.CustomException;
import com.example.RequestService.repository.RequestContainerRepository;
import com.example.RequestService.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestContainerRepository requestContainerRepository;

    public Request add(RequestDTO requestDTO) throws CustomException {

        Request request = new Request(requestDTO);
        request.setAdId(requestDTO.getId());

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

        for(RequestDTO requestDTO : requestContainerDTO.getRequestDTOS()){
            requestDTO.setFreeFrom(getMidnightStartDate(requestDTO.getFreeFrom()).getTime());
            requestDTO.setFreeTo(getMidnightEndDate(requestDTO.getFreeTo()).getTime());
            Request request = new Request(requestDTO);
            if(request == null)
                throw new CustomException("Could not create request in bundle", HttpStatus.BAD_REQUEST);
            requestContainer.getBoundleList().add(requestRepository.save(new Request(requestDTO)));
        }

        return requestContainerRepository.save(requestContainer);
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

    private boolean checkBundleRequest(List<RequestDTO> requestDTOS){

        for(int i = 0; i < requestDTOS.size() - 1; i++){
            RequestDTO tempRequest = requestDTOS.get(i);

            if(tempRequest.getUserSentRequest().equals(tempRequest.getUserEmail())){
                return false;
            }

            for(int j = i + 1; i < requestDTOS.size(); j++){
                RequestDTO requestDTO = requestDTOS.get(j);

                System.out.println("=================================");
                System.out.println("=====> 1. " + tempRequest.getId());
                System.out.println("=====> 2. " + requestDTO.getId());
                System.out.println("=================================");

                if(tempRequest.getId() == requestDTO.getId())
                    return false;

                if(!tempRequest.getUserEmail().equals(requestDTO.getUserEmail()))
                    return false;

                if(!tempRequest.getUserSentRequest().equals(requestDTO.getUserSentRequest()))
                    return false;

                if(tempRequest.getUserSentRequest().equals(tempRequest.getUserEmail()) || requestDTO.getUserSentRequest().equals(requestDTO.getUserEmail()))
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

    public List<RequestInfoDTO> getAllRequestsInfo(String username) throws CustomException {

        List<RequestInfoDTO> requestInfoDTOList = new ArrayList<>();
        List<Request> requestList = requestRepository.findAllByUserEmail(username);

        if(requestList == null || requestList.isEmpty()){
            throw new CustomException("You don't have any requests.", HttpStatus.OK);
        }

        for(Request request : requestList){


            RequestInfoDTO requestInfoDTO = new RequestInfoDTO(request.getAdId());

            if(requestInfoDTOList.isEmpty()){
                requestInfoDTOList.add(requestInfoDTO);
                switch (request.getPaidState()){
                    case PENDING:
                        requestInfoDTO.setPendingRequestNum(requestInfoDTO.getPendingRequestNum() + 1);
                        break;
                    case RESERVED:
                        requestInfoDTO.setAcceptedRequestNum(requestInfoDTO.getPendingRequestNum() + 1);
                        break;
                    case CANCELED:
                        requestInfoDTO.setCanceledRequestNum(requestInfoDTO.getCanceledRequestNum() + 1);
                        break;
                    default:
                        break;
                }
            }else{
                int index = 0;
                boolean found = false;
                for(RequestInfoDTO requestInfo : requestInfoDTOList){
                    index = requestInfoDTOList.indexOf(requestInfo);
                    if(requestInfo.getAdId() == request.getAdId()){
                        found = true;
                        requestInfoDTO = requestInfo;
                        switch (request.getPaidState()){
                            case PENDING:
                                requestInfoDTO.setPendingRequestNum(requestInfoDTO.getPendingRequestNum() + 1);
                                break;
                            case RESERVED:
                                requestInfoDTO.setAcceptedRequestNum(requestInfoDTO.getPendingRequestNum() + 1);
                                break;
                            case CANCELED:
                                requestInfoDTO.setCanceledRequestNum(requestInfoDTO.getCanceledRequestNum() + 1);
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                }
                if(!found){
                    requestInfoDTOList.add(requestInfoDTO);
                    switch (request.getPaidState()){
                        case PENDING:
                            requestInfoDTO.setPendingRequestNum(requestInfoDTO.getPendingRequestNum() + 1);
                            break;
                        case RESERVED:
                            requestInfoDTO.setAcceptedRequestNum(requestInfoDTO.getPendingRequestNum() + 1);
                            break;
                        case CANCELED:
                            requestInfoDTO.setCanceledRequestNum(requestInfoDTO.getCanceledRequestNum() + 1);
                            break;
                        default:
                            break;
                    }
                }else{
                    requestInfoDTOList.remove(index);
                    requestInfoDTOList.add(requestInfoDTO);
                }
            }
        }
        return requestInfoDTOList;
    }

    public List<Request> getAllRequestsForAd(Long adId) throws CustomException {

        List<Request> requestList = requestRepository.findAllByAdId(adId);
        if(requestList.isEmpty() || requestList == null){
            throw new CustomException("No requests for this advertisement", HttpStatus.BAD_REQUEST);
        }

        return requestList;
    }
}
