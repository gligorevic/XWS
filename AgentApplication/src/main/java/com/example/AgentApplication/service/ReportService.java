package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Car;
import com.example.AgentApplication.domain.Report;
import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.dto.ReportDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.CarRepository;
import com.example.AgentApplication.repository.ReportRepository;
import com.example.AgentApplication.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private CarRepository carRepository;

    public Report getReportByRequestId(Long id){
        return reportRepository.getReportByRequestId(id);
    }

    public Report addNewReport(ReportDTO reportDTO) throws CustomException {
        Report report = new Report(reportDTO);
        Request request = requestRepository.getOne(reportDTO.getRequestId());

        if(request == null)
            throw new CustomException("Request doesn't exist.", HttpStatus.BAD_REQUEST);

        if(reportRepository.getReportByRequestId(reportDTO.getRequestId()) != null)
            throw new CustomException("Report for this request already exists.", HttpStatus.NOT_ACCEPTABLE);

        Car car = carRepository.findCarById(request.getAdvertisement().getCar().getId());
        car.setKmPassed(car.getKmPassed() + reportDTO.getKm());

        report.setRequest(request);
        return reportRepository.save(report);
    }

}
