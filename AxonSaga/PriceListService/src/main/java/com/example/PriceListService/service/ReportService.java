package com.example.PriceListService.service;

import com.example.PriceListService.domain.AddedPrice;
import com.example.PriceListService.domain.PriceList;
import com.example.PriceListService.domain.PriceListItem;
import com.example.PriceListService.domain.Report;
import com.example.PriceListService.dto.ReportDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.repository.AddedPriceRepository;
import com.example.PriceListService.repository.PriceListItemRepository;
import com.example.PriceListService.repository.PriceListRepository;
import com.example.PriceListService.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private PriceListItemRepository priceListItemRepository;

    @Autowired
    private AddedPriceRepository addedPriceRepository;

    public Report getReportByRequestId(Long id){
        return reportRepository.getReportByRequestId(id);
    }

    public Report addNewReport(ReportDTO reportDTO, String userEmail) throws CustomException {
        Report report = new Report(reportDTO);
        report.setUserEmail(userEmail);

        if(reportRepository.getReportByRequestId(reportDTO.getRequestId()) != null)
            throw new CustomException("Report for this request already exists.", HttpStatus.NOT_ACCEPTABLE);
//oduzeti km restriction
        List<PriceList> priceLists = priceListRepository.getPricelistsForCalculating(reportDTO.getRentedFrom(), reportDTO.getRentedTo());
        if(!priceLists.isEmpty() && priceLists.size() == 1){
            PriceListItem priceListItem = priceListItemRepository.checkAdvertisementIdExists(priceLists.get(0).getId(), report.getAdId());
            if(priceListItem.getPricePerKm()>0){
                AddedPrice addedPrice = new AddedPrice(reportDTO.getUserEmailRented());
                addedPrice.setPrice(priceListItem.getPricePerKm() * reportDTO.getKm());
                addedPriceRepository.save(addedPrice);
        }
        }else if(priceLists.size()>1){
            float price = 0;
            for(PriceList priceList: priceLists){
                if(priceList.getValidFrom().before( reportDTO.getRentedFrom()) && priceList.getValidTo().before(report.getRentedTo())){
                   long diff = Math.abs(reportDTO.getRentedFrom().getTime() - priceList.getValidTo().getTime());
                   PriceListItem priceListItem = priceListItemRepository.checkAdvertisementIdExists(priceList.getId(), report.getAdId());
                   if(priceListItem != null && priceListItem.getPricePerKm()>0);
                        price += diff * priceListItem.getPricePerKm();
                }else if(reportDTO.getRentedFrom().before(priceList.getValidFrom()) && reportDTO.getRentedTo().after(priceList.getValidTo())){
                    long diff = Math.abs(priceList.getValidFrom().getTime() - priceList.getValidTo().getTime());
                    PriceListItem priceListItem = priceListItemRepository.checkAdvertisementIdExists(priceList.getId(), report.getAdId());
                    if(priceListItem != null && priceListItem.getPricePerKm()>0);
                        price += diff * priceListItem.getPricePerKm();
                }else if(priceList.getValidFrom().after( reportDTO.getRentedFrom()) &&  priceList.getValidTo().after(report.getRentedTo())){
                    long diff = Math.abs(priceList.getValidFrom().getTime() - reportDTO.getRentedTo().getTime());
                    PriceListItem priceListItem = priceListItemRepository.checkAdvertisementIdExists(priceList.getId(), report.getAdId());
                    if(priceListItem != null && priceListItem.getPricePerKm()>0);
                        price += diff * priceListItem.getPricePerKm();
                }
            }
            AddedPrice addedPrice = new AddedPrice(reportDTO.getUserEmailRented());
            addedPrice.setPrice(price);
            addedPriceRepository.save(addedPrice);
        }

        return reportRepository.save(report);
    }

}
