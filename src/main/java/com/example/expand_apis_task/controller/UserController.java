package com.example.expand_apis_task.controller;

import com.example.expand_apis_task.config.JwtGenerator;
import com.example.expand_apis_task.dto.UserDTO;
import com.example.expand_apis_task.model.Role;
import com.example.expand_apis_task.model.UserEntity;
import com.example.expand_apis_task.service.RoleService;
import com.example.expand_apis_task.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserServiceImpl userService,
                          AuthenticationManager authenticationManager,
                          JwtGenerator jwtGenerator,
                          RoleService roleService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> gelAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode((userDTO.getPassword())));

        Role role = roleService.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));

        userService.save(user);

        return ResponseEntity.ok("User added successfully.");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDTO userDTO) {
        if (userService.existByName(userDTO.getUsername())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUsername(),
                            userDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("No such User registered!", HttpStatus.NOT_FOUND);
    }
}
