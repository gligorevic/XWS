package com.example.AgentApplication.service;

import com.baeldung.soap.ws.client.generated.*;
import com.example.AgentApplication.domain.Advertisement;
import com.example.AgentApplication.domain.PriceList;
import com.example.AgentApplication.domain.PriceListItem;
import com.example.AgentApplication.dto.PriceListItemDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.AdvertisementRepository;
import com.example.AgentApplication.repository.PriceListItemRepository;
import com.example.AgentApplication.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceListItemService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private PriceListItemRepository priceListItemRepository;

    @Autowired
    private PriceListRepository priceListRepository;


    public PriceListItem addNewPriceListItem(PriceListItemDTO priceListItemDTO) throws CustomException {
        if(priceListItemDTO.getAdvertisementId() == null || priceListItemDTO.getAdvertisementId().equals(""))
            throw new CustomException("Wrong input.", HttpStatus.BAD_REQUEST);
        PriceListItem priceListItem = new PriceListItem(priceListItemDTO);
        PriceList priceList = priceListRepository.findById(priceListItemDTO.getPriceListId()).get();
        if(priceList == null)
            throw new CustomException("Price list doesn't exist.", HttpStatus.BAD_REQUEST);

        if(priceListItemRepository.checkAdvertisementIdExists(priceListItemDTO.getPriceListId(), priceListItemDTO.getAdvertisementId()) != null)
            throw new CustomException("Price List item for advertisement already exists.", HttpStatus.NOT_ACCEPTABLE);

        Advertisement advertisement = advertisementRepository.findAdvertisementById(priceListItemDTO.getAdvertisementId());
        if(advertisement == null)
            throw new CustomException("Advertisement doesn't exist.", HttpStatus.BAD_REQUEST);

        priceListItem.setPriceList(priceList);

        PricelistPortService service = new PricelistPortService();
        PricelistPort pricelistPort = service.getPricelistPortSoap11();
        GetPricelistItemRequest request = new GetPricelistItemRequest();
        try {
            PricelistItem pricelistItem = new PricelistItem(priceListItem, advertisement.getRemoteId());
            request.setPricelistItem(pricelistItem);
            GetPricelistItemResponse response = pricelistPort.getPricelistItem(request);
            priceListItem.setRemoteId(response.getId());
        }catch (Exception e){
            System.out.println("Mikroservis ne radi");
        }

        return priceListItemRepository.save(priceListItem);
    }

    public List<PriceListItemDTO> getPriceListItems(Long priceListId){
        List<PriceListItemDTO> list = new ArrayList<>();
        priceListItemRepository.findAllByPriceListId(priceListId).stream().forEach(priceListItem -> {list.add(new PriceListItemDTO(priceListItem));});
        return list;
    }
}
