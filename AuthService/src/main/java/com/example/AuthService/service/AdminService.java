package com.example.AuthService.service;

import com.example.AuthService.domain.Privilege;
import com.example.AuthService.domain.User;
import com.example.AuthService.dto.UserDTO;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.repository.PrivilegeRepository;
import com.example.AuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public boolean changeUserPrivileges(List<Long> privilegeList, Long enduserId) throws CustomException {
        User user = userRepository.getOne(enduserId);

        if(user == null) {
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
        }

        List<Privilege> privileges =  privilegeRepository.findAllById(privilegeList);
        user.setBlockedPrivileges(privileges);

        userRepository.save(user);

        return true;
    }

    public Boolean toggleBlockUser(Long userId) throws CustomException {
        User user = userRepository.getOne(userId);

        if(user == null)
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);

        user.toggleBlock();
        userRepository.save(user);

        return true;
    }

    public UserDTO logicalDeleteUser(Long userId) throws CustomException {
        User user = userRepository.getOne(userId);

        if(user == null)
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);

        user.logicalDelete();
        userRepository.save(user);

        return new UserDTO(user);
    }

    public List<Privilege> getBlockedPrivilegesByUserId(String userId) {
        List<Privilege> blockedPrivileges = privilegeRepository.findBlockedPrivilegesByUserId(Long.parseLong(userId));

        return blockedPrivileges;
    }
}
