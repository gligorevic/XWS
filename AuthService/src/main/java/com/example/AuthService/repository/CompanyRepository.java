package com.example.AuthService.repository;

import com.example.AuthService.domain.Company;
import com.example.AuthService.domain.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c where c.companyStatus = com.example.AuthService.domain.CompanyStatus.PENDING")
    List<Company> findAllByCompanyStatusPending();

    Company findCompanyByUserId(Long id);
}
