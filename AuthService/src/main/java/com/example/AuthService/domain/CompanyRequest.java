package com.example.AuthService.domain;

import com.example.AuthService.dto.CompanyDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CompanyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String companyName;
    private String reqistrationNumber;
    private String address;
    private String phoneNumber;

    public CompanyRequest() {
    }


    public CompanyRequest(CompanyDTO companyDTO) {
        this.companyName = companyDTO.getCompanyName();
        this.reqistrationNumber = companyDTO.getRegistrationNumber();
        this.address = companyDTO.getAddress();
        this.phoneNumber = companyDTO.getPhoneNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReqistrationNumber() {
        return reqistrationNumber;
    }

    public void setReqistrationNumber(String reqistrationNumber) {
        this.reqistrationNumber = reqistrationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
