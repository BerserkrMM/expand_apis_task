package com.example.expand_apis_task.service;

import com.example.expand_apis_task.dto.UserDTO;
import com.example.expand_apis_task.model.UserEntity;

import java.util.List;

public interface UserService {

    void addUser(UserDTO userDTO);

    String authenticateUser(UserDTO userDTO);
}
