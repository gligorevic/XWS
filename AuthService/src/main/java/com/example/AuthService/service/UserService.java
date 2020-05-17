package com.example.AuthService.service;

import com.example.AuthService.domain.Role;
import com.example.AuthService.dto.LoginRequestDTO;
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
    private AuthenticationManager authenticationManager;

    public boolean  verifyUser(String bearerToken) {
        String jwt = getJWTFromBearerToken(bearerToken);

        System.out.println(jwt);
        tokenHelper.validate(jwt);

        System.out.println("Iscitavam role");
        List<String> rolesFromJWT = tokenHelper.getRolesFromJWT(jwt);


        return true;
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

    private String getJWTFromBearerToken(String bearerToken){
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_BEARER_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }
}
