package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Privilege;
import com.example.AgentApplication.domain.Role;
import com.example.AgentApplication.domain.User;
import com.example.AgentApplication.dto.LoginRequestDTO;
import com.example.AgentApplication.dto.UserDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.PrivilegeRepository;
import com.example.AgentApplication.repository.RoleRepository;
import com.example.AgentApplication.repository.UserRepository;
import com.example.AgentApplication.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.AgentApplication.security.SecurityConstants.TOKEN_BEARER_PREFIX;

@Service
public class UserService {

    @Autowired
    private JWTTokenHelper tokenHelper;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public String verifyUser(String bearerToken) throws CustomException {
        String jwt = tokenHelper.getJWTFromBearerToken(bearerToken);

        tokenHelper.validate(jwt);

        List<Long> rolesIdFromJWT = tokenHelper.getRolesIdFromJWT(jwt);

        List<Privilege> privilegesBoundWithRoles =  privilegeRepository.findByRolesIn(rolesIdFromJWT);
        User user = userRepository.findByEmail(tokenHelper.getUserUsernameFromJWT(jwt));

        List<Long> blockedPrivileges = user.getBlockedPrivileges().stream().map(p -> p.getId()).collect(Collectors.toList());

        List<Privilege> privileges = privilegesBoundWithRoles.stream()
                .filter(e -> !blockedPrivileges.contains(e.getId()))
                .collect(Collectors.toList());

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


    public UserDTO register(UserDTO userDTO) throws Exception, CustomException {
        User user = userRepository.findByEmail(userDTO.getEmail());

        if(user != null) {
            throw new CustomException("User already exist", HttpStatus.BAD_REQUEST);
        }

        Role role = roleRepository.findByName(userDTO.getRoleName());

        if(role == null || role.getName().equals("ROLE_ADMIN")) {
            throw new CustomException("Role doesn't exist", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User(userDTO);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        newUser.setRoles(roleList);
        newUser.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userRepository.save(newUser);

        userDTO.setPassword(null);

        return userDTO;
    }

    public User getUser(String email, Authentication authentication) throws Exception {
        User userFromAuth = (User)authentication.getPrincipal();

        if(!userFromAuth.getEmail().equals(email)) {
            throw new Exception("Unauthorized");
        }
        User user = userRepository.findByEmail(email);
        user.getRoles();
        user.getBlockedPrivileges();

        return user;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> allUsers = userRepository.findByEmailIsNotNull().stream().map(user -> new UserDTO(user)).collect(Collectors.toList());

        return allUsers;
    }
}
