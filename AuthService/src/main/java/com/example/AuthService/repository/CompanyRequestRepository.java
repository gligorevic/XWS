package com.example.AuthService.repository;

import com.example.AuthService.domain.CompanyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRequestRepository extends JpaRepository<CompanyRequest, Long> {


}
