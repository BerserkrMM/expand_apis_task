package com.example.expand_apis_task.service.impl;

import com.example.expand_apis_task.dto.UserDTO;
import com.example.expand_apis_task.model.UserEntity;
import com.example.expand_apis_task.repository.UserRepository;
import com.example.expand_apis_task.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}
