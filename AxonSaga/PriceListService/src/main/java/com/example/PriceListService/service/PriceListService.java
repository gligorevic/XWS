package com.example.PriceListService.service;

import com.example.PriceListService.client.AdvertisementClient;
import com.example.PriceListService.domain.PriceList;
import com.example.PriceListService.domain.PriceListItem;
import com.example.PriceListService.dto.DataForPriceCalculationDTO;
import com.example.PriceListService.dto.PriceListDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.repository.PriceListItemRepository;
import com.example.PriceListService.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private PriceListItemRepository priceListItemRepository;


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

    public Float calculatePriceForRequest(DataForPriceCalculationDTO dataForPriceCalculationDTO) {
        List<PriceListItem> foundPriceListItems = priceListItemRepository.findPriceListItemForSpecificPeriod(dataForPriceCalculationDTO.getAdId(), dataForPriceCalculationDTO.getDateFrom(), dataForPriceCalculationDTO.getDateTo());
        Float finalPrice = 0f;
        int diffInDays = (int)( (dataForPriceCalculationDTO.getDateTo().getTime() - dataForPriceCalculationDTO.getDateFrom().getTime()) / (1000 * 60 * 60 * 24) );
        for (PriceListItem priceListItem : foundPriceListItems) {
            if (priceListItem.getDiscount().getMinNumberDays() <= diffInDays)
                finalPrice += priceListItem.getPricePerDay() - (priceListItem.getPricePerDay() * priceListItem.getDiscount().getPercentage() / 100);
            else
                finalPrice += priceListItem.getPricePerDay();
        }
        return finalPrice * diffInDays;
    }
}
