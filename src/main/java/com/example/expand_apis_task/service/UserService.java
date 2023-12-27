package com.example.expand_apis_task.service;

import com.example.expand_apis_task.model.UserEntity;
import com.example.expand_apis_task.dto.UserDTO;
import com.example.expand_apis_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
    }

    public boolean existByName(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}
