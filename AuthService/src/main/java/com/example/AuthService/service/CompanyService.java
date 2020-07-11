package com.example.AuthService.service;

import com.example.AuthService.MQConfig.ChannelBinding;
import com.example.AuthService.domain.Company;
import com.example.AuthService.domain.CompanyStatus;
import com.example.AuthService.domain.User;
import com.example.AuthService.dto.CompanyDTO;
import com.example.AuthService.dto.EmailMessage;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.repository.CompanyRepository;
import com.example.AuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    private MessageChannel email;

    public CompanyService(ChannelBinding channelBinding) {
        this.email = channelBinding.mailing();
    }

    public List<Company> findAllPending(){
        return companyRepository.findAllByCompanyStatusPending();
    }

    public Company acceptRequest(Long id) throws CustomException {
        Company company = companyRepository.findById(id).get();

        User user = company.getUser();
        user.setBlockedPrivileges(new ArrayList<>());

        company.setCompanyStatus(CompanyStatus.ACTIVE);
        userRepository.save(user);

        Company acceptedCompany = companyRepository.save(company);

        Message<EmailMessage> msg = MessageBuilder.withPayload(new EmailMessage(user.getEmail(), "Accepted company request", "Company request has been accepted.")).build();
        this.email.send(msg);

        return acceptedCompany;
    }

    public Company declineRequest(Long id){
        Company company = companyRepository.findById(id).get();

        company.setCompanyStatus(CompanyStatus.DENIED);

        Company declinedCompany = companyRepository.save(company);

        Message<EmailMessage> msg = MessageBuilder.withPayload(new EmailMessage(company.getUser().getEmail(), "Declined company request", "Company request has been declined. You can change company information and resend company registration request.")).build();
        this.email.send(msg);

        return declinedCompany;
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
