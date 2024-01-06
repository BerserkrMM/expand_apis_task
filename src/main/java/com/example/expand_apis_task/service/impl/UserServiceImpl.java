package com.example.expand_apis_task.service.impl;

import com.example.expand_apis_task.config.JwtGenerator;
import com.example.expand_apis_task.dto.UserDTO;
import com.example.expand_apis_task.excaption.UserAddException;
import com.example.expand_apis_task.excaption.UserAuthException;
import com.example.expand_apis_task.model.Role;
import com.example.expand_apis_task.model.UserEntity;
import com.example.expand_apis_task.repository.UserRepository;
import com.example.expand_apis_task.service.RoleService;
import com.example.expand_apis_task.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        if (userDTO.getPassword() == null) {
            throw new UserAddException("Password cannot be null");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        try{
            Role role = roleService.findByName("ROLE_USER");
            user.setRoles(List.of(role));
            userRepository.save(user);
        }
        catch (Exception e){
            throw new UserAddException("Something wrong with User add",e);
        }
    }

    @Override
    public String authenticateUser(UserDTO userDTO) throws UserAddException {
        String token;
        Authentication authentication;

        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUsername(),
                            userDTO.getPassword()));
        }
        catch (AuthenticationException e){
            throw new UserAuthException("User auth problem",e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = jwtGenerator.generateToken(authentication);

        return token;
    }
}
