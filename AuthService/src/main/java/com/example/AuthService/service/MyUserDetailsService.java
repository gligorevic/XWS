package com.example.AuthService.service;

import com.example.AuthService.domain.User;
import com.example.AuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                currentRequestAttributes()).
                getRequest();

        String ip = getClientIP(request);

        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("Blocked, too much attempts.");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else if(user.isBlocked()) {
            throw new UsernameNotFoundException("User is blocked");
        }

        return user;
    }

    private String getClientIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
