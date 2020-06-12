package com.example.RequestService.service;

import com.example.RequestService.domain.PaidState;
import com.example.RequestService.domain.Request;
import com.example.RequestService.domain.RequestContainer;
import com.example.RequestService.dto.RequestContainerDTO;
import com.example.RequestService.dto.RequestDTO;
import com.example.RequestService.dto.ReservationPeriodDTO;
import com.example.RequestService.exception.CustomException;
import com.example.RequestService.repository.RequestContainerRepository;
import com.example.RequestService.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

        for(int i = 0; i < requestDTOS.size() - 2; i++){
            RequestDTO tempRequest = requestDTOS.get(i);

            for(int j = i + 1; i < requestDTOS.size() - 1; j++){
                RequestDTO requestDTO = requestDTOS.get(j);

                if(tempRequest.getId() == requestDTO.getId())
                    return false;

                if(!tempRequest.getUserEmail().equals(requestDTO.getUserEmail()))
                    return false;

                if(requestDTO.getUserSentRequest().equals(requestDTO.getUserSentRequest()))
                    return false;
            }
        }
        return true;
    }

    public List<Request> cancelRequestsReservationPeriod(ReservationPeriodDTO reservationPeriodDTO){
        List<Request> requests = requestRepository.getRequestsForCanceling(reservationPeriodDTO.getStartDate(), reservationPeriodDTO.getEndDate(), reservationPeriodDTO.getAdvertisementId());
        if(!requests.isEmpty()) {
            for (Request r : requests) {
                r.setPaidState(PaidState.CANCELED);
            }
            requestRepository.saveAll(requests);
        }
        return requests;
    }
}
