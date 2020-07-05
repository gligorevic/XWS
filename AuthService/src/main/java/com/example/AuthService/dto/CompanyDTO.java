package com.example.AuthService.dto;

public class CompanyDTO {

    private Long userId;
    private String companyName;
    private String registrationNumber;
    private String address;
    private String phoneNumber;

    public CompanyDTO() {
    }

    public CompanyDTO(Long userId, String companyName, String reqistrationNumber, String address, String phoneNumber) {
        this.userId = userId;
        this.companyName = companyName;
        this.registrationNumber = reqistrationNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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
