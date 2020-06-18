package com.example.AgentApplication.repository;

import com.example.AgentApplication.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Report getReportByRequestId(Long id);

}
