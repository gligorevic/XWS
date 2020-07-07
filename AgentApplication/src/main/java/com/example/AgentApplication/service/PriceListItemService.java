package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.PriceList;
import com.example.AgentApplication.domain.PriceListItem;
import com.example.AgentApplication.dto.PriceListItemDTO;
import com.example.AgentApplication.exception.CustomException;
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

        priceListItem.setPriceList(priceList);

        return priceListItemRepository.save(priceListItem);
    }

    public List<PriceListItemDTO> getPriceListItems(Long priceListId){
        List<PriceListItemDTO> list = new ArrayList<>();
        priceListItemRepository.findAllByPriceListId(priceListId).stream().forEach(priceListItem -> {list.add(new PriceListItemDTO(priceListItem));});
        return list;
    }
}
