package com.example.PriceListService.service;

import com.example.PriceListService.client.AdvertisementClient;
import com.example.PriceListService.domain.AddedPrice;
import com.example.PriceListService.domain.PriceList;
import com.example.PriceListService.domain.PriceListItem;
import com.example.PriceListService.domain.Report;
import com.example.PriceListService.dto.AdvertisementCalculatingDTO;
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

    @Autowired
    private AdvertisementClient advertisementClient;

    public Report getReportByRequestId(Long id){
        return reportRepository.getReportByRequestId(id);
    }

    public Report addNewReport(ReportDTO reportDTO, String userEmail, String auth) throws CustomException {
        Report report = new Report(reportDTO);
        report.setUserEmail(userEmail);

        if(reportRepository.getReportByRequestId(reportDTO.getRequestId()) != null)
            throw new CustomException("Report for this request already exists.", HttpStatus.NOT_ACCEPTABLE);

        AdvertisementCalculatingDTO ad = advertisementClient.getAdvertisemenById(reportDTO.getAdId(), auth).getBody();
        if(ad == null)
            throw new CustomException("Advertisement doesn't exist.", HttpStatus.BAD_REQUEST);

        if(reportDTO.getKm() > ad.getKmRestriction()){
            List<PriceList> priceLists = priceListRepository.getPricelistsForCalculating(reportDTO.getRentedFrom(), reportDTO.getRentedTo(), userEmail);
            if(!priceLists.isEmpty()){
                for(PriceList priceList : priceLists) {
                    PriceListItem priceListItem = priceListItemRepository.checkAdvertisementIdExists(priceList.getId(), report.getAdId());
                    if(priceListItem != null && priceListItem.getPricePerKm()>0){
                        AddedPrice addedPrice = new AddedPrice(reportDTO.getUserEmailRented());
                        addedPrice.setPrice(priceListItem.getPricePerKm() * (reportDTO.getKm() - ad.getKmRestriction()));
                        addedPriceRepository.save(addedPrice);
                        break;
                    }
                }
            }
        }

        return reportRepository.save(report);
    }

    public Long addReportAgent(com.baeldung.springsoap.gen.Report report1) throws CustomException {
        ReportDTO reportDTO = new ReportDTO(report1);
        Report report = new Report(reportDTO);
        report.setUserEmail(report1.getUserEmail());

        if(reportRepository.getReportByRequestId(reportDTO.getRequestId()) != null)
            throw new CustomException("Report for this request already exists.", HttpStatus.NOT_ACCEPTABLE);


        Report saved = reportRepository.save(report);
        return saved.getId();
    }

}
