package com.example.AuthService.service;

import com.example.AuthService.domain.Privilege;
import com.example.AuthService.repository.PrivilegeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;

    private static final Logger log = LoggerFactory.getLogger(PrivilegeService.class);

    public List<Privilege> getAllPrivileges() {
        List<Privilege> privileges = privilegeRepository.findAll();
        return privileges;
    }
}
