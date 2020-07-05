package com.example.AuthService.dto;

public class AgentRegistrationDTO {

    private UserDTO userDTO;
    private CompanyDTO companyDTO;

    public AgentRegistrationDTO() {
    }

    public AgentRegistrationDTO(UserDTO userDTO, CompanyDTO companyDTO) {
        this.userDTO = userDTO;
        this.companyDTO = companyDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }
}
