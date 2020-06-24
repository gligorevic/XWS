package com.example.AuthService.service;

import com.example.AuthService.domain.Privilege;
import com.example.AuthService.domain.User;
import com.example.AuthService.dto.UserDTO;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.repository.PrivilegeRepository;
import com.example.AuthService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    public boolean changeUserPrivileges(List<Long> privilegeList, Long enduserId, String adminEmail) throws CustomException {
        User user = userRepository.findById(enduserId).get();

        if (user == null) {
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
        }

        List<Privilege> privileges = privilegeRepository.findAllById(privilegeList);
        user.setBlockedPrivileges(privileges);

        userRepository.save(user);

        log.info("Admin {} changed privileges to user {}.", bCryptPasswordEncoder.encode(adminEmail), bCryptPasswordEncoder.encode(user.getEmail()));

        return true;
    }

    public Boolean toggleBlockUser(Long userId, String adminEmail) throws CustomException {
        User user = userRepository.findById(userId).get();

        if (user == null)
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);

        user.toggleBlock();
        userRepository.save(user);

        if(user.isBlocked()){
            log.info("Admin {} blocked user {}.", bCryptPasswordEncoder.encode(adminEmail), bCryptPasswordEncoder.encode(user.getEmail()));
        }else{
            log.info("Admin {} unblocked user {}.", bCryptPasswordEncoder.encode(adminEmail), bCryptPasswordEncoder.encode(user.getEmail()));
        }

        return true;
    }

    public UserDTO logicalDeleteUser(Long userId, Authentication auth) throws CustomException {
        User user = userRepository.findById(userId).get();

        if (user == null)
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);

        user.logicalDelete();
        userRepository.save(user);

        User admin = (User) auth.getPrincipal();
        log.info("User {} has been deleted by {}", bCryptPasswordEncoder.encode(user.getEmail()), bCryptPasswordEncoder.encode(admin.getEmail()));

        return new UserDTO(user);
    }

    public List<Privilege> getBlockedPrivilegesByUserId(String userId) {
        List<Privilege> blockedPrivileges = privilegeRepository.findBlockedPrivilegesByUserId(Long.parseLong(userId));

        return blockedPrivileges;
    }
}
