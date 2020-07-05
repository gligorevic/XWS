package com.example.PriceListService.service;

import com.example.PriceListService.domain.Report;
import com.example.PriceListService.dto.ReportDTO;
import com.example.PriceListService.exception.CustomException;
import com.example.PriceListService.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report getReportByRequestId(Long id){
        return reportRepository.getReportByRequestId(id);
    }

    public Report addNewReport(ReportDTO reportDTO, String userEmail) throws CustomException {
        Report report = new Report(reportDTO);
        report.setUserEmail(userEmail);

        if(reportRepository.getReportByRequestId(reportDTO.getRequestId()) != null)
            throw new CustomException("Report for this request already exists.", HttpStatus.NOT_ACCEPTABLE);

        /*Car car = carRepository.findCarById(request.getAdvertisement().getCar().getId());
        car.setKmPassed(car.getKmPassed() + reportDTO.getKm());*/

        return reportRepository.save(report);
    }

}
