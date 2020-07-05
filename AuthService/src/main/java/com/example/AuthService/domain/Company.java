package com.example.AuthService.domain;

import com.example.AuthService.dto.CompanyDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String companyName;
    private String registrationNumber;
    private String address;
    private String phoneNumber;

    private CompanyStatus companyStatus;

    public Company() {
    }

    public Company(CompanyDTO companyDTO) {
        this.companyName = companyDTO.getCompanyName();
        this.registrationNumber = companyDTO.getRegistrationNumber();
        this.address = companyDTO.getAddress();
        this.phoneNumber = companyDTO.getPhoneNumber();
        this.companyStatus = CompanyStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String reqistrationNumber) {
        this.registrationNumber = reqistrationNumber;
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

    public CompanyStatus getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(CompanyStatus companyStatus) {
        this.companyStatus = companyStatus;
    }

    public void setNewCompanyInformation(CompanyDTO companyDTO) {
        this.companyName = companyDTO.getCompanyName();
        this.registrationNumber = companyDTO.getRegistrationNumber();
        this.address = companyDTO.getAddress();
        this.phoneNumber = companyDTO.getPhoneNumber();
        this.companyStatus = CompanyStatus.PENDING;
    }
}
