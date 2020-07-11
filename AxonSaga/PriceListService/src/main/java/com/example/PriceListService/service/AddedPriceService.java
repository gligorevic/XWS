package com.example.PriceListService.service;

import com.example.PriceListService.domain.AddedPrice;
import com.example.PriceListService.enumeration.PaidState;
import com.example.PriceListService.repository.AddedPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddedPriceService {

    @Autowired
    private AddedPriceRepository addedPriceRepository;

    public Boolean checkIfNotPaid(String email){
        if(!addedPriceRepository.getNotPaidAddedPrices(email).isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public List<AddedPrice> getAddedPrices(String email){
        return addedPriceRepository.getNotPaidAddedPrices(email);
    }

    public AddedPrice payAddedPrice(Long id){
        AddedPrice addedPrice = addedPriceRepository.findById(id).get();
        addedPrice.setPaidState(PaidState.PAID);
        return addedPriceRepository.save(addedPrice);
    }
}
