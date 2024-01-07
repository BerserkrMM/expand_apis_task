package com.example.expand_apis_task.service.impl;

import com.example.expand_apis_task.auth.JwtGenerator;
import com.example.expand_apis_task.exception.UserAddException;
import com.example.expand_apis_task.exception.UserAuthException;
import com.example.expand_apis_task.model.dto.UserDTO;
import com.example.expand_apis_task.model.entity.RoleEntity;
import com.example.expand_apis_task.model.entity.UserEntity;
import com.example.expand_apis_task.repository.UserRepository;
import com.example.expand_apis_task.service.RoleService;
import com.example.expand_apis_task.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        String userPassword = Optional.ofNullable(userDTO.getPassword())
                .map(passwordEncoder::encode)
                .orElseThrow(() -> new UserAddException("Password cannot be null"));

        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userPassword);

        RoleEntity roleEntity = roleService.findByName("ROLE_USER");
        user.setRoles(List.of(roleEntity));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserAddException("Cannot save user", e);
        }
    }

    @Override
    public String authenticateUser(UserDTO userDTO) {
        String token;
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUsername(),
                            userDTO.getPassword()));
        } catch (AuthenticationException e) {
            throw new UserAuthException("User auth problem", e);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = jwtGenerator.generateToken(authentication);

        return token;
    }
}
