package com.example.PriceListService.service;

import com.example.PriceListService.domain.Discount;
import com.example.PriceListService.domain.PriceList;
import com.example.PriceListService.domain.PriceListItem;
import com.example.PriceListService.dto.PriceListItemDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.repository.DiscountRepository;
import com.example.PriceListService.repository.PriceListItemRepository;
import com.example.PriceListService.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListItemService {

    @Autowired
    private PriceListItemRepository priceListItemRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private DiscountRepository discountRepository;

    public PriceListItem addNewPriceListItem(PriceListItemDTO priceListItemDTO) throws CustomException {
        PriceListItem priceListItem = new PriceListItem(priceListItemDTO);
        PriceList priceList = priceListRepository.findById(priceListItemDTO.getPriceListId()).get();
        if(priceList == null){
            throw new CustomException("Price list doesn't exist.", HttpStatus.BAD_REQUEST);
        }
        priceListItem.setPriceList(priceList);
        Discount discount = new Discount(priceListItemDTO.getMinNumberDays(), priceListItemDTO.getPercentage());
        discountRepository.save(discount);
        priceListItem.setDiscount(discount);
        return priceListItemRepository.save(priceListItem);
    }

    public List<PriceListItem> getPriceListItems(Long priceListId){
        return priceListItemRepository.findAllByPriceListId(priceListId);
    }
}
