package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.enumeration.PaidState;
import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.domain.RequestContainer;
import com.example.AgentApplication.dto.RequestContainerDTO;
import com.example.AgentApplication.dto.RequestDTO;
import com.example.AgentApplication.dto.ReservationPeriodDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.AdvertisementRepository;
import com.example.AgentApplication.repository.RequestContainerRepository;
import com.example.AgentApplication.repository.RequestRepository;
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

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Request add(RequestDTO requestDTO) throws CustomException {
        Request request = new Request(requestDTO);
        Advertisement advertisement = advertisementRepository.findAdvertisementById(requestDTO.getAdId());
        if(advertisement == null)
            throw new CustomException("Advertisement doesn't exist", HttpStatus.BAD_REQUEST);

        request.setAdvertisement(advertisement);
        return requestRepository.save(request);
    }

    public RequestContainer addBundle(RequestContainerDTO requestContainerDTO) throws CustomException {

        RequestContainer requestContainer = new RequestContainer(requestContainerDTO);
        for(RequestDTO requestDTO : requestContainerDTO.getRequestDTOS()){
            requestDTO.setFreeFrom(getMidnightStartDate(requestDTO.getFreeFrom()).getTime());
            requestDTO.setFreeTo(getMidnightEndDate(requestDTO.getFreeTo()).getTime());
            Request request = new Request(requestDTO);
            Advertisement advertisement = advertisementRepository.findAdvertisementById(requestDTO.getAdId());
            if(advertisement == null)
                throw new CustomException("Advertisement doesn't exist", HttpStatus.BAD_REQUEST);
            request.setAdvertisement(advertisement);
            requestContainer.getBoundleList().add(request);
        }
        requestRepository.saveAll(requestContainer.getBoundleList());
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

    public List<Request> getPassedRequests(){
        return requestRepository.getRequestsPassedAndPaid(new Date());
    }

    public List<Request> getReservedRequests() { return  requestRepository.getReservedRequests();}

}
