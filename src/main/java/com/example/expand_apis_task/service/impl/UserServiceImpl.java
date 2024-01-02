package com.example.expand_apis_task.service.impl;

import com.example.expand_apis_task.dto.UserDTO;
import com.example.expand_apis_task.model.Role;
import com.example.expand_apis_task.model.UserEntity;
import com.example.expand_apis_task.repository.UserRepository;
import com.example.expand_apis_task.service.RoleService;
import com.example.expand_apis_task.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public void add(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
    }

    @Override
    public boolean existByName(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void addUser(String username, String encodedPassword) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        Role role = roleService.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}
