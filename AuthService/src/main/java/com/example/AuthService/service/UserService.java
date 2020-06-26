package com.example.AuthService.service;

import com.example.AuthService.constants.Format;
import com.example.AuthService.controller.UserController;
import com.example.AuthService.domain.Privilege;
import com.example.AuthService.domain.Role;
import com.example.AuthService.domain.User;
import com.example.AuthService.dto.ChangePasswordDTO;
import com.example.AuthService.dto.LoginRequestDTO;
import com.example.AuthService.dto.UserDTO;
import com.example.AuthService.exception.CustomException;
import com.example.AuthService.repository.PrivilegeRepository;
import com.example.AuthService.repository.RoleRepository;
import com.example.AuthService.repository.UserRepository;
import com.example.AuthService.security.JWTTokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.AuthService.security.SecurityConstants.TOKEN_BEARER_PREFIX;

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

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public String verifyUser(String bearerToken) throws CustomException {
        String jwt = tokenHelper.getJWTFromBearerToken(bearerToken);

        if(StringUtils.hasText(jwt) && tokenHelper.validate(jwt)) {

            List<Long> rolesIdFromJWT = tokenHelper.getRolesIdFromJWT(jwt);

            List<Privilege> privilegesBoundWithRoles =  privilegeRepository.findByRolesIn(rolesIdFromJWT);
            String username = tokenHelper.getUserUsernameFromJWT(jwt);
            User user = userRepository.findByEmail(username);

            List<Long> blockedPrivileges = user.getBlockedPrivileges().stream().map(p -> p.getId()).collect(Collectors.toList());

            List<Privilege> privileges = privilegesBoundWithRoles.stream()
                    .filter(e -> !blockedPrivileges.contains(e.getId()))
                    .collect(Collectors.toList());
            log.info("User {} verified", bCryptPasswordEncoder.encode(username));
            String accessToken = tokenHelper.generateAccessToken(privileges, jwt);
            log.info("AccessToken generated for user {}", bCryptPasswordEncoder.encode(username));
            return accessToken;
        }

        return "";
    }

    public String login(LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        Authentication authentication = authenticationManager.authenticate(auth);

        SecurityContextHolder.getContext().setAuthentication(auth);

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

        log.info("User successully registrated with email {}", (bCryptPasswordEncoder.encode(userDTO.getEmail())));

        return userDTO;
    }

    public User getUser(String email, Authentication authentication) throws Exception {
        User userFromAuth = (User)authentication.getPrincipal();

        if(!userFromAuth.getEmail().equals(email)) {
            throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByEmail(email);
        if(user == null) {
            log.error("User {} not found", bCryptPasswordEncoder.encode(email));
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
        }
        user.getRoles();
        user.getBlockedPrivileges();

        log.info("User {} found.", bCryptPasswordEncoder.encode(user.getEmail()));

        return user;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> allUsers = userRepository.findByEmailIsNotNull().stream().map(user -> new UserDTO(user)).collect(Collectors.toList());

        return allUsers;
    }

    public void changePassword(ChangePasswordDTO newPasswordData) throws CustomException {
        if(!Format.password.matcher(newPasswordData.getNewPassword()).matches()) throw new CustomException("Weak password", HttpStatus.NOT_ACCEPTABLE);
        User user = userRepository.findByEmail(newPasswordData.getUsername());
        if(user == null) throw new CustomException("Bad username", HttpStatus.BAD_REQUEST);
        if(!bCryptPasswordEncoder.matches(newPasswordData.getOldPassword(), user.getPassword())) throw new CustomException("Password not match", HttpStatus.BAD_REQUEST);
        user.setPassword(bCryptPasswordEncoder.encode(newPasswordData.getNewPassword()));
        userRepository.save(user);
    }
}
