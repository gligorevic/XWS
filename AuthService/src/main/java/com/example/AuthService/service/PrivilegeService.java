package com.example.AuthService.service;

import com.example.AuthService.domain.Privilege;
import com.example.AuthService.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }
}
