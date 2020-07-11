package com.example.PriceListService.service;

import com.baeldung.springsoap.gen.Pricelist;
import com.example.PriceListService.domain.PriceList;
import com.example.PriceListService.dto.PriceListDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    public PriceList addNewPriceList(PriceListDTO priceListDTO, String userEmail) throws CustomException {
        List<PriceList> priceLists = priceListRepository.checkIfDatesAreOverlaping(priceListDTO.getValidFrom(), priceListDTO.getValidTo(), userEmail);
        if(!priceLists.isEmpty())
            throw new CustomException("Dates of pricelists are overlaping.", HttpStatus.NOT_ACCEPTABLE);

        PriceList priceList = new PriceList(priceListDTO);
        priceList.setUserEmail(userEmail);
        return priceListRepository.save(priceList);
    }

    public List<PriceList> getPricelistsByUserEmail(String userEmail){
        return priceListRepository.findAllByUserEmail(userEmail);
    }

    public Long addPricelistAgent(Pricelist pricelist) throws CustomException {
        PriceListDTO priceListDTO = new PriceListDTO(pricelist);
        PriceList p = addNewPriceList(priceListDTO, pricelist.getUserEmail());
        return p.getId();
    }
}
