package com.example.AuthService.service;

import com.example.AuthService.domain.Privilege;
import com.example.AuthService.domain.Role;
import com.example.AuthService.dto.LoginRequestDTO;
import com.example.AuthService.repository.PrivilegeRepository;
import com.example.AuthService.repository.RoleRepository;
import com.example.AuthService.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

import static com.example.AuthService.security.SecurityConstants.TOKEN_BEARER_PREFIX;

@Service
public class UserService {

    @Autowired
    private JWTTokenHelper tokenHelper;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String verifyUser(String bearerToken) {
        String jwt = tokenHelper.getJWTFromBearerToken(bearerToken);

        tokenHelper.validate(jwt);

        List<Long> rolesIdFromJWT = tokenHelper.getRolesIdFromJWT(jwt);

        List<Privilege> privileges =  privilegeRepository.findByRolesIn(rolesIdFromJWT);

        String accessToken = tokenHelper.generateAccessToken(privileges, jwt);

        return accessToken;
    }

    public String login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_BEARER_PREFIX +  tokenHelper.generate(authentication);

        return jwt;
    }


}
