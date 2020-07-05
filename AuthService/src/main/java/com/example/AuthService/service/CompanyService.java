package com.example.AuthService.service;

import com.example.AuthService.domain.Company;
import com.example.AuthService.domain.CompanyStatus;
import com.example.AuthService.domain.User;
import com.example.AuthService.dto.CompanyDTO;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.repository.CompanyRepository;
import com.example.AuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Company> findAllPending(){
        return companyRepository.findAllByCompanyStatusPending();
    }

    public Company acceptRequest(Long id) throws CustomException {
        Company company = companyRepository.findById(id).get();

        User user = company.getUser();
        user.setBlockedPrivileges(new ArrayList<>());

        company.setCompanyStatus(CompanyStatus.ACTIVE);
        userRepository.save(user);

        return companyRepository.save(company);
    }

    public Company declineRequest(Long id){
        Company company = companyRepository.findById(id).get();

        company.setCompanyStatus(CompanyStatus.DENIED);

        return companyRepository.save(company);
    }

    public Company getCompany(Long userId) throws Exception {
        Company company = companyRepository.findCompanyByUserId(userId);
        if(company == null)
            throw new Exception("Company not found");
        return company;
    }

    public Company reregisterCompany(User user, CompanyDTO companyDTO) throws CustomException {
        Company company = user.getCompany();
        company.setNewCompanyInformation(companyDTO);
        return companyRepository.save(company);
    }

    public CompanyStatus getCompanyStatus(Long userId) throws CustomException {
        User user = userRepository.findById(userId).get();

        if(!user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_AGENT")))
            throw new CustomException("Forbiden", HttpStatus.FORBIDDEN);

        return user.getCompany().getCompanyStatus();
    }
}
