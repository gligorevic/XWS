package com.example.AuthService.domain;

import com.example.AuthService.dto.CompanyDTO;

import javax.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String companyName;
    private String reqistrationNumber;
    private String address;
    private String phoneNumber;

    public Company() {
    }

    public Company(User user, String companyName, String reqistrationNumber, String address, String phoneNumber) {
        this.user = user;
        this.companyName = companyName;
        this.reqistrationNumber = reqistrationNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
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
