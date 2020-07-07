package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.PriceList;
import com.example.AgentApplication.dto.PriceListDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    public PriceList addNewPriceList(PriceListDTO priceListDTO) throws CustomException {
        List<PriceList> priceLists = priceListRepository.checkIfDatesAreOverlaping(priceListDTO.getValidFrom(), priceListDTO.getValidTo());
        if(!priceLists.isEmpty())
            throw new CustomException("Dates of pricelists are overlaping.", HttpStatus.NOT_ACCEPTABLE);

        PriceList priceList = new PriceList(priceListDTO);

        return priceListRepository.save(priceList);
    }

    public List<PriceList> getPricelists(){
        return priceListRepository.findAll();
    }
}
