package com.example.AgentApplication.service;

import com.baeldung.soap.ws.client.generated.*;
import com.example.AgentApplication.domain.PriceList;
import com.example.AgentApplication.dto.PriceListDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;

@Service
public class PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    public PriceList addNewPriceList(PriceListDTO priceListDTO) throws CustomException, DatatypeConfigurationException {
        List<PriceList> priceLists = priceListRepository.checkIfDatesAreOverlaping(priceListDTO.getValidFrom(), priceListDTO.getValidTo());
        if(!priceLists.isEmpty())
            throw new CustomException("Dates of pricelists are overlaping.", HttpStatus.NOT_ACCEPTABLE);

        PriceList priceList = new PriceList(priceListDTO);

        PricelistPortService service = new PricelistPortService();
        PricelistPort pricelistPort = service.getPricelistPortSoap11();
        GetPricelistRequest request = new GetPricelistRequest();
        try {
            Pricelist pricelist = new Pricelist(priceList);
            request.setPricelist(pricelist);
            GetPricelistResponse response = pricelistPort.getPricelist(request);
            priceList.setRemoteId(response.getId());
        }catch (Exception e){
            System.out.println("Mikroservis ne radi");
        }

        return priceListRepository.save(priceList);
    }

    public List<PriceList> getPricelists(){
        return priceListRepository.findAll();
    }
}
