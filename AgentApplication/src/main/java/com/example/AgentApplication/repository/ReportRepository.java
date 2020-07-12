package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAll();

    Report getReportByRequestId(Long id);

}
